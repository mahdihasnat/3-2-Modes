//
// Created by mahdi on 12/23/2021.
//

#include "Queue.h"

template<class T>
Queue<T>::Queue() : mutex(1) {
}

template<class T>
Queue<T>::~Queue() {

}

template<class T>
void Queue<T>::syn_push(T const &val) {
    mutex.down();
    q.push(val);
    mutex.up();
}

template<class T>
void Queue<T>::asyn_push(const T &val) {
    return q.push(val);
}

template<class T>
T Queue<T>::asyn_front() {
    T ret = q.front();
    return ret;
}

template<class T>
void Queue<T>::asyn_pop() {
    return q.pop();
}

template<class T>
bool Queue<T>::asyn_empty() {
    return q.empty();
}

template<class T>
void Queue<T>::lock() {
    mutex.down();
}

template<class T>
void Queue<T>::unlock() {
    mutex.up();
}
