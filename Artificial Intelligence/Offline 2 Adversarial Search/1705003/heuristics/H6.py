from heuristics.H5 import H5


class H6:
    def __init__(self, w1, w2, w3, w4, w5, w6):
        self.h5 = H5(w1, w2, w3, w4, w5)
        self.w6 = w6

    def __str__(self) -> str:
        return f"H6({self.h5.h4.h3.h2.w1},{self.h5.h4.h3.h2.w2},{self.h5.h4.h3.w3},{self.h5.h4.w4},{self.h5.w5},{self.w6})"

    def get_value(self, state, first_player):
        if state.board[6] > 24:
            closeness_to_win = 100
        elif state.board[13] > 24:
            closeness_to_win = -100
        else:
            if first_player:
                closeness_to_win = state.board[6]
            else:
                closeness_to_win = -state.board[13]
        return self.h5.get_value(state, first_player) + self.w6 * closeness_to_win
