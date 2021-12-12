from agent.Agent import Agent
import random
class RandomAgent(Agent):

    def get_move(self, state, first_player=True):
        return random.randint(1,6)