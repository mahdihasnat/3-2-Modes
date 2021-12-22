//
// Created by mahdi on 12/22/2021.
//

#include "Sleep.h"
#include <chrono>
#include <thread>

void sleep_milliseconds(int milisec)
{
    std::this_thread::sleep_for(std::chrono::milliseconds(milisec));
}