//
// Created by mahdi on 12/22/2021.
//

#include "boarding.h"
#include "Semaphore.h"
#include "Sleep.h"
#include "logger.h"

Semaphore mutex_officer(1);
extern int y;

bool boarding_check_boarding_pass(Passenger const &passenger) {
    log{} << passenger << " has started waiting at boarding gate" << endl;
    bool ret;
    mutex_officer.down();
    log{} << "Boarding officer has started checking boarding pass of " << passenger << endl;

    if (passenger.lostBoardingPass()) {
        ret = false;
        log{} << passenger << " failed to show boarding pass to officer" << endl;

    } else {
        ret = true;
        log{} << passenger << " started showing boarding pass to officer" << endl;

        sleep_milliseconds(y);

        log{} << "Boarding Officer checked boarding pass of " << passenger << " successfully" << endl;
    }

    mutex_officer.up();
    return ret;
}