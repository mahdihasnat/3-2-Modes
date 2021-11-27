//
// Created by mahdi on 11/27/2021.
//

#include <queue>
#include "astar.h"
#include "map"

class MapCmp
{
public:
    template<typename T>
    bool operator ()(const unique_ptr<T> & lhs , const unique_ptr<T> & rhs) const
    {
        return *(lhs) < *(rhs);
    }
};

class PriorityField
{
public:
    int x;
    Board * board;

    PriorityField(int x, Board *board) : x(x), board(board) {}

};

void astar(Board * startState)
{

    map<unique_ptr<Board > , int , MapCmp> dist;
    priority_queue<PriorityField> priorityQueue;
    unique_ptr<Board> source(startState);
    dist[move(source)] = 0;

    

}