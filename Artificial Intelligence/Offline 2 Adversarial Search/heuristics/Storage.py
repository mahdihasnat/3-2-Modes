from heuristics.Heuristic import Heuristic


class StorageHeuristic(Heuristic):

    def get_value(self, state):
        return (state.get_storage_1st_player() - state.get_storage_2nd_player())

    def __str__(self):
        return self.__class__.__name__
