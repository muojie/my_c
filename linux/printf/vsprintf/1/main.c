#include <stdio.h>

int main()
{
	int buf;
	char input[10] = "0x100000";
	int i;

	i = sscanf(input, "0x%x", &buf);

	printf("sscanf num %d : %x\n", i, buf);

	return 0;
}

