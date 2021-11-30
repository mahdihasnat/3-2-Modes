//
// Created by mahdi on 11/29/2021.
//

#include "RowLinearConflict.h"
#include "Manhattan.h"

int RowLinearConflict(const Board2D &board2D) {
    auto board = board2D.getBoard();
    int k=board2D.getK();
    int ret =Manhattan(board2D);

    for(int i=0;i<k;i++)
    {
        
    }

}
