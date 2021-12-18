from heuristics.H2 import H2
from heuristics.Heuristic import Heuristic


class H3:

    def __init__(self, storage_weight, side_weight, addition_move_weight):
        # super().__init__(storage_weight, side_weight)
        self.w3 = addition_move_weight
        self.h2=H2(storage_weight, side_weight)

    def __str__(self):
        return f"H3({super().__str__()},{self.w3})"

    def get_value(self, state,first_player):
        additional_move_count = 0
        if not state.is_terminal() :
            if first_player:
                for i in range(0,6):
                    additional_move_count += state.board[i] == i+1
            else:
                for i in range(7,13):
                    additional_move_count -= state.board[i] == i-6
            
        return self.h2.get_value(state,first_player) + self.w3 * additional_move_count
