#! /bin/sh

tree -iaf | sed 's/^[\.]//g' | awk '{print $1}'
