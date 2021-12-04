//
// Created by mahdi on 11/24/2021.
//

#include "Board.h"

Board::Board() {}

Board * Board::prevMove(int direction)
{
    return nextMove(direction^2);
}
