#define DBG(a) cerr << "line " << __LINE__ << " : " << #a << " --> " << (a) << endl
#define NL cout << endl

#include <iostream>
#include "board/Board2D.h"
#include "search/astar.h"
#include "board/Board3.h"
#include "heuristics/Hamming.h"
#include "heuristics/Dummy.h"
#include "heuristics/Manhattan.h"
#include "board/Board4.h"
#include "heuristics/LinearConflict.h"

using namespace std;

void Lightoj1139()
{
    freopen("out.txt","w+",stdout);
    int T,cs=0;
    cin>>T;
    while (T--)
    {
        const int k=3;
        vector<vector<int>> arr(k, vector<int>(k));
        for(int i=0;i<k;i++)
        {
            for(int j=0;j<k;j++)
            {
                cin>>arr[i][j];
                if(arr[i][j] == 0)
                    arr[i][j] = k*k-1;
                else arr[i][j]--;
            }
        }
        Board2D board2D(arr);
        if(board2D.isSolvable())
        {
            Board3 board4(board2D);
            cout<<"Case "<<++cs<<": "<<astar(&board4,&Manhattan)<<"\n";
        }
        else cout<<"Case "<<++cs<<": impossible\n";
    }
}

int main() {
    DBG(freopen("in.txt","r+",stdin));
    Lightoj1139();
    /*
    Board2D b(3);

    cin>>b;

    Board3 b3(b);

    cout<<b3;

    if(!b.isValid())
    {
        DBG("input is not valid\n");
        return -1;
    }
    astar(&b3,&Hamming);
     */

    return 0;
}
