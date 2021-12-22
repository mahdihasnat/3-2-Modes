//
// Created by mahdi on 12/22/2021.
//

#include "security.h"
#include "SecurityBelt.h"
#include <stdlib.h>

extern int n;
extern int p;

SecurityBelt **sb;

void security_init() {
    sb = new SecurityBelt *[n];
    for (int i = 0; i < n; i++)
        sb[i] = new SecurityBelt(i + 1, p);
}

void security_destroy() {
    for (int i = 0; i < n; i++)
        delete sb[i];
    delete[] sb;
}

void security_check(Passenger const &passenger) {
    int belt_id = rand() % n;
    sb[belt_id]->check_security(passenger);
}   