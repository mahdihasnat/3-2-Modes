//
// Created by mahdi on 11/27/2021.
//

#include <queue>
#include <ostream>
#include <cassert>
#include "astar.h"
#include "map"

#define DBG(a) cerr << "line " << __LINE__ << " : " << #a << " --> " << (a) << endl
#define NL cout << endl

std::ostream &
operator<<(std::ostream &dest, __int128_t value) {
    std::ostream::sentry s(dest);
    if (s) {
        __uint128_t tmp = value < 0 ? -value : value;
        char buffer[128];
        char *d = std::end(buffer);
        do {
            --d;
            *d = "0123456789"[tmp % 10];
            tmp /= 10;
        } while (tmp != 0);
        if (value < 0) {
            --d;
            *d = '-';
        }
        int len = std::end(buffer) - d;
        if (dest.rdbuf()->sputn(d, len) != len) {
            dest.setstate(std::ios_base::badbit);
        }
    }
    return dest;
}


const char moves[4] = {'D', 'R', 'U', 'L'};

class MapCmp {
public:
    bool operator()(Board *l, Board *r) const {
        return (*l) < (*r);
    }
};

class PriorityField {
public:

    int f; // g(board) + h(board)
    Board *board;

    PriorityField(int f, Board *board) : f(f), board(board) {}

    bool operator<(const PriorityField &priorityField) const {
        return this->f > priorityField.f;
    }

    friend ostream &operator<<(ostream &os, const PriorityField &field) {
        os << "f: " << field.f << " board: " << field.board;
        return os;
    }

};

void clearMap(map<Board *, int, MapCmp> &mp)
{
    for(auto i: mp)
        delete i.first;
    mp.clear();
}

void clearPQ(priority_queue<PriorityField> &pq)
{
    while (!pq.empty())
    {
        auto i = pq.top();
        pq.pop();

        delete i.board;
    }
}

void printPath(map<Board *, int, MapCmp> &prevMove , Board * finalNode)
{
    assert(prevMove.find(finalNode) != prevMove.end());
    int dir = prevMove[finalNode];
    if(dir == -1)
    {
        cout<<finalNode->getBoard2D();
        return;
    }
    else
    {
        Board * pre = finalNode->nextMove(dir^2);
        printPath(prevMove , pre );
        delete pre;
        cout<<"---------------------------------"<<moves[dir]<<"-------------------------------"<<endl;
        cout<<finalNode->getBoard2D();
        return;
    }
}

int astar(Board *startBoard, int (*heuristic)(const Board2D &board2D) ) {

    priority_queue<PriorityField> pq;
    map<Board *, int, MapCmp> dist;
    map<Board *, int, MapCmp> prevMove;

    Board *startBoard1 = startBoard->clone();
    dist[startBoard1] = 0;

    Board *startBoard2 = startBoard->clone();
    pq.push({0 + heuristic(startBoard2->getBoard2D()), startBoard2});

    Board *startBoard3 = startBoard->clone();
    prevMove[startBoard3]=-1;

    int totalExplored = 0;
    int totalExpanded = 0;

    while (!pq.empty()) {

        PriorityField priorityField = pq.top();
        pq.pop();

        totalExpanded++;


        int f = priorityField.f;
        Board *board = priorityField.board;
        int g = f - heuristic(board->getBoard2D());

        /*
        DBG(priorityField);
        DBG(board->getBoard2D());
        DBG(f);
        DBG(g);
*/

        if (board->getBoard2D().isFInal()) {
            /*
            cout << "Final Found" << "\n";
            DBG(totalExplored);
            DBG(totalExpanded);
            DBG(g);
             */

            cout<<"Total Move :"<<g<<endl;
            cout<<"Total Explored:"<<dist.size()<<endl;
            cout<<"Total Expanded:"<<totalExpanded<<endl;
            printPath(prevMove , board);


            delete board;
            clearMap(dist);
            clearMap(prevMove);
            clearPQ(pq);
            return g;
        }


        if (dist[board] != g) {
            delete board;
            continue;
        }


        for (int direction = 0; direction < 4; direction++) {

            Board *nextBoard = board->nextMove(direction);



            if (dist.find(nextBoard) == dist.end()) {

                Board *nextBoard1 = nextBoard->clone();
                pq.push({g + 1 + heuristic(nextBoard1->getBoard2D()), nextBoard1});

                dist[nextBoard] = g + 1;

                Board * nextBoard2 = nextBoard->clone();
                prevMove[nextBoard2] = direction;

                assert(dist[nextBoard1] == g + 1);

            } else if (dist[nextBoard] > g + 1) {
                pq.push({g + 1 + heuristic(nextBoard->getBoard2D()), nextBoard});

                dist[nextBoard] = g + 1;
                prevMove[nextBoard] = direction;

            } else {
                delete nextBoard;
            }
        }
        delete board;
    }
    /*
    cout << "No final state";
    DBG(totalExplored);
    DBG(totalExpanded);
*/
    clearMap(dist);
    clearMap(prevMove);
    return -1;
}