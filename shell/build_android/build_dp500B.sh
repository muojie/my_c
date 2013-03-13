#! /bin/sh

cd ~

buildPath="source/server_sync/dp500/"
Product="dp500.xml"
destDir="../dp500b_image"
ftpPath="/var/ftp/image/dp500"
ftpPath_500b="/var/ftp/image/dp500B"
buildName="dp500"
buildName1="dp500B"

if [ ! -d "$buildPath" ]; then
    mkdir "$buildPath"
else
    rm -rf "$buildPath"
    mkdir "$buildPath"
fi

cd "$buildPath"

repo init -u git://192.168.1.110/Android/manifest -m "$Product"

repo sync

cp ../tpd_custom_ft5206.h mediatek/custom/nollec73_gb/kernel/touchpanel/ft5206/

./makeMtk nollec73_gb new 

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

if [ ! -d "$ftpPath_500b" ]; then
    mkdir "$ftpPath_500b"
fi

da=`date +%Y_%m_%d_%H%M`
mv "$destDir" "$ftpPath_500b"/"$da"_"$buildName1"

