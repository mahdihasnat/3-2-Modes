//
// Created by mahdi on 12/1/2021.
//
#include "bits/stdc++.h"
#include "board/Board3.h"

using namespace std;

void gen3()
{
    int tot = 0;
    int max = 387420489;
    for(int i=0;i<max;i++)
    {
        Board3 board3(i);
        tot+=board3.getBoard2D().isValid();
    }
    cout<<"tot:"<<tot<<endl;
}

