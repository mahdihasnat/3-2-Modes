import random

from agent.Agent import Agent


class RandomAgent(Agent):

    def __str__(self):
        return self.__class__.__name__

    def get_move(self, state, first_player=True):
        return random.choice(state.get_valid_moves(first_player))
