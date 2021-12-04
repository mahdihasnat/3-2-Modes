//
// Created by mahdi on 11/27/2021.
//

#ifndef OFFLINE_1_A_STAR_SEARCH_ASTAR_H
#define OFFLINE_1_A_STAR_SEARCH_ASTAR_H


#include "../board/Board.h"

int astar(Board * startBoard, int (*heuristic)(const Board2D &board2D));

#endif //OFFLINE_1_A_STAR_SEARCH_ASTAR_H
