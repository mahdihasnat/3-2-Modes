import multiprocessing
import time


from agent.MiniMax import MiniMaxAlphaBetaAgent
from heuristics.H1 import H1
from heuristics.H2 import H2
from play import play

MAX_DEPTH = 4


def get_stastics(agent1, agent2):
    ret = [0, 0, 0]
    for _ in range(3):
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
        print(row)
        matrix.append(row)
    end = time.time();
    print("--- %s sec ---" % (end - start))
    return matrix


def generate_statistics():
    agents = []
    # agents.append(RandomAgent())

    agents.append(MiniMaxAlphaBetaAgent(depth=MAX_DEPTH, heuristic=H2(storage_weight=4, side_weight=1)))
    agents.append(MiniMaxAlphaBetaAgent(depth=MAX_DEPTH, heuristic=H2(storage_weight=5, side_weight=1)))
    agents.append(MiniMaxAlphaBetaAgent(depth=MAX_DEPTH, heuristic=H2(storage_weight=6, side_weight=1)))
    agents.append(MiniMaxAlphaBetaAgent(depth=MAX_DEPTH, heuristic=H2(storage_weight=7, side_weight=1)))
    agents.append(MiniMaxAlphaBetaAgent(depth=MAX_DEPTH+1, heuristic=H2(storage_weight=4, side_weight=1)))
    agents.append(MiniMaxAlphaBetaAgent(depth=MAX_DEPTH+1, heuristic=H2(storage_weight=5, side_weight=1)))
    agents.append(MiniMaxAlphaBetaAgent(depth=MAX_DEPTH+1, heuristic=H2(storage_weight=6, side_weight=1)))
    agents.append(MiniMaxAlphaBetaAgent(depth=MAX_DEPTH+1, heuristic=H2(storage_weight=7, side_weight=1)))

    matrix = get_all_statistics(agents)

    mxlen = 0
    for agent in agents:
        mxlen = max(mxlen, len(str(agent)))
    mxlen += 2
    print(f"%{mxlen}s" % "agents:", end='')
    for agent in agents:
        print(f"%{mxlen}s" % agent, end=' ')
    print()
    mxlen -= 1
    n = len(agents)

    from operator import add

    for i in range(n):
        print(f"%{mxlen}s" % agents[i], end=' ')
        tot = [0, 0, 0]
        for j in range(n):
            print(f"%{mxlen}s" % (str(matrix[i][j])), end=' ')
            tot = list(map(add, tot, matrix[i][j]))
        print(f"%{mxlen}s" % (str(tot)), end=' ')
        print()

    print(f"%{mxlen}s" % "totals:", end=' ')
    for j in range(n):
        tot = [0, 0, 0]
        for i in range(n):
            from operator import add
            tot = list(map(add, tot, matrix[i][j]))
        print(f"%{mxlen}s" % (str(tot)), end=' ')
    print()


if __name__ == '__main__':
    print('total cpu:', multiprocessing.cpu_count())
    generate_statistics()
