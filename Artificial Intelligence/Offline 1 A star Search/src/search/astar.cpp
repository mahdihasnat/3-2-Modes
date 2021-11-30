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


char moves[4] = {'R', 'U', 'L', 'D'};

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

int astar(Board *startBoard, int (*heuristic)(const Board2D &board2D)) {

    priority_queue<PriorityField> pq;
    map<Board *, int, MapCmp> dist;
    map<Board *, int, MapCmp> prevMove;

    Board *startBoard1 = startBoard->clone();
    dist[startBoard1] = 0;

    Board *startBoard2 = startBoard->clone();
    pq.push({0 + heuristic(startBoard2->getBoard2D()), startBoard2});

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
            return g;
        }


        if (dist[board] != g)
            continue;


        for (int direction = 0; direction < 4; direction++) {
            //DBG(__LINE__);
            Board *nextBoard = board->nextMove(direction);

            //DBG(nextBoard->getBoard2D());

            if (dist.find(nextBoard) == dist.end()) {

                dist[nextBoard] = g + 1;

                Board *nextBoard1 = nextBoard->clone();
                pq.push({g + 1 + heuristic(nextBoard1->getBoard2D()), nextBoard1});

                assert(dist[nextBoard1] == g + 1);

            } else if (dist[nextBoard] > g + 1) {
                dist[nextBoard] = g + 1;

                pq.push({g + 1 + heuristic(nextBoard->getBoard2D()), nextBoard});
            } else {
                delete nextBoard;
            }
        }
    }
    /*
    cout << "No final state";
    DBG(totalExplored);
    DBG(totalExpanded);
*/
    return -1;
}