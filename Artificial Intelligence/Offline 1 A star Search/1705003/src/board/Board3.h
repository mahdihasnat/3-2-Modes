//
// Created by mahdi on 11/27/2021.
//

#ifndef OFFLINE_1_A_STAR_SEARCH_BOARD3_H
#define OFFLINE_1_A_STAR_SEARCH_BOARD3_H


#include <cassert>
#include "Board.h"

class Board3 : public Board{
private:
    int num;
public:
    Board3(int num) : num(num) {}

    Board3(const Board3 & board3):num(board3.num){}

    Board3(const Board2D &board2D)
    {
        assert(board2D.getK() == 3);
        num= 0;
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                num*=9;
                num+=board2D.getVal(i,j);
            }
        }
    }

    Board2D getBoard2D() const override
    {
        Board2D board2D(3);
        int x=num;
        for(int i=8;i>=0;i--)
        {
            board2D.setVal(i/3,i%3,x%9);
            x/=9;
        }
        return board2D;
    }

    Board *clone() override{
        return new Board3(*this);
    }

    Board *nextMove(int direction) override{
        return  new Board3(getBoard2D().nextMove(direction));
    }


    __int128_t getNum() const override {
        return num;
    }

};


#endif //OFFLINE_1_A_STAR_SEARCH_BOARD3_H
