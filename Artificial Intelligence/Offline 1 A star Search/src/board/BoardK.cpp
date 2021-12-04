//
// Created by mahdi on 12/4/2021.
//

#include "BoardK.h"

Board *BoardK::nextMove(int direction) {
    return new BoardK(board2D.nextMove(direction));
}

Board2D BoardK::getBoard2D() const {
    return board2D;
}

__int128_t BoardK::getNum() const {
    return 0;
}

Board *BoardK::clone() {
    return new BoardK(*this);
}

BoardK::BoardK(const Board2D &board2D) : board2D(board2D) {}
