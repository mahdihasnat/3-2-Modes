#include "types.h"
#include "user.h"
#include "date.h"

int
main(int argc,char ** argv)
{
	rtcdate d;
	if(date(&d) < 0)
	{
		printf(1,"error in date system call\n");
		exit();
	}
	printf(1,"%d-%d-%d %d:%d:%d\n",d.year,d.month,d.day,d.hour,d.minute,d.second);
	exit();
}