#include <linux/module.h>
#include <linux/vermagic.h>
#include <linux/compiler.h>

MODULE_INFO(vermagic, VERMAGIC_STRING);

struct module __this_module
__attribute__((section(".gnu.linkonce.this_module"))) = {
 .name = KBUILD_MODNAME,
 .init = init_module,
#ifdef CONFIG_MODULE_UNLOAD
 .exit = cleanup_module,
#endif
 .arch = MODULE_ARCH_INIT,
};

static const struct modversion_info ____versions[]
__used
__attribute__((section("__versions"))) = {
	{ 0x8e49f4de, "module_layout" },
	{ 0xecf43c07, "kmalloc_caches" },
	{ 0x105e2727, "__tracepoint_kmalloc" },
	{ 0x6a9f26c9, "init_timer_key" },
	{ 0x7d11c268, "jiffies" },
	{ 0xe83fea1, "del_timer_sync" },
	{ 0xea147363, "printk" },
	{ 0xb4390f9a, "mcount" },
	{ 0x45450063, "mod_timer" },
	{ 0x596903e5, "kmem_cache_alloc" },
	{ 0x37a0cba, "kfree" },
};

static const char __module_depends[]
__used
__attribute__((section(".modinfo"))) =
"depends=";


MODULE_INFO(srcversion, "8432374CDC152FE08A8F5F6");
