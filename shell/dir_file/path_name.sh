#! /bin/sh
#获得当前文件所在目录名
basename 'pwd'
pwd|rev|awk -F V '{print $1}'|rev
pwd|sed 's,^\(.*/\)\?\([^/]*\),\2,'
