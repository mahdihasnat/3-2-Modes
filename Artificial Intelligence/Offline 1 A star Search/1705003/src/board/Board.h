//
// Created by mahdi on 11/24/2021.
//

#ifndef OFFLINE_1_A_STAR_SEARCH_BOARD_H
#define OFFLINE_1_A_STAR_SEARCH_BOARD_H

#include "Board2D.h"

class Board
{
private:

public:
    Board(const Board & b);
    Board();

    virtual ~Board() = default;

    virtual Board * nextMove(int direction) = 0;
    Board * prevMove(int direction);
    virtual Board2D getBoard2D() const = 0;
    virtual __int128 getNum() const = 0;
    virtual Board * clone() = 0;

    bool operator <(const Board & board) const
    {
        __int128 l = getNum();
        __int128 r = getNum();
        if(l==0 or r==0)
            return getBoard2D() < board.getBoard2D();

        return getNum()<board.getNum();
    };

    friend ostream &operator <<(ostream & os , const Board & b)
    {
        return os<<(b.getBoard2D());
    }
};


#endif //OFFLINE_1_A_STAR_SEARCH_BOARD_H
