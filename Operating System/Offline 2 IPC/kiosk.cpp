#include "kiosk.h"
#include <semaphore.h>

#include<unistd.h>


#include <iostream>
using namespace std;

extern int w;
extern int m;

sem_t empty;
int count = 1;

void kiosk_init()
{
	sem_init(&empty,0,m);
	count=1;
}

void kiosk_destroy()
{
	sem_destroy(&empty);
}

void kiosk_self_check(const Passenger & p)
{
	cout<<p<<" has started waiting in kiosk"<<endl;
	sem_wait(&empty);
	cout<<p<<" has entered the kiosk "<<endl;

	sleep(w);
	
	cout<<p<<" has got his boarding pass"<<endl;
	sem_post(&empty);

}