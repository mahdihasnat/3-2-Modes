from heuristics.Heuristic import Heuristic


class H1:

    def get_value(self, state,first_player):
        return (state.get_storage_1st_player() - state.get_storage_2nd_player())

    def __str__(self):
        return "H1"
