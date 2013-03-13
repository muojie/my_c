#! /bin/sh

cd ~

buildPath="source/server_sync/dp300/"
Product="dp300.xml"
destDir="../dp300_image"
ftpPath="/var/ftp/image/dp300"

cd "$buildPath"

if [ ! -d "$destDir" ]; then
    mkdir "$destDir"
else
    rm -rf "$destDir"
    mkdir "$destDir"
fi

cp out/target/product/nollec73_gb/*.img "$destDir"
cp out/target/product/nollec73_gb/DSP_BL "$destDir"
cp kernel/kernel_nollec73_gb.bin "$destDir"
cp bootable/bootloader/uboot/logo.bin "$destDir"
cp mediatek/source/misc/MT6573_Android_scatter.txt "$destDir"
cp mediatek/source/preloader/preloader_nollec73_gb.bin "$destDir"
cp bootable/bootloader/uboot/uboot_nollec73_gb.bin "$destDir"

chmod 644 "$destDir"/*

if [ ! -d "$ftpPath" ]; then
    mkdir "$ftpPath"
fi

da=`date +%Y%m%d%H%M`
mv "$destDir" "$ftpPath/$da"

