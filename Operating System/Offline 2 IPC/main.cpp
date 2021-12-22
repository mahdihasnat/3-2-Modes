#include<bits/stdc++.h>
using namespace std;

#include "kiosk.h"
#include "passenger.h"
#include "security.h"

int m,n,p;
int w,x,y,z;

void system_init()
{

	m=2;
	n=1;
	p=1;
	w=0;
	x=0;
	y=0;
	z=1;

	kiosk_init();
	security_init();
}

void system_destroy()
{
	kiosk_destroy();
	security_destroy();
}

int main()
{
	
	cout<<"hellow\n";
	
	system_init();

	const int totalP=10;
	Passenger ** p = new Passenger *[totalP];
	pthread_t * pth = new pthread_t[totalP];

	for(int i=0;i<totalP;i++)
	{
		p[i]=new Passenger(i,true);
		pthread_create(pth + i,NULL,passenger_fly,p[i]);
	}

	for(int i=0;i<totalP;i++)
	{
		pthread_join(pth[i],NULL);
	}

	for(int i=0;i<totalP;i++)
	{
		delete p[i];
	}
	delete p;
	delete pth;

	system_destroy();

}