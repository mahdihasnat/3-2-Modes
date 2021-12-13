from agent.Agent import Agent


class ConsoleAgent(Agent):
    def get_move(self, state, first_player=True):
        print(state)
        while True:
            try:
                n = int(input(first_player and "First Player Move:" or "Second Player Move:"))
                break
            except Exception as e:
                pass
        return n
