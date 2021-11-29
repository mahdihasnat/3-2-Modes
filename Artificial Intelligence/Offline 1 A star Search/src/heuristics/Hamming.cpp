//
// Created by mahdi on 11/28/2021.
//

#include "Hamming.h"

int Hamming(const Board2D &board2D) {
    auto board = board2D.getBoard();
    int k = board.size();
    int n = k*k -1;
    int ret=n;
    for(int i=0;i<n;i++)
    {
        ret-= board[i/k][i%k] == i;
    }
    return ret;
}
