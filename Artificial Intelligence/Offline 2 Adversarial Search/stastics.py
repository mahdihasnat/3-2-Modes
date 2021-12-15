import multiprocessing
import time

from agent.MiniMax import MiniMaxAlphaBetaAgent
from heuristics.Storage import StorageHeuristic
from heuristics.StorageWithSide import StorageWithSideHeuristic
from play import play

MAX_DEPTH = 6


def get_stastics(agent1, agent2):
    ret = [0, 0, 0]
    for _ in range(1):
        ret[play(agent1=agent1, agent2=agent2)] += 1
    return ret


def parallel_process(agent, agents):
    with multiprocessing.Pool(multiprocessing.cpu_count()) as pool:
        # return pool.starmap(partial(get_stastics, agent1=agent), agents)
        return pool.starmap(get_stastics, [(agent, ag) for ag in agents])


def get_all_statistics(agents):
    start = time.time()
    matrix = []
    n = len(agents)
    for i in range(n):
        row = []
        # for j in range(n):
        #     row.append(get_stastics(agent1=agents[i],agent2=agents[j]))

        row = parallel_process(agents[i], agents)

        matrix.append(row)
    end = time.time();
    print("--- %s sec ---" % (end - start))
    return matrix


def generate_statistics():
    agents = []
    # agents.append(RandomAgent())
    agents.append(MiniMaxAlphaBetaAgent(depth=MAX_DEPTH, heuristic=StorageHeuristic()))
    agents.append(
        MiniMaxAlphaBetaAgent(depth=MAX_DEPTH, heuristic=StorageWithSideHeuristic(storage_weight=1, side_weight=1)))
    agents.append(
        MiniMaxAlphaBetaAgent(depth=MAX_DEPTH, heuristic=StorageWithSideHeuristic(storage_weight=4, side_weight=1)))
    agents.append(
        MiniMaxAlphaBetaAgent(depth=MAX_DEPTH, heuristic=StorageWithSideHeuristic(storage_weight=1, side_weight=4)))

    matrix = get_all_statistics(agents)

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
            print(f"%{mxlen}s" % (str(matrix[i][j])), end=' ')
        print()


if __name__ == '__main__':
    print('total cpu:',multiprocessing.cpu_count())
    generate_statistics()
