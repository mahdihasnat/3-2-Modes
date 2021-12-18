from heuristics.H4 import H4


class H5:

    def __init__(self, w1, w2, w3, w4, w5) -> None:
        self.h4 = H4(w1, w2, w3, w4)
        self.w5 = w5

    def __str__(self) -> str:
        return f"H5({self.h4.h3.h2.w1},{self.h4.h3.h2.w2},{self.h4.h3.w3},{self.h4.w4},{self.w5})"

    def get_value(self, state, first_player):
        number_of_non_empty_pits = 0
        if first_player:
            for i in range(6):
                number_of_non_empty_pits += 1 if state.board[i] != 0 else 0
        else:
            for i in range(7, 13):
                number_of_non_empty_pits -= 1 if state.board[i] != 0 else 0

        return self.h4.get_value(state, first_player) + self.w5 * number_of_non_empty_pits
