//
// Created by mahdi on 11/24/2021.
//

#include "Board2D.h"

const int dx[4] = {1, 0, -1, 0};
const int dy[4] = {0, 1, 0, -1};



Board2D Board2D::nextMove(int direction) const {
    Board2D ret = *this;
    const int k = ret.board.size();
    int x = -1;
    int y = -1;
    for (int i = 0; i < k; i++) {
        for (int j = 0; j < k; j++)
            if (ret.board[i][j] == k * k - 1) {
                x = i;
                y = j;
                break;
            }
    }
    int xx = x + dx[direction];
    int yy = y + dy[direction];

    xx=max(xx,0);
    xx=min(xx,k-1);

    yy=max(yy,0);
    yy=min(yy,k-1);


    swap(ret.board[x][y], ret.board[xx][yy]);
    return ret;
}

bool Board2D::isValid() const {
    int k = (int)board.size();
    vector<bool> found(k * k, false);

    for (vector<signed char> row: board) {
        if (board.size() != k)
            return false;
        for (const signed char &elem: row) {
            if (elem < 0 || elem >= k * k)
                return false;
            if (found[elem])
                return false;
            found[elem] = true;
        }
    }
    return true;
}

Board2D::Board2D(const vector<std::vector<int8_t>> &board) : board(board) {}

const vector<std::vector<int8_t>> &Board2D::getBoard() const {
    return board;
}
