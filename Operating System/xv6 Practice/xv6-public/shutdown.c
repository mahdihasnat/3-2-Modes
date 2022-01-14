#include "types.h"
#include "user.h"

int
main(int argc,char *argv[])
{
	printf(1,"Are u sure to shutdown?[Y/N]:");
	char c;
	if(read(0,&c,1)<0)
	{
		printf(1,"read error\n");
		exit();
	}
	char cc;
	while(read(0,&cc,1)>0)
	{
		if(cc=='\n' || cc=='\r')
			break;
	}
	if(c=='Y'||c=='y')
	{
		shutdown();
	}
	exit();
}