@echo off
echo Creating Medal Manager distribution package...

:: Set up our directory structure
set DIST_DIR=dist\gerenciador-medalhas
set JRE_SOURCE=C:\Users\pedro.cornutti\Downloads\OpenJDK17U-jre_x64_windows_hotspot_17.0.13_11\jdk-17.0.13+11-jre
echo Creating distribution directory...
mkdir %DIST_DIR%

:: Copy our executable
echo Copying application executable...
copy target\gerenciador-medalhas.exe %DIST_DIR%\

:: Copy the entire JRE
echo Copying Java Runtime Environment...
xcopy /E /I /H "%JRE_SOURCE%" "%DIST_DIR%\jre"

echo Distribution package created successfully!
echo.
echo You can find your application in the '%DIST_DIR%' folder
echo To test it, run gerenciador-medalhas.exe from that folder
pause