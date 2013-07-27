import os, os.path, sys
import urllib, zipfile
import shutil, glob, fnmatch
import subprocess, logging
import shutil
from optparse import OptionParser
                
def main(bop_dir, mcp_dir):
    sys.path.append(mcp_dir)
    from bop import repatch_bop
    
    repatch_bop(bop_dir, mcp_dir)
    
if __name__ == '__main__':
    parser = OptionParser()
    parser.add_option('-m', '--mcp-dir',   action='store',      dest='mcp_dir',       help='Path to download/extract MCP to',         default=None )
    options, _ = parser.parse_args()
    
    bop_dir = os.path.dirname(os.path.abspath(__file__))
    mcp_dir = os.path.abspath('mcp')

    if not options.mcp_dir is None:
        mcp_dir = os.path.abspath(options.mcp_dir)
    
    main(bop_dir, mcp_dir)
