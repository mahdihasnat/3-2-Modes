//
// Created by mahdi on 12/22/2021.
//

#ifndef OFFLINE_2_IPC_LOGGER_H
#define OFFLINE_2_IPC_LOGGER_H
#include <iostream>
#include <sstream>
#include "Semaphore.h"
using namespace std;
class log : public ostringstream
{
public:
    ~log();
private:
    static Semaphore mutex;
};

#endif //OFFLINE_2_IPC_LOGGER_H
