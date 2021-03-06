//
// Created by mahdi on 11/24/2021.
//

#include "Board2D.h"
#include <set>

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

    xx = max(xx, 0);
    xx = min(xx, k - 1);

    yy = max(yy, 0);
    yy = min(yy, k - 1);


    swap(ret.board[x][y], ret.board[xx][yy]);
    return ret;
}

bool Board2D::isValid() const {
    int k = (int) board.size();
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

bool Board2D::isSolvable() const {
    int k = getK();
    if (k % 2 == 1)
        return totalInversion() % 2 == 0;
    else {
        int blankRow = -1;
        for (int i = 0; i < k and blankRow == -1; i++) {
            for (int j = 0; j < k; j++) {
                if (board[i][j] == k * k - 1) {
                    blankRow = i;
                    break;
                }
            }
        }
        //cout<<"inv:"<<(totalInversion())<<endl;
        return  ((blankRow % 2) xor (totalInversion() % 2));
    }
}

int Board2D::totalInversion() const {
    int k = getK();
    int n = k * k - 1;
    int ret = 0;
    set<int> st;
    for (int i = k - 1; i >= 0; i--) {
        for (int j = k - 1; j >= 0; j--) {
            if (board[i][j] == n) continue;
            for (int x: st) {
                if (x > board[i][j]) break;
                ret++;
            }
            st.insert(board[i][j]);
        }
    }
    return ret;
}

Board2D::Board2D(vector<vector<int>> board) :board(board.size() , vector<int8_t>(board.size())) {
    for(int i=0;i<board.size();i++)
    {
        for(int j=0;j<board.size();j++)
        {
            this->board[i][j]=board[i][j];
        }
    }
}
