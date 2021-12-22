#include "kiosk.h"
#include "Semaphore.h"
#include "logger.h"

#include "Sleep.h"
#include <iostream>

using namespace std;

extern int w;

Semaphore *available;

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

    log{} << p << " has got his boarding pass" << endl;
    available->up();

}