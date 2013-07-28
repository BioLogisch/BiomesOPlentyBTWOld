import os, os.path, sys
import urllib, zipfile
import shutil, glob, fnmatch
import subprocess, logging
import shutil
from optparse import OptionParser
    
def bop_main(bop_dir, mcp_dir):
    sys.path.append(mcp_dir)
    from runtime.reobfuscate import reobfuscate
    from bop import copytree, reset_logger, recompile, copyreobfuscatedfiles, unzipandcopybtw, movetodist, createapplication, packagedist, cleanup
    from update_binary_patches import updatepatches
    
    print '=============================== Build Biomes O Plenty Start ====================================='

    print 'Copying BOP Files'
    copytree(os.path.join(bop_dir, 'src'), os.path.join(mcp_dir, 'src')) 
    os.chdir(mcp_dir)
    reset_logger()
    print 'Recompiling'
    recompile(None, False, False)
    reset_logger()
    print 'Reobfuscating'
    reobfuscate(None, False, False, False, False, False, False)
    reset_logger()
    print 'Copying Reobfuscated Files'
    copyreobfuscatedfiles(bop_dir, mcp_dir)
    print 'Unzipping BTW & Copying Files'
    unzipandcopybtw(bop_dir)
    print 'Creating Binary Patches'
    updatepatches()
    print 'Moving Files To Temporary Dist Folder'
    movetodist(bop_dir)
    print 'Creating Patcher Application'
    createapplication(bop_dir)
    print 'Packaging Files'
    packagedist(bop_dir)
    print 'Cleaning Up'
    cleanup(bop_dir, mcp_dir)

    print '=============================== Build Biomes O Plenty Finished ================================='

if __name__ == '__main__':
    parser = OptionParser()
    parser.add_option('-m', '--mcp-dir',   action='store',      dest='mcp_dir',       help='Path to download/extract MCP to',         default=None )
    options, _ = parser.parse_args()
    
    bop_dir = os.path.dirname(os.path.abspath(__file__))
    mcp_dir = os.path.abspath('mcp')

    if not options.mcp_dir is None:
        mcp_dir = os.path.abspath(options.mcp_dir)
    
    bop_main(bop_dir, mcp_dir)