import os, os.path, sys
import urllib, zipfile, json, urllib2
import shutil, glob, fnmatch
import subprocess, logging, re, shlex
import csv, ConfigParser
import zipfile,os.path
from hashlib import md5  # pylint: disable-msg=E0611
from pprint import pprint
from zipfile import ZipFile, ZIP_DEFLATED
from pprint import pprint
from contextlib import closing
from subprocess import call
from runtime.commands import Commands, CLIENT, SERVER, CalledProcessError
from runtime.mcp import recompile_side
  
#==========================================================================
#                      Patching
#==========================================================================
def apply_initial_patches(mcp_dir, bop_dir, src_dir, copy_files=True):
    #patch files
    print 'Applying initial patches'
    sys.stdout.flush()

    if os.path.isdir(os.path.join(bop_dir, 'initialpatches', 'minecraft')):
        apply_patches(mcp_dir, os.path.join(bop_dir, 'initialpatches', 'minecraft'), src_dir)
        
    if os.path.isdir(os.path.join(bop_dir, 'initialpatches', 'minecraft_server')):
        apply_patches(mcp_dir, os.path.join(bop_dir, 'initialpatches', 'minecraft_server'), src_dir)
        
def apply_bop_patches(mcp_dir, bop_dir, src_dir, copy_files=True):
    #patch files
    print 'Applying BOP patches'
    sys.stdout.flush()

    if os.path.isdir(os.path.join(bop_dir, 'patches', 'minecraft')):
        apply_patches(mcp_dir, os.path.join(bop_dir, 'patches', 'minecraft'), src_dir)
        
    if os.path.isdir(os.path.join(bop_dir, 'patches', 'minecraft_server')):
        apply_patches(mcp_dir, os.path.join(bop_dir, 'patches', 'minecraft_server'), src_dir)
        
def repatch_bop(bop_dir, mcp_dir):
    print 'Applying BOP patches'
    sys.stdout.flush()
    
    src_dir = os.path.join(mcp_dir, 'src')
    orig_dir = os.path.join(mcp_dir, 'src_base')

    if os.path.isdir(os.path.join(bop_dir, 'patches', 'minecraft')):
        repatch(mcp_dir, os.path.join(bop_dir, 'patches', 'minecraft'), src_dir, orig_dir)
        
    if os.path.isdir(os.path.join(bop_dir, 'patches', 'minecraft_server')):
        repatch(mcp_dir, os.path.join(bop_dir, 'patches', 'minecraft_server'), src_dir, orig_dir)

def fix_patch(in_file, out_file, find=None, rep=None):
    #Fixes the following issues in the patch file if they exist:
    #  Normalizes the path seperators for the current OS
    #  Normalizes the line endings
    # Returns the path that the file wants to apply to
    
    in_file = os.path.normpath(in_file)
    if out_file is None:
        tmp_file = in_file + '.tmp'
    else:
        out_file = os.path.normpath(out_file)
        tmp_file = out_file
        dir_name = os.path.dirname(out_file)
        if dir_name:
            if not os.path.exists(dir_name):
                os.makedirs(dir_name)
                
    file = 'not found'
    with open(in_file, 'rb') as inpatch:
        with open(tmp_file, 'wb') as outpatch:
            for line in inpatch:
                line = line.rstrip('\r\n')
                if line[:3] in ['+++', '---', 'Onl', 'dif']:
                    if not find == None and not rep == None:
                        line = line.replace('\\', '/').replace(find, rep).replace('/', os.sep)
                    else:
                        line = line.replace('\\', '/').replace('/', os.sep)
                    outpatch.write(line + os.linesep)
                else:
                    outpatch.write(line + os.linesep)
                if line[:3] == '---':
                    file = line[line.find(os.sep, line.find(os.sep)+1)+1:]
                    
    if out_file is None:
        shutil.move(tmp_file, in_file)
    return file
        
