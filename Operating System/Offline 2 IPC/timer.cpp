//
// Created by mahdi on 12/23/2021.
//

#include "timer.h"
#include <chrono>


#include <bits/stdc++.h>

using namespace std;
using namespace chrono;

time_point <system_clock> start;

void timer_init() {
    start = system_clock::now();
}

void timer_destroy() {

}

string timer_get_time_str() {
    double milisec = duration_cast<milliseconds>(system_clock::now() - start).count();
    cout << milisec << endl;
    double sec = milisec / 1000.0;
    stringstream ss;
    ss << setprecision(3) << fixed << setw(6) << right << sec;
    return ss.str();
}

// int main() {
// 	timer_init();
// 	for(int i=1;i<=2e8;i++){
// 		int x;
// 		x++;
// 	}
// 	cout<<timer_get_time_str()<<endl;
// 	return 0;
// }