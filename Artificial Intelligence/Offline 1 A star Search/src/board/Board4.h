//
// Created by mahdi on 11/29/2021.
//

#ifndef OFFLINE_1_A_STAR_SEARCH_BOARD4_H
#define OFFLINE_1_A_STAR_SEARCH_BOARD4_H


#include <cassert>
#include "Board.h"

class Board4 : public Board{
private:
    unsigned long long num;
public:
    Board4(const Board4 & board3):num(board3.num){}

    Board4(const Board2D &board2D)
    {
        assert(board2D.getK() == 4);
        num= 0;
        for(int i=0;i<4;i++)
        {
            for(int j=0;j<4;j++)
            {
                num*=16ULL;
                num+=board2D.getVal(i,j);
            }
        }
    }

    Board2D getBoard2D() const override
    {
        Board2D board2D(4);
        unsigned long long x=num;
        for(int i=15;i>=0;i--)
        {
            board2D.setVal(i/4,i%4,x%16LLU );
            x/=16LLU;
        }
        return board2D;
    }

    Board *clone() override{
        return new Board4(*this);
    }

    Board *nextMove(int direction) override{
        return  new Board4(getBoard2D().nextMove(direction));
    }


    __int128_t getNum() const override {
        return num;
    }
};


#endif //OFFLINE_1_A_STAR_SEARCH_BOARD4_H
