from H2 import H2
from Heuristic import Heuristic


class H3(Heuristic):

    def __init__(self, storage_weight, side_weight, addition_move_weight):
        self.h2 = H2(storage_weight, side_weight)
        self.w3 = addition_move_weight

    def get_value(self, state):
        additional_move_first = 0
        return self.h2.get_value(state)
