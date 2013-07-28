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

#==========================================================================
#                      Patching
#==========================================================================
def apply_binary_patches(bop_dir, copy_files=True):
    #patch files
    print 'Applying binary patches'
    sys.stdout.flush()

    os.chdir(bop_dir)

    if os.path.isdir(os.path.join(bop_dir, 'patches', 'MINECRAFT-JAR')):
        apply_patches(os.path.join(bop_dir, 'patches', 'MINECRAFT-JAR'), os.path.join(bop_dir, 'btw', 'btwtemp', 'MINECRAFT-JAR'))
        
    if os.path.isdir(os.path.join(bop_dir, 'patches', 'MINECRAFT_SERVER-JAR')):
        apply_patches(os.path.join(bop_dir, 'patches', 'MINECRAFT_SERVER-JAR'), os.path.join(bop_dir, 'btw', 'btwtemp', 'MINECRAFT_SERVER-JAR'))

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
                
    file = os.path.splitext(os.path.basename(in_file))[0]
                    
    if out_file is None:
        shutil.move(tmp_file, in_file)
    return file
        
def apply_patches(patch_dir, target_dir, find=None, rep=None):
    temp = os.path.abspath('temp.patch')
    
    for path, _, filelist in os.walk(patch_dir, followlinks=True):
        for cur_file in fnmatch.filter(filelist, '*.patch'):
            patch_file = os.path.normpath(os.path.join(patch_dir, path[len(patch_dir)+1:], cur_file))
            target_file = os.path.join(target_dir, fix_patch(patch_file, temp, find, rep))
            call(["java", "-Xmx200m", "ie.wombat.jbdiff.JBPatch", target_file, target_file, patch_file])

    if os.path.isfile(temp):
        os.remove(temp)
        
#==========================================================================
#                      Copy Files
#==========================================================================
def copynonbtwedits(bop_dir): 
    minecraft_jar_loc = os.path.join(bop_dir, 'nonbtwedits', 'MINECRAFT-JAR')
    minecraft_server_jar_loc = os.path.join(bop_dir, 'nonbtwedits', 'MINECRAFT_SERVER-JAR')

    if os.path.exists(minecraft_jar_loc):
        copytree(minecraft_jar_loc, os.path.join(bop_dir, 'btw', 'btwtemp', 'MINECRAFT-JAR'))
    if os.path.exists(minecraft_server_jar_loc):
        copytree(minecraft_server_jar_loc, os.path.join(bop_dir, 'btw', 'btwtemp', 'MINECRAFT_SERVER-JAR'))
     
#==========================================================================
#                      Zip
#==========================================================================
def unzipbtw(bop_dir):
    filelist = os.listdir(os.path.join(bop_dir, 'btw'))
    zippath = os.path.join(bop_dir, 'btw', '\n'.join(fname for fname in filelist if fname.startswith("BTWMod") and fname.endswith('.zip')))
    
    btwtemp = os.path.join(bop_dir, 'btw', 'btwtemp')
    
    with zipfile.ZipFile(zippath) as zf:
        zf.extractall(btwtemp)
        
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
#                      Cleanup
#==========================================================================
def rmtempdir(bop_dir):
    btwtemp = os.path.join(bop_dir, 'btw', 'btwtemp')
    
    if os.path.exists(btwtemp):
        shutil.rmtree(btwtemp)
        
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