//
// Created by mahdi on 12/22/2021.
//

#include "vip_channel.h"
#include "logger.h"
#include "Sleep.h"


Semaphore available_to_kiosk(1);

int count_to_gate = 0;
int count_to_special_kiosk = 0;

Semaphore mutex_count_to_gate(1);
Semaphore mutex_count_to_special_kiosk(1);
Semaphore mutex_vip_channel(1);

extern int z;

void vip_channel_to_gate(Passenger const &p) {
    log{} << p << " has arrived at kiosk side of vip channel" << endl;

    mutex_count_to_gate.down();
    {
        count_to_gate++;
        if (count_to_gate == 1)
        {
            available_to_kiosk.down();
            mutex_vip_channel.down();
        }
    }
    mutex_count_to_gate.up();

    log{} << p << " has started crossing vip channel to gate" << endl;

    sleep_milliseconds(z);

    log{} << p << " has crossed vip channel to gate" << endl;

    mutex_count_to_gate.down();
    {
        count_to_gate--;
        if (count_to_gate == 0)
        {
            mutex_vip_channel.up();
            available_to_kiosk.up();
        }
    }
    mutex_count_to_gate.up();
}

void vip_channel_to_special_kiosk(Passenger const &p) {
    log{} << p << " has arrived at gate side of vip channel" << endl;

    available_to_kiosk.down();
    available_to_kiosk.up();

    mutex_count_to_special_kiosk.down();
    {
        count_to_special_kiosk++;
        if (count_to_special_kiosk == 1)
        {
            mutex_vip_channel.down();
        }
    }
    mutex_count_to_special_kiosk.up();

    log{} << p << " has started crossing vip channel to special kiosk" << endl;

    sleep_milliseconds(z);

    log{} << p << " has crossed vip channel to special kiosk" << endl;

    mutex_count_to_special_kiosk.down();
    {
        count_to_special_kiosk--;
        if (count_to_special_kiosk == 0)
        {
            mutex_vip_channel.up();
        }
    }
    mutex_count_to_special_kiosk.up();

}