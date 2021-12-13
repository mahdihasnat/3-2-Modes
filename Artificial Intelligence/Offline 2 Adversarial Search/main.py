from agent.Console import ConsoleAgent
from agent.MiniMax import MiniMaxAlphaBetaAgent

from heuristics.Storage import StorageHeuristic
from play import play

if __name__ == '__main__':
    print(play(ConsoleAgent(), MiniMaxAlphaBetaAgent(depth=9, heuristic=StorageHeuristic())))
    # print(play(RandomAgent(), RandomAgent()))
