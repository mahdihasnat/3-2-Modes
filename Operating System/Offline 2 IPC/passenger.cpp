#include "Passenger.h"

Passenger::Passenger(int id, bool is_vip)
{
	this->id = id;
	this->is_vip = is_vip;
}

void *Passenger::fly()
{
	kiosk_self_check(*this);
	return NULL;
}
void * passenger_fly(void *args)
{
	Passenger *p = (Passenger *)args;
	reinterpret_cast<Passenger *>(p)->fly();
	return NULL;
}