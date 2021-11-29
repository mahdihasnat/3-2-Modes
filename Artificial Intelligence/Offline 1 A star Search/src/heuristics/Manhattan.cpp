//
// Created by mahdi on 11/29/2021.
//

#include "Manhattan.h"

int Manhattan(const Board2D &board2D) {
    int ret=0;
    int k=board2D.getK();
    int n=k*k-1;
    auto board = board2D.getBoard();

    for(int i=0;i<k;i++)
    {
        for(int j=0;j<k;j++)
        {
            ret+=board[i][j]!=n?abs(i - board[i][j]/k)+abs(j-board[i][j]%k):0;
        }
    }
    return ret;
}
