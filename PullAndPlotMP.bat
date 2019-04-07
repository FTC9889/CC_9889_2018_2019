set arg1=%1
@echo off
cd platform-tools
adb pull /sdcard/saved_data/%arg1%.csv
move %arg1%.csv ../.
cd ../.
py plotDTMP.py %arg1%