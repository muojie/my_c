#include <linux/timer.h>
#include <linux/slab.h>
#include <linux/gfp.h>

static struct timer_list *my_timer;

static void my_timer_function(unsigned long data)
{
	struct timer_list *temp_timer = (struct timer_list *) data;

	printk("==================== jiffies: %d\n", jiffies);

	mod_timer(temp_timer, jiffies + 100);
}

static int __init my_timer_init(void)
{
	my_timer = kzalloc(sizeof(*my_timer), GFP_KERNEL);

	printk("++++++++++++++++++++my_timer_init()\n");

	init_timer(my_timer);
	my_timer->expires = jiffies + 100;
	my_timer->function = my_timer_function;
	my_timer->data = (unsigned long)my_timer;

	mod_timer(my_timer, jiffies + 200);
	printk("==================== jiffies: %d\n", jiffies);

	printk("--------------------my_timer_init()\n");

	return 0;
}

static void __exit my_timer_exit(void)
{
	del_timer_sync(my_timer);

	kfree(my_timer);

	printk("--------------------my_timer_exit()\n");
}

module_init(my_timer_init);
module_exit(my_timer_exit);
