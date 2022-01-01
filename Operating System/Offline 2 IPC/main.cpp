#include <queue>

#include "kiosk.h"
#include "passenger.h"
#include "security.h"
#include "timer.h"
#include "generator.h"
#include "Sleep.h"
#include "logger.h"

using namespace std;

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

    cin >> m >> n >> p;
    cin >> w >> x >> y >> z;

    kiosk_init(m);
    security_init();
    timer_init();
}

void system_destroy() {
    kiosk_destroy();
    security_destroy();
    timer_destroy();
}

int main(int argc, char *argv[]) {

    if (argc == 1) {
        cout << "Usage: " << argv[0] << " <input_file> [<output_file>]" << endl;
        return 0;
    }

    freopen(argv[1], "r", stdin);

    if (argc >= 3)
        freopen(argv[2], "w", stdout);

    system_init();

    double passenger_per_time_unit = 2.0/1.0;
    int total_time_unit=10;

    Generator generator(passenger_per_time_unit);
    queue<pthread_t *> thread_q;
    int new_passenger_number=1;

    for(int i=0;i<total_time_unit;i++){
        
        int passenger_num = generator.generate();
        for(int j=0;j<passenger_num;j++){
            Passenger *passenger = new Passenger( new_passenger_number++ ,rand()%2);
            pthread_t *thread = new pthread_t;
            pthread_create(thread, NULL, passenger_fly, (void *)passenger);
            thread_q.push(thread);
        }
        
        Log{} << "time unit " << i << ": " << passenger_num << " passengers generated" << endl;
        sleep_milliseconds(1);
    }

    while(!thread_q.empty())
    {
        pthread_t *thread = thread_q.front();
        thread_q.pop();
        pthread_join(*thread, NULL);
        delete thread;
    }
    system_destroy();

}