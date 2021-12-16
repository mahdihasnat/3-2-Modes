from agent.Console import ConsoleAgent
from agent.MiniMax import MiniMaxAlphaBetaAgent

from heuristics.Storage import StorageHeuristic
from heuristics.StorageWithSide import StorageWithSideHeuristic
from play import play

if __name__ == '__main__':
    # print(play(MiniMaxAlphaBetaAgent(depth=11, heuristic=StorageWithSideHeuristic(1,1),debug=True) , ConsoleAgent()))
    print(play(ConsoleAgent(),MiniMaxAlphaBetaAgent(depth=9, heuristic=StorageWithSideHeuristic(1,1) ,debug=True) ))
    # print(play(RandomAgent(), RandomAgent()))