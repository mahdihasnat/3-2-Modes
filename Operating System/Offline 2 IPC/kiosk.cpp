#include "kiosk.h"
#include "Semaphore.h"
#include "logger.h"

#include "Sleep.h"
#include <iostream>

using namespace std;

extern int w;

Semaphore *available;

Semaphore mutex_special_kiosk(1);

void kiosk_init(int totalKiosk) {
    available = new Semaphore(totalKiosk);
}

void kiosk_destroy() {
    delete available;
}

void kiosk_self_check(const Passenger &p) {
    log{} << p << " has started waiting in kiosk" << endl;

    available->down();
    log{} << p << " has entered the kiosk " << endl;

    sleep_milliseconds(w);

    log{} << p << " has got his boarding pass from kiosk" << endl;
    available->up();

}


void kiosk_special_check(const Passenger &p) {
    log{} << p << " has started waiting in special kiosk" << endl;

    mutex_special_kiosk.down();
    log{} << p << " has entered the special kiosk " << endl;

    sleep_milliseconds(w);

    log{} << p << " has got his boarding pass from special kiosk" << endl;
    mutex_special_kiosk.up();

}