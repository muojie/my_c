#include <stdio.h>

int main()
{
	char str[4];

	str[0] = 0x3B;
	str[1] = 0x6D;
	str[2] = 0x00;
	str[3] = 0x00;

	printf("str[i] = %x\n", str[0]);

	return 0;
}
