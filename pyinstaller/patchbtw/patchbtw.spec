# -*- mode: python -*-
a = Analysis(['/Users/aarongauntlett/Documents/Minecraft Development/bopbtw/dist/patchbtw.py'],
             pathex=['/Users/aarongauntlett/Documents/Minecraft Development/bopbtw/dist/pyinstaller-2.0'],
             hiddenimports=[],
             hookspath=None)
pyz = PYZ(a.pure)
exe = EXE(pyz,
          a.scripts,
          a.binaries,
          a.zipfiles,
          a.datas,
          name=os.path.join('dist', 'patchbtw'),
          debug=False,
          strip=None,
          upx=True,
          console=True )
