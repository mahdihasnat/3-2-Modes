from state.State import State

from agent.Random import RandomAgent

def runner(agent1, agent2):
    state = State()
    turnfor1 = True
    while not state.is_terminal():
        if turnfor1:
            move = agent1.get_move(state, True)
            try:
                state, turnfor1 = state.move_1st_player(move)
            except Exception as e:
                print(e)
        else:
            move = agent2.get_move(state, False)
            try:
                state, turnfor1 = state.move_2nd_player(move)
                turnfor1 = not turnfor1
            except Exception as e:
                print(e)
    score1 = state.get_score_1st_player()
    score2 = state.get_score_2nd_player()

    if score1 > score2:
        return 1
    elif score1 < score2:
        return -1
    else:
        return 0


if __name__ == '__main__':
    # runner(ConsoleAgent() ,ConsoleAgent())
    print(runner(RandomAgent(), RandomAgent()))
