//
// Created by mahdi on 11/24/2021.
//

#ifndef OFFLINE_1_A_STAR_SEARCH_BOARD2D_H
#define OFFLINE_1_A_STAR_SEARCH_BOARD2D_H

#include <vector>
#include <cstdint>
#include <iostream>
#include <iomanip>

using namespace std;

class Board2D {
private:
    std::vector<std::vector<int8_t> > board;

public:
    explicit Board2D(const int &k) : board(k, vector<int8_t>(k, 0)) {
    }

    Board2D(const vector<std::vector<int8_t>> &board);

    Board2D nextMove(int direction) const;

    bool isValid() const;

    void setVal(int i,int  j,int x)
    {
        board[i][j] = x;
    }

    int getK() const
    {
        return board.size();
    }

    int8_t getVal(int i,int j) const
    {
        return board[i][j];
    }

    bool isFInal() {
        int k = board.size();
        for (int i = 0; i < k; i++) {
            for (int j = 1; j < k; j++)
                if (board[i][j] <= board[i][j - 1])
                    return false;
        }
        for (int i = 1; i < k; i++)
            if (board[i][0] <= board[i - 1][k - 1])
                return false;
        return true;
    }

    friend istream &operator>>(istream &is, Board2D &d) {
        int k = d.board.size();
        for (int i = 0; i < k; i++)
            for (int j = 0; j < k; j++) {
                int x;
                is >> x;
                d.board[i][j] = x;
            }
        return is;
    }

    friend ostream &operator<<(ostream &os, const Board2D &d) {
        const int k = d.board.size();
#define rowPrint(os, k, c) {int x=k;while(x--) (os)<<c;os<<"\n";}
        rowPrint(os, k * 3 + 1, '-')
        for (const vector<int8_t> &row: d.board) {
            for (const int8_t &element: row)
                os << "|" << setw(2) << (int) element;
            os << "|\n";
            rowPrint(os, k * 3 + 1, '-')
        }
        return os;
    }


};


#endif //OFFLINE_1_A_STAR_SEARCH_BOARD2D_H
