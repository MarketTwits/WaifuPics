#ifndef AppImage
  #error AppImage must point to the packaged WaifuPics app image
#endif

#ifndef AppVersion
  #error AppVersion must be provided by the release workflow
#endif

[Setup]
AppId={{86C3E235-8B88-4D9D-946C-52E1A81906A5}
AppName=WaifuPics
AppVersion={#AppVersion}
AppPublisher=MarketTwits
VersionInfoVersion={#AppVersion}.0
VersionInfoCompany=MarketTwits
VersionInfoDescription=WaifuPics installer
VersionInfoProductName=WaifuPics
VersionInfoProductVersion={#AppVersion}
AppPublisherURL=https://github.com/MarketTwits/WaifuPics
AppSupportURL=https://github.com/MarketTwits/WaifuPics/issues
AppUpdatesURL=https://github.com/MarketTwits/WaifuPics/releases
DefaultDirName={localappdata}\Programs\WaifuPics
DefaultGroupName=WaifuPics
UninstallDisplayIcon={app}\WaifuPics.exe
SetupIconFile=..\..\modules\instances\desktop\resources\windows\WaifuPics.ico
ArchitecturesAllowed=x64compatible
ArchitecturesInstallIn64BitMode=x64compatible
PrivilegesRequired=lowest
OutputDir=.
OutputBaseFilename=WaifuPics-v{#AppVersion}-windows-x64
Compression=lzma2/ultra64
SolidCompression=yes
WizardStyle=modern
DisableProgramGroupPage=yes
CloseApplications=yes
RestartApplications=no
Uninstallable=yes

[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"

[Tasks]
Name: "desktopicon"; Description: "Create a desktop shortcut"; GroupDescription: "Additional shortcuts:"; Flags: unchecked

[Files]
Source: "{#AppImage}\*"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs

[Icons]
Name: "{autoprograms}\WaifuPics"; Filename: "{app}\WaifuPics.exe"
Name: "{autodesktop}\WaifuPics"; Filename: "{app}\WaifuPics.exe"; Tasks: desktopicon

[Run]
Filename: "{app}\WaifuPics.exe"; Description: "Launch WaifuPics"; Flags: postinstall nowait skipifsilent
