#ifndef PASSENGER_H
#define PASSENGER_H
#include <iostream>
using namespace std;

class Passenger
{
	
public:
	int id;
	Passenger(int id)
	{
		this->id = id;
	}
	~Passenger();

	friend ostream &operator<<(ostream &os, const Passenger &p)
	{
		os << "Passenger id: " << p.id << endl;
		return os;
	}

	friend istream &operator>>(istream &is, Passenger &p)
	{
		is >> p.id;
		return is;
	}

};

#endif