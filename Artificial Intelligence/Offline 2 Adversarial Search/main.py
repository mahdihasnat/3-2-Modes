from agent.Console import ConsoleAgent
from agent.MiniMax import MiniMaxAlphaBetaAgent

from heuristics.H1 import H1
from heuristics.H2 import H2
from heuristics.H3 import H3

from play import play

if __name__ == '__main__':
    # print(play(MiniMaxAlphaBetaAgent(depth=11, heuristic=H2(1,1),debug=True) , ConsoleAgent()))
    print(play(ConsoleAgent(), MiniMaxAlphaBetaAgent(depth=9, heuristic=H3(1, 1,2), debug=True)))
    # print(play(RandomAgent(), RandomAgent()))