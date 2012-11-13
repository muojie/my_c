#! /bin/sh
# 获取运行脚本所在的路径

selfpath=$(cd "$(dirname "$0")";pwd)

echo $selfpath
