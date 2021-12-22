#ifndef KIOSK_ADDED
#define KIOSK_ADDED

#include "passenger.h"

void kiosk_init();
void kiosk_self_check(const Passenger & p);
void kiosk_destroy();

#endif