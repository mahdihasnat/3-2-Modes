//
// Created by mahdi on 12/22/2021.
//

#include "security.h"
#include "SecurityBelt.h"
#include <stdlib.h>
#include <set>
#include <climits>
using namespace std;

extern int n;
extern int p;

SecurityBelt **sb;

int *security_belt_available_slot;
Semaphore security_belt_available_slot_mutex(1);

void security_init()
{
    sb = new SecurityBelt *[n];
    for (int i = 0; i < n; i++)
        sb[i] = new SecurityBelt(i + 1, p);
    security_belt_available_slot = new int[n];
    for (int i = 0; i < n; i++)
        security_belt_available_slot[i] = p;
}

void security_destroy()
{
    for (int i = 0; i < n; i++)
        delete sb[i];
    delete[] sb;
    delete[] security_belt_available_slot;
}

int acquire_belt()
{
    int max_slot = INT_MIN;
    int id = -1;

    security_belt_available_slot_mutex.down();
    for (int i = 0; i < n; i++)
    {
        if (security_belt_available_slot[i] > max_slot)
        {
            max_slot = security_belt_available_slot[i];
            id = i;
        }
    }
    security_belt_available_slot[id]--;
    security_belt_available_slot_mutex.up();

    return id;
}

void release_belt(int belt_id)
{
    security_belt_available_slot_mutex.down();
    security_belt_available_slot[belt_id]++;
    security_belt_available_slot_mutex.up();
}

void security_check(Passenger const &passenger)
{
    // int belt_id = rand() % n;
    int belt_id = acquire_belt();
    sb[belt_id]->check_security(passenger);
    release_belt(belt_id);
}