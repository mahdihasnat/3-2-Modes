#include "passenger.h"
#include "kiosk.h"
#include "vip_channel.h"
#include "security.h"
#include "boarding.h"
#include "logger.h"

#include <cstdlib>

Passenger::Passenger(int id, bool is_vip) {
    this->id = id;
    this->is_vip = is_vip;
    this->lost_boarding_pass = rand()%2;
}

bool Passenger::lostBoardingPass() const {
    if(is_vip)
        return false;
    else 
        return lost_boarding_pass;
}

void Passenger::setLostBoardingPass(bool val) {
    lost_boarding_pass = val;
}

void *passenger_fly(void *args) {
    Passenger *p = (Passenger *) args;
    kiosk_self_check(*p);
    if (p->is_vip) {
        vip_channel_to_gate(*p);
    } else {
        security_check(*p);
    }
    while (boarding_check_boarding_pass(*p) == false) {
        vip_channel_to_special_kiosk(*p);
        kiosk_special_check(*p);
        p->setLostBoardingPass(rand()%2);
        vip_channel_to_gate(*p);
    }
    Log{} << (*p) << " has boarded on the plane successfully" << endl;

    delete p;

    return NULL;
}