from heuristics.Heuristic import Heuristic


class H2:

    def __init__(self, storage_weight, side_weight):
        self.w1 = storage_weight
        self.w2 = side_weight

    def __str__(self):
        return f"H2({self.w1},{self.w2})"

    def get_value(self, state,first_player):
        return self.w1 * (state.get_storage_1st_player() - state.get_storage_2nd_player()) + self.w2 * (
                sum(state.board[0:6]) - sum(state.board[7:13]))
