//
// Created by mahdi on 11/30/2021.
//

#include "LinearConflict.h"
#include "Manhattan.h"

int LongestIncreasingSubsequence( const vector<int>  &arr)
{
    vector<int> ans;
    int n = arr.size();
    for (int i = 0; i < n; i++) {
        auto it
                = lower_bound(ans.begin(), ans.end(), arr[i]);
        if (it == ans.end()) {
            ans.push_back(arr[i]);
        }
        else {
            *it = arr[i];
        }
    }
    return ans.size();
}

int minimumMove(vector<int> v)
{
    return (v.size() - LongestIncreasingSubsequence(v))                                   ;
}
int inversionCount(const vector<int> &v)
{
    int ret=0;
    for(int i=0;i<v.size();i++)
    {
        for(int j=i+1;j<v.size();j++)
            ret+=v[i]>v[j];
    }
    return ret;
}

int LinearConflict(const Board2D &board2D) {
    int ret = Manhattan(board2D);
    int k = board2D.getK();
    auto board = board2D.getBoard();
    for (int r = 0; r < k; r++) {
        vector<int> valuesInSameRow;
        for (int i = 0; i < k; i++) {
            if(board[r][i] /k == r and board[r][i]!= k*k-1)
                valuesInSameRow.push_back(board[r][i]);
        }
        //ret+= minimumMove(valuesInSameRow);
        ret+= inversionCount(valuesInSameRow)*2;
    }
    return ret;
}
