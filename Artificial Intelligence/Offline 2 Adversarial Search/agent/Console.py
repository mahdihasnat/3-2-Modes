from agent.Agent import Agent


class ConsoleAgent(Agent):
    def get_move(self, state, first_player=True):
        print(state)
        n=int(input(first_player and "First Player Move:" or "Second Player Move:"))
        return n
