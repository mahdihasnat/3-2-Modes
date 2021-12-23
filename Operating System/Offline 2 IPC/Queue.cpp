//
// Created by mahdi on 12/23/2021.
//

#ifndef QUEUE_CPP
#define QUEUE_CPP

#include "Queue.h"


template<typename T>
Queue<T>::Queue() : mutex(1) {
}

template<typename T>
Queue<T>::~Queue() {

}

template<typename T>
void Queue<T>::syn_push(T const &val) {
    mutex.down();
    q.push(val);
    mutex.up();
}

template<typename T>
void Queue<T>::asyn_push(const T &val) {
    return q.push(val);
}

template<typename T>
T Queue<T>::asyn_front() {
    return q.front();
}

template<typename T>
void Queue<T>::asyn_pop() {
    return q.pop();
}

template<typename T>
bool Queue<T>::asyn_empty() {
    return q.empty();
}

template<typename T>
void Queue<T>::lock() {
    return mutex.down();
}

template<typename T>
void Queue<T>::unlock() {
    return mutex.up();
}

#endif //QUEUE_CPP