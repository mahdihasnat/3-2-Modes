//
// Created by mahdi on 12/22/2021.
//

#ifndef OFFLINE_2_IPC_SEMAPHORE_H
#define OFFLINE_2_IPC_SEMAPHORE_H

#include <semaphore.h>

class Semaphore {
private:
    sem_t value;
public:
    Semaphore(int );
    Semaphore(const Semaphore &) = delete;
    ~Semaphore();
    void up();
    void down();
};


#endif //OFFLINE_2_IPC_SEMAPHORE_H
