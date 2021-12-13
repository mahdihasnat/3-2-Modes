

from agent.Random import RandomAgent
from agent.Console import ConsoleAgent
from agent.MiniMax import MiniMaxAlphaBetaAgent

from heuristics.Storage import StorageHeuristic
from play import play

def generate_statistics():
    total = 0
    for _ in range(100):
        if play(agent1=RandomAgent(), agent2=MiniMaxAlphaBetaAgent(depth=5, heuristic=StorageHeuristic())) == 1:
            total += 1
    print("total win:" + str(total))

if __name__ == '__main__':
    generate_statistics()