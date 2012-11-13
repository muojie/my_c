#! /bin/sh

find . -type f | sed 's/^[\.]//g' | awk '{print $1}'
find . -type l | sed 's/^[\.]//g' | awk '{print $1}'
