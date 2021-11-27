#include <iostream>
#include "board/Board2D.h"

using namespace std;

int main() {
    Board2D b({{0,1},{2,3}});
    cout<<b;
    cin>>b;
    cout<<b.isFInal();
    return 0;
}
