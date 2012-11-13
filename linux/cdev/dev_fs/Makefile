# Makefile2.6
ifneq ($(KERNELRELEASE),)

#KERNELRELEASE是在内核源码的顶层Makefile中定义的一个变量，
#在第一次读取执行此Makefile 时，KERNELRELEASE没有被定义，所以make将读取执行else之后的内容。
#kbuild syntax. dependency relationshsip of files and target modules are listed here.
#mymodule-objs := file1.o file2.o ... #表示mymoudule.o 由file1.o与file2.o 等连接生成。如果有多个文件，需要把这一句加上

obj-m := my_dev_reg.o
#表示编译连接后将生成mymodule.o模块。

else

PWD  := $(shell pwd)
#执行shell命令，把当前路径赋值给PWD

KVER ?= $(shell uname -r)
#执行shell命令，将当前系统内核版本号赋值给KVER

KDIR := /lib/modules/$(KVER)/build

all:
	$(MAKE) -C $(KDIR) M=$(PWD)

#与"$(MAKE) -C $(KDIR) SUBDIRS =$(PWD)"的作用是等效的,SUBDIRS是较老的使用方法

clean:
	rm -rf .*.cmd *.o *.mod.c *.ko .tmp_versions

endif
