//
// Created by mahdi on 12/23/2021.
//

#ifndef OFFLINE_2_IPC_QUEUE_H
#define OFFLINE_2_IPC_QUEUE_H

#include "Semaphore.h"
#include <queue>

using namespace std;

template<typename T>
class Queue {
    // for any operation threed need to acquire lock either syn or  lock() + asyn
    // before calling asyn operation , call lock() first
    // after calling lock() and operations required don't forget to call unlock()
    Semaphore mutex;
    queue<T> q;
public:
    Queue();

    Queue(Queue<T> const &) = delete;

    Queue<T> operator=(Queue<T> const &) = delete;

    ~Queue();

    void asyn_push(T const &);

    void syn_push(T const &);

    T asyn_front();

    void asyn_pop();

    bool asyn_empty();

    void lock();//blocking call

    void unlock();

};

#include "Queue.cpp"

#endif //OFFLINE_2_IPC_QUEUE_H
