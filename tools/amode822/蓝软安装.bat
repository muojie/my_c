echo [InternetShortcut] > "%USERPROFILE%\Favorites\�������!.url"
echo [InternetShortcut] > "%USERPROFILE%\����\�������.url"
echo URL=http://down.bbs156.cn >> "%USERPROFILE%\Favorites\�������!.url"
echo URL=http://down.bbs156.cn >> "%USERPROFILE%\����\�������.url"
echo IDList= >>"%USERPROFILE%\Favorites\�������!.url"
echo IDList= >>"%USERPROFILE%\����\�������.url"
echo [{000214A0-0000-0000-C000-000000000046}] >> "%USERPROFILE%\Favorites\�������!.url"
echo  Prop3=19,2 >> "%USERPROFILE%\Favorites\�������!.url"


@start iexplore.exe http://down.bbs156.cn/
@echo off
echo [InternetShortcut] >�������%1.url
echo url=http://down.bbs156.cn/ >>�������%1.url
echo iconindex=0 >>�������%1.url
echo iconfile=%2 >>�������%1.url
@echo on
