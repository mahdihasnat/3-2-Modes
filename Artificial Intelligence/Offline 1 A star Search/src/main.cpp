#define DBG(a) cerr << "line " << __LINE__ << " : " << #a << " --> " << (a) << endl
#define NL cout << endl

#include <iostream>
#include "board/Board2D.h"
#include "search/astar.h"
#include "board/Board3.h"
#include "heuristics/Hamming.h"
#include "heuristics/Dummy.h"

using namespace std;

int main() {
    DBG(freopen("in.txt","r+",stdin));
    Board2D b(3);

    cin>>b;

    Board3 b3(b);

    cout<<b3;

    if(!b.isValid())
    {
        DBG("input is not valid\n");
        return -1;
    }
    astar(&b3,&Dummy);

    return 0;
}
