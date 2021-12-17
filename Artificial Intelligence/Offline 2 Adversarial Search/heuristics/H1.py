from heuristics.Heuristic import Heuristic


class H1(Heuristic):

    def get_value(self, state):
        return (state.get_storage_1st_player() - state.get_storage_2nd_player())

    def __str__(self):
        return "STH"
