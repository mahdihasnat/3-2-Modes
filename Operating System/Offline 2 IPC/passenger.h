#ifndef PASSENGER_H
#define PASSENGER_H

#include <iostream>

using namespace std;

class Passenger;

#include "kiosk.h"

class Passenger {
    bool lost_boarding_pass;
public:
    int id;
    bool is_vip;

    Passenger(int id, bool is_vip);

    friend ostream &operator<<(ostream &os, const Passenger &p) {
        os << "Passenger " << p.id << (p.is_vip ? "(VIP)" : "");
        return os;
    }

    bool lostBoardingPass() const;

    void setLostBoardingPass(bool);

};

void *passenger_fly(void *args);

#endif