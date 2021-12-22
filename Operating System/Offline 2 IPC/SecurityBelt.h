//
// Created by mahdi on 12/22/2021.
//

#ifndef OFFLINE_2_IPC_SECURITYBELT_H
#define OFFLINE_2_IPC_SECURITYBELT_H

#include "passenger.h"
#include "Semaphore.h"

class SecurityBelt {
    Semaphore available;
    int id;
public:
    SecurityBelt(int, int);

    void check_security(Passenger const &);
};

#endif //OFFLINE_2_IPC_SECURITYBELT_H
