//
// Created by mahdi on 12/22/2021.
//

#ifndef OFFLINE_2_IPC_SECURITY_H
#define OFFLINE_2_IPC_SECURITY_H

#include "passenger.h"

void security_init();
void security_destroy();
void security_check(Passenger const & p);
#endif //OFFLINE_2_IPC_SECURITY_H
