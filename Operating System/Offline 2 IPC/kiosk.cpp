#include "kiosk.h"
#include <semaphore.h>

#include<unistd.h>


#include <iostream>
using namespace std;

extern int w;
extern int m;

sem_t available;

void kiosk_init()
{
	sem_init(&available,0,m);
}

void kiosk_destroy()
{
	sem_destroy(&available);
}

void kiosk_self_check(const Passenger & p)
{
	cout<<p<<" has started waiting in kiosk"<<endl;
	sem_wait(&available);
	cout<<p<<" has entered the kiosk "<<endl;

	sleep(w);
	
	cout<<p<<" has got his boarding pass"<<endl;
	sem_post(&available);

}