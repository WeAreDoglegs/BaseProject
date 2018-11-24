set APK=%1
set DEST=%2
set IS_CHANNEL=%3
echo %APK%
echo "------ jiagu  running..."

md %DEST%
java -jar ../jiagu/jiagu.jar -login 741085974@qq.com Wjx19950117@?
java -jar ../jiagu/jiagu.jar -importsign ../app/doglegs.jks wyw2018 doglegs wyw2018
java -jar ../jiagu/jiagu.jar -showmulpkg
java -jar ../jiagu/jiagu.jar -config -crashlog -x86 -msg
if %IS_CHANNEL% ==1 (java -jar ../jiagu/jiagu.jar -jiagu %APK% %DEST% -autosign -pkgparam ../jiagu/channels.txt -automulpkg) else (java -jar ../jiagu/jiagu.jar -jiagu %APK% %DEST% -autosign)

echo "------ jiagu  finished!"
