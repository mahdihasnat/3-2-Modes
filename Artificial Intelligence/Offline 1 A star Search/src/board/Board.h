//
// Created by mahdi on 11/24/2021.
//

#ifndef OFFLINE_1_A_STAR_SEARCH_BOARD_H
#define OFFLINE_1_A_STAR_SEARCH_BOARD_H

#include "Board2D.h"

class Board
{
private:
    Board(const Board & b)
    {

    }
public:
    Board();

    virtual ~Board() = default;

    virtual Board * nextMove(int direction) = 0;
    Board * prevMove(int direction);
    virtual Board2D getBoard2D() = 0;
    virtual __int128 getNum() const = 0;

    virtual bool operator <(const Board & board)
    {
        return getNum()<board.getNum();
    };
};


#endif //OFFLINE_1_A_STAR_SEARCH_BOARD_H