def apply_patches(mcp_dir, patch_dir, target_dir, find=None, rep=None):
    # Attempts to apply a directory full of patch files onto a target directory.
    sys.path.append(mcp_dir)
    
    temp = os.path.abspath('temp.patch')
    cmd = cmdsplit('patch -p2 -i "%s" ' % temp)
    
    if os.name == 'nt':
        applydiff = os.path.abspath(os.path.join(mcp_dir, 'runtime', 'bin', 'applydiff.exe'))
        cmd = cmdsplit('"%s" -uf -p2 -i "%s"' % (applydiff, temp))
    
    for path, _, filelist in os.walk(patch_dir, followlinks=True):
        for cur_file in fnmatch.filter(filelist, '*.patch'):
            patch_file = os.path.normpath(os.path.join(patch_dir, path[len(patch_dir)+1:], cur_file))
            target_file = os.path.join(target_dir, fix_patch(patch_file, temp, find, rep))
            process = subprocess.Popen(cmd, cwd=target_dir, bufsize=-1)
            process.communicate()

    if os.path.isfile(temp):
        os.remove(temp)
        
def repatch(mcp_dir, patch_dir, target_dir, orig_dir, find=None, rep=None):
    # Attempts to apply a directory full of patch files onto a target directory.
    sys.path.append(mcp_dir)
    
    temp = os.path.abspath('temp.patch')
    cmd = cmdsplit('patch -p2 -i "%s" ' % temp)
    
    if os.name == 'nt':
        applydiff = os.path.abspath(os.path.join(mcp_dir, 'runtime', 'bin', 'applydiff.exe'))
        cmd = cmdsplit('"%s" -uf -p2 -i "%s"' % (applydiff, temp))
    
    for path, _, filelist in os.walk(patch_dir, followlinks=True):
        for cur_file in fnmatch.filter(filelist, '*.patch'):
            patch_file = os.path.normpath(os.path.join(patch_dir, path[len(patch_dir)+1:], cur_file))
            target_file = os.path.join(target_dir, fix_patch(patch_file, temp, find, rep))
            orig_file = os.path.join(orig_dir, fix_patch(patch_file, temp, find, rep))
            if os.path.isfile(target_file):
                os.remove(target_file)
            shutil.copyfile(orig_file, target_file)
            process = subprocess.Popen(cmd, cwd=target_dir, bufsize=-1)
            process.communicate()

    if os.path.isfile(temp):
        os.remove(temp)
        
def cmdsplit(args):
    if os.sep == '\\':
        args = args.replace('\\', '\\\\')
    return shlex.split(args)             
        
#==========================================================================
#                      MCP Decompile Process
#==========================================================================
def reset_logger():
    # Resets the logging handlers, if we don't do this, we get multi-prints from MCP
    log = logging.getLogger()
    while len(log.handlers) > 0:
        log.removeHandler(log.handlers[0])    
        
def decompile(mcp_dir, bop_dir):
    from runtime.cleanup import cleanup
    from runtime.decompile import decompile      

    src_dir = os.path.join(mcp_dir, 'src')    
    
    os.chdir(mcp_dir)
        
    if os.path.isdir(src_dir):
        cleanup(None, False)
        if os.path.exists(os.path.join(mcp_dir, 'src_base')):
            shutil.rmtree(os.path.join(mcp_dir, 'src_base'))
    
    reset_logger()
    decompile(None, False, False, False, False, False, False, False, False, False, False, False, False)
    reset_logger()    
    os.chdir(bop_dir) 
    
#==========================================================================
#                      MCP Recompile Process
#==========================================================================
def recompile(conffile, only_client, only_server):
    errorcode = 0
    try:
        commands = Commands(conffile, verify=True)

        # client or server
        process_client = True
        process_server = True
        if only_client and not only_server:
            process_server = False
        if only_server and not only_client:
            process_client = False

        if process_client:
            try:
                recompile_side(commands, CLIENT)
            except CalledProcessError:
                errorcode = 2
                pass
        if process_server:
            try:
                recompile_side(commands, SERVER)
            except CalledProcessError:
                errorcode = 3
                pass
    except Exception:  # pylint: disable-msg=W0703
        logging.exception('FATAL ERROR')
        sys.exit(1)    
        
#==========================================================================
#                      Binary Patch Setup
#==========================================================================
def copyreobfuscatedfiles(bop_dir, mcp_dir):
    basediff = os.path.join(bop_dir, 'basediff')
    minecraft_jar_loc = os.path.join(basediff, 'work', 'MINECRAFT-JAR')
    minecraft_server_jar_loc = os.path.join(basediff, 'work', 'MINECRAFT_SERVER-JAR')

    if os.path.exists(minecraft_jar_loc):
        shutil.rmtree(minecraft_jar_loc)
    if os.path.exists(minecraft_server_jar_loc):
        shutil.rmtree(minecraft_server_jar_loc)
    copytree(os.path.join(mcp_dir, 'reobf', 'minecraft'), minecraft_jar_loc) 
    copytree(os.path.join(mcp_dir, 'reobf', 'minecraft_server'), minecraft_server_jar_loc)
    
