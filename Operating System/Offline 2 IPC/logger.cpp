//
// Created by mahdi on 12/22/2021.
//

#include "logger.h"
#include "timer.h"

Semaphore Log::mutex(1);

Log::~Log() {
    mutex.down();
    cout << "[" << timer_get_time_str() << "] " << this->str();
    mutex.up();
}