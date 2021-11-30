//
// Created by mahdi on 11/30/2021.
//

#include "LinearConflict.h"
#include "Manhattan.h"

int LinearConflict(const Board2D &board2D) {
    int ret=Manhattan(board2D);
    int k=board2D.getK();
    auto board = board2D.getBoard();
    for(int r=0;r<k;r++)
    {

        for(int i=0;i<k;i++)
        {
            for(int j=i+1;j<k;j++)
            {
                ret+= 2 * (  board[r][i]/k == r and board[r][j]/k ==r and board[r][i] > board[r][j] );
            }
        }
    }
    return ret;
}
