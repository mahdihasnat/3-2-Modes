from heuristics.Heuristic import Heuristic


class StorageHeuristic(Heuristic):
    def get_value(self, state):
        return (state.get_score_1st_player() - state.get_score_2nd_player())
