#include "types.h"
#include "user.h"

int main(int argc,char *argv[])
{
	printf(1,"total argument: %d\n",argc);
	
	int *arr = (int *)malloc(sizeof(int)*(argc-1));
	int i=0;
	for(i=0;i<argc-1;i++)
	{
		arr[i] = atoi(argv[i+1]);
	}
	int sum;
	sum=add(argc-1,arr);
	printf(1,"sum: %d\n",sum);
	free(arr);
	exit();
}