def unzipandcopybtw(bop_dir):
    basediff = os.path.join(bop_dir, 'basediff')
    base_loc = os.path.join(basediff, 'base')
    filelist = os.listdir(os.path.join(basediff, 'btw'))
    zippath = os.path.join(basediff, 'btw', '\n'.join(fname for fname in filelist if fname.startswith("BTWMod") and fname.endswith('.zip')))
    
    btwtemp = os.path.join(basediff, 'btw', 'btwtemp')
    unzip(zippath, btwtemp)
    copytree(os.path.join(btwtemp, 'MINECRAFT-JAR'), base_loc) 
    copytree(os.path.join(btwtemp, 'MINECRAFT_SERVER-JAR'), base_loc)    
    shutil.rmtree(os.path.join(basediff, 'btw', 'btwtemp'))  
    shutil.rmtree(os.path.join(basediff, 'base', 'btwmodtex'))   
    shutil.rmtree(os.path.join(basediff, 'base', 'net'))    
    shutil.rmtree(os.path.join(basediff, 'base', 'textures'))  
    shutil.rmtree(os.path.join(basediff, 'base', 'title'))  
    
#==========================================================================
#                      Create Dist
#==========================================================================
def movetodist(bop_dir):
    basediffdir = os.path.join(bop_dir, 'basediff')
    nonbtweditsdir = os.path.join(basediffdir, 'nonbtwedits')
    patchesdir = os.path.join(basediffdir, 'patches')
    binarypatcherdir = os.path.join(bop_dir, 'binarypatcher')
    distdir = os.path.join(bop_dir, 'tempdist')
        
    if os.path.exists(distdir):
        shutil.rmtree(distdir)
    
    if os.path.exists(os.path.join(binarypatcherdir, 'nonbtwedits')):
        shutil.rmtree(os.path.join(binarypatcherdir, 'nonbtwedits'))
        
    if os.path.exists(os.path.join(binarypatcherdir, 'patches')):
        shutil.rmtree(os.path.join(binarypatcherdir, 'patches'))
    
    copytree(nonbtweditsdir, os.path.join(binarypatcherdir, 'nonbtwedits')) 
    copytree(patchesdir, os.path.join(binarypatcherdir, 'patches')) 
    copytree(binarypatcherdir, distdir) 
        
    if os.path.isfile(os.path.join(bop_dir, 'tempdist', '.gitignore')):
        os.remove(os.path.join(bop_dir, 'tempdist', '.gitignore'))
        
    if os.path.exists(os.path.join(distdir, 'java')):
        shutil.rmtree(os.path.join(distdir, 'java'))
    
def packagedist(bop_dir):
    if os.path.exists(os.path.join(bop_dir, 'dist')):
        shutil.rmtree(os.path.join(bop_dir, 'dist'))
    os.makedirs(os.path.join(bop_dir, 'dist'))
    
    zipdir(os.path.join(bop_dir, 'tempdist'), os.path.join(bop_dir, 'dist', 'BOP-BTW-Patcher-Universal.zip'))

    if os.path.exists(os.path.join(bop_dir, 'tempdist')):
        shutil.rmtree(os.path.join(bop_dir, 'tempdist'))
