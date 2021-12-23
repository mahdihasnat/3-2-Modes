#include<bits/stdc++.h>

using namespace std;

#include "kiosk.h"
#include "passenger.h"
#include "security.h"

int m, n, p;
int w, x, y, z;

void system_init() {

    m = 10;
    n = 1;
    p = 5;
    w = 0;
    x = 0;
    y = 0;
    z = 0;

    cin>>m>>n>>p;
    cin>>w>>x>>y>>z;

    kiosk_init(m);
    security_init();
}

void system_destroy() {
    kiosk_destroy();
    security_destroy();
}

int main(int argc,char *argv[]) {

    if(argc ==1 )
    {
        cout<<"Usage: "<<argv[0]<<" <input_file> [<output_file>]"<<endl;
        return 0;
    }

    freopen(argv[1],"r",stdin);

    if(argc >= 3)
        freopen(argv[2],"w",stdout);

    system_init();

    const int totalP = 10;
    Passenger **passenger = new Passenger *[totalP];
    pthread_t *pth = new pthread_t[totalP];

    for (int i = 0; i < totalP; i++) {
        passenger[i] = new Passenger(i, false);
        pthread_create(pth + i, NULL, passenger_fly, passenger[i]);
    }

    for (int i = 0; i < totalP; i++) {
        pthread_join(pth[i], NULL);
    }

    for (int i = 0; i < totalP; i++) {
        delete passenger[i];
    }
    delete[] passenger;
    delete[] pth;

    system_destroy();

}