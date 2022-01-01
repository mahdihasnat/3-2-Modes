//
// Created by mahdi on 12/22/2021.
//

#include "SecurityBelt.h"

#include "logger.h"

#include "Sleep.h"

extern int x;

SecurityBelt::SecurityBelt(int id, int maximumPassenger) : available(maximumPassenger), id(id) {
}

void SecurityBelt::check_security(const Passenger &p) {
    Log{} << p << " has started waiting for security check in belt " << id << endl;
    available.down();

    Log{} << p << " has started the security check in belt " << id << endl;

    sleep_milliseconds(x);

    Log{} << p << " has crossed the security check in belt " << id << endl;
    available.up();
}