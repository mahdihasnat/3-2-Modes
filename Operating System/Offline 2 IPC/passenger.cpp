#include "Passenger.h"
#include "kiosk.h"
#include "vip_channel.h"
#include "security.h"
#include "boarding.h"
#include "logger.h"

Passenger::Passenger(int id, bool is_vip) {
    this->id = id;
    this->is_vip = is_vip;
    this->lost_boarding_pass = false;
}

bool Passenger::lostBoardingPass() const {
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
        p->setLostBoardingPass(false);
        vip_channel_to_gate(*p);
    }
    log{} << (*p) << " has boarded on the plane successfully" << endl;

    return NULL;
}