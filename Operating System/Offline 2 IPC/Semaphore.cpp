//
// Created by mahdi on 12/22/2021.
//

#include "Semaphore.h"
#include <semaphore.h>

Semaphore::Semaphore(int value) {
    sem_init(&this->value, 0, value);
}

Semaphore::~Semaphore() {
    sem_destroy(&this->value);
}

void Semaphore::up() {
    sem_post(&this->value);
}

void Semaphore::down() {
    sem_wait(&this->value);
}