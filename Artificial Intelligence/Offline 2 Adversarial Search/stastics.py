from agent.MiniMax import MiniMaxAlphaBetaAgent
from agent.Random import RandomAgent
from heuristics.Storage import StorageHeuristic
from heuristics.StorageWithSide import StorageWithSideHeuristic
from play import play

MAX_DEPTH=4

def get_stastics(*, agent1, agent2):
    ret = [0, 0, 0]
    for _ in range(100):
        ret[play(agent1=agent1, agent2=agent2)] += 1
    return ret


def generate_statistics():
    agents = []
    # agents.append(RandomAgent())
    agents.append(MiniMaxAlphaBetaAgent(depth=MAX_DEPTH, heuristic=StorageHeuristic()))
    agents.append(MiniMaxAlphaBetaAgent(depth=MAX_DEPTH,heuristic=StorageWithSideHeuristic(storage_weight=4,side_weight=1)))
    agents.append(MiniMaxAlphaBetaAgent(depth=MAX_DEPTH,heuristic=StorageWithSideHeuristic(storage_weight=1,side_weight=4)))
    mxlen = 0
    for agent in agents:
        mxlen = max(mxlen, len(str(agent)))

    mxlen += 2
    print(f"%{mxlen}s" % "agents:", end='')
    for agent in agents:
        print(f"%{mxlen}s" % agent, end=' ')
    print()
    n = len(agents)
    for i in range(n):
        print(f"%{mxlen}s" % agents[i], end=' ')
        for j in range(n):
            print(f"%{mxlen}s" % (str(get_stastics(agent1=agents[i], agent2=agents[j]))), end=' ')
        print()


if __name__ == '__main__':
    generate_statistics()
