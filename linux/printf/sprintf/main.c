#include <stdio.h>

int main()
{
	int i;
	char strbuf[40];
	char buf[17] = {0x11, 0x22, 0x33, 0x44, 0x55, 0x66};

	for(i = 0; i < 17; i++)
		sprintf(strbuf+i*2, "%02x", buf[i]);

	printf("str: %s\n", strbuf);

	return 0;
}
