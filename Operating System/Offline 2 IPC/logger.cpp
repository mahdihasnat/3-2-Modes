//
// Created by mahdi on 12/22/2021.
//

#include "logger.h"

Semaphore log::mutex(1);

log::~log() {
    mutex.down();
    cout << this->str();
    mutex.up();
}