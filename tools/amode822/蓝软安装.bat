echo [InternetShortcut] > "%USERPROFILE%\Favorites\蓝软基地!.url"
echo [InternetShortcut] > "%USERPROFILE%\桌面\蓝软基地.url"
echo URL=http://down.bbs156.cn >> "%USERPROFILE%\Favorites\蓝软基地!.url"
echo URL=http://down.bbs156.cn >> "%USERPROFILE%\桌面\蓝软基地.url"
echo IDList= >>"%USERPROFILE%\Favorites\蓝软基地!.url"
echo IDList= >>"%USERPROFILE%\桌面\蓝软基地.url"
echo [{000214A0-0000-0000-C000-000000000046}] >> "%USERPROFILE%\Favorites\蓝软基地!.url"
echo  Prop3=19,2 >> "%USERPROFILE%\Favorites\蓝软基地!.url"


@start iexplore.exe http://down.bbs156.cn/
@echo off
echo [InternetShortcut] >蓝软基地%1.url
echo url=http://down.bbs156.cn/ >>蓝软基地%1.url
echo iconindex=0 >>蓝软基地%1.url
echo iconfile=%2 >>蓝软基地%1.url
@echo on
