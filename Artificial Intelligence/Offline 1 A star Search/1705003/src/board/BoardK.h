//
// Created by mahdi on 12/4/2021.
//

#ifndef OFFLINE_1_A_STAR_SEARCH_BOARDK_H
#define OFFLINE_1_A_STAR_SEARCH_BOARDK_H


#include "Board.h"

class BoardK : public Board{
Board2D board2D;
public:
    BoardK(const Board2D &board2D);

    BoardK(const BoardK &boardK): board2D(boardK.board2D){}

    Board *nextMove(int direction) override;

    Board2D getBoard2D() const override;

    __int128_t getNum() const override;

    Board *clone() override;
};


#endif //OFFLINE_1_A_STAR_SEARCH_BOARDK_H
