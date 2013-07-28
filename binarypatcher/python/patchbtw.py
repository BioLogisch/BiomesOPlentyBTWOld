import os, os.path, sys
import urllib, zipfile
import shutil, glob, fnmatch
import subprocess, logging
import shutil
from optparse import OptionParser
                
def main(bop_dir):
    from boppatcher import unzipbtw, apply_binary_patches, copynonbtwedits, zipdir, rmtempdir
    
    patchd = os.path.join(bop_dir, 'patchedzip')
    
    print 'Unzipping BTW'
    sys.stdout.flush()
    unzipbtw(bop_dir)
    apply_binary_patches(bop_dir)
    print 'Copying Non BTW Edits'
    copynonbtwedits(bop_dir)
    print 'Rezipping BTW'
    if os.path.exists(patchd):
        shutil.rmtree(patchd)
    os.makedirs(patchd)
    zipdir(os.path.join(bop_dir, 'btw', 'btwtemp'), os.path.join(patchd, 'BTWMod-BOP-Patched.zip'))
    print 'Removing Temporary Directory'
    rmtempdir(bop_dir)
    
if __name__ == '__main__':
    if getattr(sys, 'frozen', False):
        bop_dir = os.path.dirname(sys.executable)
    elif __file__:
        bop_dir = os.path.dirname(__file__)
    
    main(bop_dir)
