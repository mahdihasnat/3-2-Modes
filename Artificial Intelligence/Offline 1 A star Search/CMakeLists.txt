cmake_minimum_required(VERSION 3.20)
project(Offline_1_A_star_Search)

set(CMAKE_CXX_STANDARD 17)

add_executable(Offline_1_A_star_Search src/main.cpp src/board/Board.cpp src/board/Board.h src/board/Board2D.cpp src/board/Board2D.h src/board/Board3.cpp src/board/Board3.h src/search/astar.cpp src/search/astar.h src/heuristics/Hamming.cpp src/heuristics/Hamming.h src/heuristics/Dummy.cpp src/heuristics/Dummy.h)
