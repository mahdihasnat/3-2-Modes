from agent.MiniMax import MiniMaxAlphaBetaAgent
from agent.Random import RandomAgent
from heuristics.Storage import StorageHeuristic


def generate_statistics():
    agents = []
    agents.append(RandomAgent())
    agents.append(MiniMaxAlphaBetaAgent(depth=5, heuristic=StorageHeuristic()))

    mxlen = 0
    for agent in agents:
        mxlen = max(mxlen, len("{}".format(type(agent).__name__)))

    mxlen += 2
    print(f"%{mxlen}s" % "agents:", end='')
    for agent in agents:
        print(f"%{mxlen}s" % type(agent).__name__, end=' ')
    print()
    n = len(agents)
    for i in range(n):
        print(f"%{mxlen}s" % type(agents[i]).__name__, end=' ')
        for j in range(n):
            print(f"%{mxlen}s" % (str(i) + "," + str(j)), end=' ')
        print()


if __name__ == '__main__':
    generate_statistics()
