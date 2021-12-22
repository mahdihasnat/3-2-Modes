//
// Created by mahdi on 12/22/2021.
//

#include "SecurityBelt.h"

#include "logger.h"

#include <unistd.h>

extern int x;

SecurityBelt::SecurityBelt(int id,int maximumPassenger): available(maximumPassenger),id(id)
{

}
void SecurityBelt::check_security(const Passenger & p) {
    log{}<<p<<"  has started waiting for security check in belt "<<id<<endl;
    available.down();
    
    log{}<<p<<"  has starting security check in belt "<<id<<endl;
    
    sleep(x);

    log{}<<p<<"  has finished security check in belt "<<id<<endl;
    available.up();
}