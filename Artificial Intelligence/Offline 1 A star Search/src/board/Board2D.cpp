//
// Created by mahdi on 11/24/2021.
//

#include "Board2D.h"

int dx[4] = {1, 0, -1, 0};
int dy[4] = {0, 1, 0, -1};

char moves[4] = {'R', 'U', 'L', 'D'};

Board2D Board2D::nextMove(int direction) const {
    Board2D ret = *this;
    int k = ret.board.size();
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

    swap(ret.board[x][y], ret.board[xx][yy]);
    return ret;
}

bool Board2D::isValid() const {
    int k = board.size();
    vector<bool> found(k * k, 0);

    for (vector<signed char> row: board) {
        if (board.size() != k)
            return false;
        for (const signed char &elem: row) {
            if (elem < 0 or elem >= k * k)
                return false;
            if (found[elem])
                return false;
            found[elem] = 1;
        }
    }
    return true;
}

Board2D::Board2D(const vector<std::vector<int8_t>> &board) : board(board) {}
