//
// Created by mahdi on 11/27/2021.
//

#include "Board3.h"

Board *Board3::nextMove(int direction) {
    return  new Board3(getBoard2D().nextMove(direction));
}

__int128_t Board3::getNum() const {
    return num;
}

Board *Board3::clone() {
    return new Board3(*this);
}