#==========================================================================
#                     Cleanup
#==========================================================================
def cleanup(bop_dir, mcp_dir):
    basediff = os.path.join(bop_dir, 'basediff')
    btw_minecraft_jar_loc = os.path.join(basediff, 'base', 'MINECRAFT-JAR')
    btw_minecraft_server_jar_loc = os.path.join(basediff, 'base', 'MINECRAFT_SERVER-JAR')
    work_minecraft_jar_loc = os.path.join(basediff, 'work', 'MINECRAFT-JAR')
    work_minecraft_server_jar_loc = os.path.join(basediff, 'work', 'MINECRAFT_SERVER-JAR')
    bop_mcp_csrc_loc = os.path.join(mcp_dir, 'src', 'minecraft', 'net', 'minecraft', 'src', 'biomesoplenty')
    bop_mcp_ssrc_loc = os.path.join(mcp_dir, 'src', 'minecraft_server', 'net', 'minecraft', 'src', 'biomesoplenty')
    
    if os.path.exists(btw_minecraft_jar_loc):
        shutil.rmtree(btw_minecraft_jar_loc)
    if os.path.exists(btw_minecraft_server_jar_loc):
        shutil.rmtree(btw_minecraft_server_jar_loc)
        
    if os.path.exists(work_minecraft_jar_loc):
        shutil.rmtree(work_minecraft_jar_loc)
    if os.path.exists(work_minecraft_server_jar_loc):
        shutil.rmtree(work_minecraft_server_jar_loc)
        
    if os.path.exists(bop_mcp_csrc_loc):
        shutil.rmtree(bop_mcp_csrc_loc)
    if os.path.exists(bop_mcp_ssrc_loc):
        shutil.rmtree(bop_mcp_ssrc_loc)
        
#==========================================================================
#                      Zip
#==========================================================================
def unzip(source_filename, dest_dir):
    with zipfile.ZipFile(source_filename) as zf:
        for member in zf.infolist():
            # Path traversal defense copied from
            # http://hg.python.org/cpython/file/tip/Lib/http/server.py#l789
            words = member.filename.split('/')
            path = dest_dir
            for word in words[:-1]:
                drive, word = os.path.splitdrive(word)
                head, word = os.path.split(word)
                if word in (os.curdir, os.pardir, ''): continue
                path = os.path.join(path, word)
            zf.extract(member, path)
            
def zipdir(basedir, archivename):
    assert os.path.isdir(basedir)
    with closing(ZipFile(archivename, "w", ZIP_DEFLATED)) as z:
        for root, dirs, files in os.walk(basedir):
            #NOTE: ignore empty directories
            for fn in files:
                absfn = os.path.join(root, fn)
                zfn = absfn[len(basedir)+len(os.sep):] #XXX: relative path
                z.write(absfn, zfn)
        
#==========================================================================
# Taken from: http://stackoverflow.com/questions/7545299/distutil-shutil-copytree
#==========================================================================
def _mkdir(newdir):
    """works the way a good mkdir should :)
        - already exists, silently complete
        - regular file in the way, raise an exception
        - parent directory(ies) does not exist, make them as well
    """
    if os.path.isdir(newdir):
        pass
    elif os.path.isfile(newdir):
        raise OSError("a file with the same name as the desired " \
                      "dir, '%s', already exists." % newdir)
    else:
        head, tail = os.path.split(newdir)
        if head and not os.path.isdir(head):
            _mkdir(head)
        #print "_mkdir %s" % repr(newdir)
        if tail:
            os.mkdir(newdir)
            
def copytree(src, dst, verbose=0, symlinks=False):
    """Recursively copy a directory tree using copy2().

    The destination directory must not already exist.
    If exception(s) occur, an Error is raised with a list of reasons.

    If the optional symlinks flag is true, symbolic links in the
    source tree result in symbolic links in the destination tree; if
    it is false, the contents of the files pointed to by symbolic
    links are copied.

    XXX Consider this example code rather than the ultimate tool.

    """
    
    if verbose == -1:
        verbose = len(os.path.abspath(dst)) + 1
    names = os.listdir(src)
    # os.makedirs(dst)
    _mkdir(dst) # XXX
    errors = []
    for name in names:
        srcname = os.path.join(src, name)
        dstname = os.path.join(dst, name)
        try:
            if symlinks and os.path.islink(srcname):
                linkto = os.readlink(srcname)
                os.symlink(linkto, dstname)
            elif os.path.isdir(srcname):
                copytree(srcname, dstname, verbose, symlinks)
            else:
                shutil.copy2(srcname, dstname)
                if verbose > 0:
                    print os.path.abspath(dstname)[verbose:]
            # XXX What about devices, sockets etc.?
        except (IOError, os.error), why:
            errors.append((srcname, dstname, str(why)))
        # catch the Error from the recursive copytree so that we can
        # continue with other files
        except Exception, err:
            errors.extend(err.args[0])
    try:
        shutil.copystat(src, dst)
    except WindowsError:
        # can't copy file access times on Windows
        pass