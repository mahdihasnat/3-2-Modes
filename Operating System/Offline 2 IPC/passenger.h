#ifndef PASSENGER_H
#define PASSENGER_H

#include <iostream>
using namespace std;

class Passenger;

#include "kiosk.h"

class Passenger
{
	
public:
	int id;
	bool is_vip;
	Passenger(int id,bool is_vip)
	{
		this->id = id;
		this->is_vip = is_vip;
	}

	friend ostream &operator<<(ostream &os, const Passenger &p)
	{
		os << "Passenger " << p.id << (p.is_vip ? "(VIP)" : "");
		return os;
	}

	void * fly(void *args)
	{
		kiosk_self_check(*this);
		return NULL;
	}


};

#endif