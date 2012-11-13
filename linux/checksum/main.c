//------------------------------------------------------------------------------   
//  循环冗余校验单元 checksum.cpp（仅作为教学演示使用）   
//  功能：实现（1）实现对字符流的循环冗余校验模拟   
//  作者：邓飞 2004.8. 成都理工大学--计算机工程系   
//  **声明：任何个人和团体均可以使用该单元文件，但需要保留原作者姓名**   
//------------------------------------------------------------------------------   
#include <stdio.h>   
#include <string.h>
   
typedef unsigned int DWord;   
typedef unsigned short Word;   
typedef unsigned char Byte;   
const Word CRC_16 = 0xC005;   
   
//------------------------------------------------------------------------------   
//  函数：gCRCCheckSum()   
//  功能：对输入字节序列，进行冗余校验，（1）如果pBuf后两个字节为0   
//        则计算结果为校验和；（2）如果不为0则对序列进行校验（计算后后两个字节   
//        不为0则说明有误）   
//  输入：pBuf字节序列（序列长度应该等于count+2，后两个字节留给校验和），   
//        count需要求取校验和的字节数目   
//  输出：校验和，存放到pBuf的最后两个字节中   
//------------------------------------------------------------------------------   
void gCRCCheckSum(Byte *pBuf, int count)   
{   
    int i, j;   
    Word crc;   //冗余校验结果寄存变量   
       
    //（1）将头两个字节放到寄存变量中   
    crc = pBuf[0] < 8 | pBuf[1];   
    //（2）对字节序列中的各个字节循环移位相除   
    for(i=2; i<count+2; i++)   
    {   
        for(j=7; j>=0; j--)   
        {   
            if(crc & 0x8000)   
            {   
                crc = crc ^ CRC_16;   
            }   
            crc <= 1;   
            crc |= ((pBuf[i] >> j) & 0x01);   
        }   
    }   
    //（4）将结果填入字节序列的最后两位   
    pBuf[count] = (Byte)(crc >> 8);   
    pBuf[count+1] = (Byte)(crc);   
}   
   
int main()   
{   
    int i, count;   
    char c;   
//    Byte buf[6] = {1, 2, 3, 4, 0, 0};   
	Byte buf[256] = {0x01, 0x0A, 0x55, 0x55, 0x55, 0x55, 0x55, 0x55, 0x55, 0x55, 0x55, 0x55};   
    Byte input[4];   
	count = 12;
       
    printf("*---------------------------------------------------------*\n");   
    printf("*  循环冗余校验算法教学演示--成都理工大学    邓飞 2004.8  *\n");   
    printf("*---------------------------------------------------------*\n");   
       
    do   
    {   
//        buf[4] = 0;     buf[5] = 0;   
		buf[count] = 0;     buf[count+1] = 0;   
        printf("\n\n原始序列  ");   
//        for(i=0; i<4; i++) printf("%08x ", buf[i]);   
        for(i=0; i<count; i++) printf("%02x ", buf[i]);   
        printf("\n");   
           
        gCRCCheckSum(buf, count);   
        printf("发送序列  ");   
//        for(i=0; i<4; i++) printf("%08x ", buf[i]);   
        for(i=0; i<count; i++) printf("%02x ", buf[i]);   
        printf("校验和(%02x%02x)", buf[i], buf[i+1]);   
        printf("\n");   

		break;	//FIXME: for test
           
        printf("请输入接收序列（4字节以空格间隔）\n");   
        printf("          ");   
        scanf("%8b %8b %8b %8b", &input[0], &input[1], &input[2], &input[3]);   
        for(i=0; i<4; i++) buf[i] = input[i];   
        gCRCCheckSum(buf, 4);   
        printf("接收序列  ");   
        for(i=0; i<4; i++) printf("%08b ", buf[i]);   
        printf("校验和(%02x%02x)\n", buf[i], buf[i+1]);    
        if(buf[4] == 0 && buf[5] == 0) printf("接收序列正确\n\n");   
        else printf("接收序列错误\n\n");   
           
        printf("继续(y/n)？");   
        do   
        {   
            c = getchar();   
        }while(c != 'y' && c != 'Y' && c != 'n' && c != 'N');   
    }while(c != 'n' && c != 'N');     
       
	return 0;
}   
