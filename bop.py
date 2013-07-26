import os, os.path, sys
import urllib, zipfile, json, urllib2
import shutil, glob, fnmatch
import subprocess, logging, re, shlex
import csv, ConfigParser
from hashlib import md5  # pylint: disable-msg=E0611
from pprint import pprint
from zipfile import ZipFile
from pprint import pprint
from contextlib import closing
  
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
        
    #if copy_files and os.path.isdir(os.path.join(bop_dir, 'src', 'minecraft')):
        #copytree(os.path.join(bop_dir, 'src', 'minecraft'), os.path.join(src_dir, 'minecraft'))
    #if copy_files and os.path.isdir(os.path.join(bop_dir, 'src', 'minecraft_server')):
        #copytree(os.path.join(bop_dir, 'src', 'minecraft_server'), os.path.join(src_dir, 'minecraft_server'))

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