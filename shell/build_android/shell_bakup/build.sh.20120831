#! /bin/sh

echo " "

if [ "$#" != 2 ]; then
    echo "Usage: build.sh project_name build_version"
    exit
else
echo "----------------------------------------------"
if [ "$1"x = "dp500x" ]; then
    echo "        You build project: DP500"
    platform="mt6573"
    buildName="dp500"
    buildPath="source/server_sync/dp500/"
    Product="dp500.xml"
    destDir="../dp500_image"
    ftpPath="/var/ftp/image/dp500"

    ftpPath_500b="/var/ftp/image/dp500B"
    buildName1="dp500B"
elif [ "$1"x = "dp300x" ]; then
    echo "        You build project: DP300"
    platform="mt6573"
    buildName="dp300"
    buildPath="source/server_sync/dp300/"
    Product="dp300.xml"
    destDir="../dp300_image"
    ftpPath="/var/ftp/image/dp300"
elif [ "$1"x = "dp600x" ]; then
    echo "        You build project: DP600"
    platform="mt6575"
    buildName="dp600"
    buildPath="source/server_sync/dp600/"
    Product="dp600.xml"
    destDir="../dp600_image"
    ftpPath="/var/ftp/image/dp600"
else
    echo "        Not support project" $1
    exit
fi
fi

if [ "$2"x = "engx" ]; then
    echo "        You build version: Engineer"
elif [ "$2"x = "userx" ]; then
    build_variant="-opt=TARGET_BUILD_VARIANT=user"
    echo "        You build version: User"
else
    echo "        Not support version" $2
    exit
fi

cd ~

echo "        Setup environment..."

if [ ! -d "$buildPath" ]; then
    mkdir "$buildPath"
else
    rm -rf "$buildPath"
    mkdir "$buildPath"
fi

cd "$buildPath"

echo repo init -u git://192.168.1.110/Android/manifest -m "$Product"
repo init -u git://192.168.1.110/Android/manifest -m "$Product"

echo repo sync
repo sync

if [ "$platform"x = "mt6573x" ]; then
    if [ "$2"x = "engx" ]; then
	echo ./makeMtk nollec73_gb new 
	./makeMtk nollec73_gb new 
    elif [ "$2"x = "userx" ]; then
	echo ./makeMtk "$build_variant" nollec73_gb new 
	./makeMtk "$build_variant" nollec73_gb new 
    fi
elif [ "$platform"x = "mt6575x" ]; then
    if [ "$2"x = "engx" ]; then
	echo ./makeMtk mtk75cu_emmc_gb2 new 
	./makeMtk mtk75cu_emmc_gb2 new 
    elif [ "$2"x = "userx" ]; then
	echo ./makeMtk "$build_variant" mtk75cu_emmc_gb2 new 
	./makeMtk "$build_variant" mtk75cu_emmc_gb2 new 
    fi
fi


if [ ! -d "$destDir" ]; then
    mkdir "$destDir"
else
    rm -rf "$destDir"
    mkdir "$destDir"
fi

if [ "$platform"x = "mt6573x" ]; then
	cp out/target/product/nollec73_gb/*.img "$destDir"
	cp out/target/product/nollec73_gb/DSP_BL "$destDir"
	cp kernel/kernel_nollec73_gb.bin "$destDir"
	cp bootable/bootloader/uboot/logo.bin "$destDir"
	cp mediatek/source/misc/MT6573_Android_scatter.txt "$destDir"
	cp mediatek/source/preloader/preloader_nollec73_gb.bin "$destDir"
	cp bootable/bootloader/uboot/uboot_nollec73_gb.bin "$destDir"
elif [ "$platform"x = "mt6575x" ]; then
	cp out/target/product/mtk75cu_emmc_gb2/* "$destDir"
fi

chmod 644 "$destDir"/*

if [ ! -d "$ftpPath" ]; then
    mkdir "$ftpPath"
fi

da=`date +%Y_%m_%d_%H%M`
echo mv "$destDir" "$ftpPath"/"$da"_"$buildName"_"$2"
mv "$destDir" "$ftpPath"/"$da"_"$buildName"_"$2"

if [ "$1"x = "dp500x" ]; then
    echo "*****************building dp500b"
else
    exit
fi

cp ../tpd_custom_ft5206.h mediatek/custom/nollec73_gb/kernel/touchpanel/ft5206/

if [ "$2"x = "engx" ]; then
	echo ./makeMtk nollec73_gb new pl ub k
	./makeMtk nollec73_gb new pl ub k

	echo ./makeMtk nollec73_gb bootimage
	./makeMtk nollec73_gb bootimage
elif [ "$2"x = "userx" ]; then
	echo ./makeMtk "$build_variant" nollec73_gb new pl ub k
	./makeMtk "$build_variant" nollec73_gb new pl ub k

	echo ./makeMtk "$build_variant" nollec73_gb bootimage
	./makeMtk "$build_variant" nollec73_gb bootimage
fi

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
echo mv "$destDir" "$ftpPath_500b"/"$da"_"$buildName1"_"$2"
mv "$destDir" "$ftpPath_500b"/"$da"_"$buildName1"_"$2"

