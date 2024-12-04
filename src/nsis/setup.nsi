; Installer script for Gerenciador de Medalhas

!define APP_NAME "Gerenciador de Medalhas"
!define COMP_NAME "Your Organization"
!define VERSION "1.0.0.0"
!define COPYRIGHT "Your Organization"
!define DESCRIPTION "Sistema de Gerenciamento de Medalhas"
!define INSTALLER_NAME "gerenciador-medalhas-installer.exe"
!define MAIN_APP_EXE "gerenciador-medalhas.exe"

; Main Install settings
Name "${APP_NAME}"
InstallDir "$PROGRAMFILES64\${APP_NAME}"
OutFile "${INSTALLER_NAME}"

Section "Install"
    ; Set output path to the installation directory
    SetOutPath $INSTDIR

    ; Add files
    File "gerenciador-medalhas.exe"
    File /r "jre\"

    ; Create Start Menu shortcuts
    CreateDirectory "$SMPROGRAMS\${APP_NAME}"
    CreateShortCut "$SMPROGRAMS\${APP_NAME}\${APP_NAME}.lnk" "$INSTDIR\${MAIN_APP_EXE}"
    CreateShortCut "$DESKTOP\${APP_NAME}.lnk" "$INSTDIR\${MAIN_APP_EXE}"

    ; Write uninstaller
    WriteUninstaller "$INSTDIR\uninstall.exe"
SectionEnd

Section "Uninstall"
    ; Remove Start Menu shortcuts
    Delete "$SMPROGRAMS\${APP_NAME}\${APP_NAME}.lnk"
    Delete "$DESKTOP\${APP_NAME}.lnk"
    RMDir "$SMPROGRAMS\${APP_NAME}"

    ; Remove files
    Delete "$INSTDIR\${MAIN_APP_EXE}"
    Delete "$INSTDIR\uninstall.exe"
    RMDir /r "$INSTDIR\jre"
    RMDir "$INSTDIR"
SectionEnd