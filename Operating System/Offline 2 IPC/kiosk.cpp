#include "kiosk.h"
#include "Semaphore.h"
#include "logger.h"

#include<unistd.h>
#include <iostream>
using namespace std;

extern int w;
extern int m;

Semaphore * available;

void kiosk_init()
{
	available = new Semaphore(m);
}

void kiosk_destroy()
{
	delete available;
}

void kiosk_self_check(const Passenger & p)
{
	//log{}<<p<<" has started waiting in kiosk"<<endl;
	available->down();
    log{}<<p<<" has entered the kiosk "<<endl;

	sleep(w);

    log{}<<p<<" has got his boarding pass"<<endl;
	available->up();

}