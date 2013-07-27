import os
import sys
import fnmatch
import shlex
import difflib
import time
import shutil
from optparse import OptionParser
from subprocess import call

def cmdsplit(args):
    if os.sep == '\\':
        args = args.replace('\\', '\\\\')
    return shlex.split(args)
                    
def cleanDirs(path):
    if not os.path.isdir(path):
        return
 
    files = os.listdir(path)
    if len(files):
        for f in files:
            fullpath = os.path.join(path, f)
            if os.path.isdir(fullpath):
                cleanDirs(fullpath)
 
    files = os.listdir(path)
    if len(files) == 0:
        os.rmdir(path)
        
def updatepatches():
    print("Creating patches")
    bop_dir = os.path.dirname(os.path.abspath(__file__))
    basediff = os.path.normpath(os.path.join(bop_dir, 'basediff'))
    
    patchd = os.path.normpath(os.path.join(basediff, 'patches'))
    base = os.path.normpath(os.path.join(basediff, 'base'))
    work = os.path.normpath(os.path.join(basediff, 'work'))
    
    if os.path.exists(patchd):
        shutil.rmtree(patchd)
    
    for path, _, filelist in os.walk(work, followlinks=True):
        for cur_file in fnmatch.filter(filelist, '*.class'):
            file_base = os.path.normpath(os.path.join(base, path[len(work)+1:], cur_file)).replace(os.path.sep, '/')
            file_work = os.path.normpath(os.path.join(work, path[len(work)+1:], cur_file)).replace(os.path.sep, '/')
            
            if not os.path.isfile(file_base):
            	print("Missing base file %s using work file..."%(file_base))
            	non_btw_edits = os.path.normpath(os.path.join(basediff, 'nonbtwedits'))
            	non_btw_edits_copy = os.path.normpath(os.path.join(non_btw_edits, path[len(work)+1:])).replace(os.path.sep, '/')
            	
                if os.path.exists(non_btw_edits):
                    shutil.rmtree(non_btw_edits)
            	
            	if not os.path.exists(non_btw_edits_copy):
                    os.makedirs(non_btw_edits_copy)
            	shutil.copyfile(file_work, os.path.normpath(os.path.join(non_btw_edits_copy, cur_file)).replace(os.path.sep, '/'))
            	continue
            
            patch_dir = os.path.join(patchd, path[len(work)+1:])
            patch_file = os.path.join(patch_dir, cur_file + '.patch')
            
            print patch_file[len(patchd)+1:]
                
            if not os.path.exists(patch_dir):
                os.makedirs(patch_dir)
            
            call(["bsdiff", file_base, file_work, patch_file])

    cleanDirs(patchd)
    
if __name__ == '__main__':
    updatepatches()
