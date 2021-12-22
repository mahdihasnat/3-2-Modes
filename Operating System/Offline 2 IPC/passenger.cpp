#include "Passenger.h"

Passenger::Passenger(int id, bool is_vip)
{
	this->id = id;
	this->is_vip = is_vip;
}

void * passenger_fly(void *args)
{
	Passenger *p = (Passenger *)args;
	kiosk_self_check(*p);
	return NULL;
}