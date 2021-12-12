from agent.Agent import Agent


class ConsoleAgent(Agent):
    def get_move(self, s, first_player=True):
        print(s)
        n=input(first_player and "First Player Move:" or "Second Player Move:")
        return n
