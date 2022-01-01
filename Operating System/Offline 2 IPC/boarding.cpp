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
    Log{} << passenger << " has started waiting at boarding gate" << endl;
    bool ret;
    mutex_officer.down();
    Log{} << "Boarding officer has started checking boarding pass of " << passenger << endl;

    if (passenger.lostBoardingPass()) {
        ret = false;
        Log{} << passenger << " failed to show boarding pass to officer" << endl;

    } else {
        ret = true;
        Log{} << passenger << " started showing boarding pass to officer" << endl;

        sleep_milliseconds(y);

        Log{} << "Boarding Officer checked boarding pass of " << passenger << " successfully" << endl;
    }

    mutex_officer.up();
    return ret;
}