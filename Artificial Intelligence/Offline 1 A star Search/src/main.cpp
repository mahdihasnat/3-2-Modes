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
    DBG(freopen("in.txt","r+",stdin));
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
            cout<<"Case "<<++cs<<": "<<astar(&board4,&LinearConflict)<<"\n";
        }
        else cout<<"Case "<<++cs<<": impossible\n";
    }
}

int Assignment()
{
    DBG(freopen("inass.txt","r+",stdin));
    int k;
    cin>>k;
    Board2D board2D(k);
    cin>>board2D;

    if(!board2D.isValid())
    {
        cout<<"Input Board is not valid"<<endl;
        cout<<board2D;
        return -1;
    }
    if(!board2D.isSolvable())
    {
        cout<<"Input Board is not solvable"<<endl;
        cout<<board2D;
        return -1;
    }

    Board * board;
    if(k==3)    board = new Board3(board2D);
    else if(k==4)    board = new Board4(board2D);
    else
    {
        cout<<"Board not implemented\n";
        return -1;
    }
    {
        cout<<("Starting Astar with Hamming:")<<endl;
        astar(board , &Hamming);
        cout<<("Completed Astar with Hamming:")<<endl;
    }
    {
        cout<<("Starting Astar with Manhattan:")<<endl;
        astar(board , &Manhattan);
        cout<<("Completed Astar with Manhattan:")<<endl;
    }
    {
        cout<<("Starting Astar with LinearConflict:")<<endl;
        astar(board , &LinearConflict);
        cout<<("Completed Astar with LinearConflict:")<<endl;
    }
    delete board;

}

int main() {


    //Lightoj1139();
    Assignment();
    return 0;
}
