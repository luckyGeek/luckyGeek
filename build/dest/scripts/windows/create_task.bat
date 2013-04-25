@echo off
SET BAT_DIR = %CD%\..\..\
IF EXIST "%SystemRoot%\System32\schtasks.exe" (
IF EXIST "%BAT_DIR%run.bat" (
echo startup.bat found.
) ELSE (
echo No startup.bat found.)
SCHTASKS /CREATE /SC DAILY /MO 1 /TN "luckygeek" /TR "%BAT_DIR%run.bat" /ST 12:00:00
) ELSE (
echo Can not create task. No schtasks.exe found.
)