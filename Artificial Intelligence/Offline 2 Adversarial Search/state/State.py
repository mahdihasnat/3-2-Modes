import copy
from builtins import staticmethod
from pdb import lasti2lineno


class State:

    def __init__(self):
        self._board = [
            4, 4, 4, 4, 4, 4, 0,
            4, 4, 4, 4, 4, 4, 0
        ]

    def __str__(self):
        """
            print format to console or file
        """
        ret = "          "
        for i in range(6, 0, -1):
            ret += "%2d " % i
        ret += "\n"
        ret += "          "
        for i in range(12, 6, -1):
            ret += "%2d " % self._board[i]
        ret += "\n"
        ret += "2nd->  "
        ret += "%2d " % self._board[13]
        ret += " " * 18
        ret += "%2d " % self._board[6]
        ret += "  <-1st\n"
        ret += "          "
        for i in range(0, 6):
            ret += "%2d " % self._board[i]
        ret += "\n"
        ret += "          "
        for i in range(1, 7):
            ret += "%2d " % i
        ret += "\n"
        return ret

    @staticmethod
    def capture_event():
        print("capturing opponents stones")

    def move_1st_player(self, move):
        """
        :return: ( next_state,true if bonus move )
        """
        if move < 1 or 6 < move:
            raise Exception("Invalid move , move number not in range")

        if self._board[move - 1] == 0:
            raise Exception("Invalid move , Board doesnot contains stone in that position")

        next_state = copy.deepcopy(self)

        total_stone = next_state._board[move - 1]
        next_state._board[move - 1] = 0

        add_stone_index = move

        for _ in range(total_stone):
            if add_stone_index == 13:
                add_stone_index = 0
            next_state._board[add_stone_index] += 1
            add_stone_index += 1

        last_stone_index = add_stone_index - 1

        # check if last stone is put on bowl
        if last_stone_index == 6:
            return next_state, True

        # check if capture of stone is possible
        opposite_index = 12 - last_stone_index
        if 0 <= last_stone_index <= 5 and next_state._board[last_stone_index] == 1 and next_state._board[opposite_index] > 0:
            State.capture_event()
            next_state._board[6] += next_state._board[opposite_index] + 1;
            next_state._board[last_stone_index] = 0
            next_state._board[opposite_index] = 0

        return next_state, False

    def move_2nd_player(self,move):
        """
        :return: ( next_state,true if bonus move )
        """

        if move < 1 or 6 < move:
            raise Exception("Invalid move , move number not in range")
        
        move+=7
        
        if self._board[move - 1] == 0:
            raise Exception("Invalid move , Board doesnot contains stone in that position")

        next_state = copy.deepcopy(self)

        total_stone = next_state._board[move - 1]
        next_state._board[move - 1] = 0

        add_stone_index = move

        for _ in range(total_stone):
            if add_stone_index == 14:
                add_stone_index = 0
            if add_stone_index == 6:
                add_stone_index = 7
            
            next_state._board[add_stone_index] += 1
            add_stone_index += 1

        last_stone_index = add_stone_index - 1

        # check if last stone is put on bowl
        if last_stone_index == 13:
            return next_state, True

        # check if capture of stone is possible
        opposite_index = 12 - last_stone_index
        if 7 <= last_stone_index <= 12 and next_state._board[last_stone_index] == 1 and next_state._board[opposite_index] > 0:
            State.capture_event()
            next_state._board[13] += next_state._board[opposite_index] + 1
            next_state._board[last_stone_index] = 0
            next_state._board[opposite_index] = 0

        return next_state, False

    def is_terminal(self):
        """
        :return: true if game is over
        """
        if all(x == 0 for x in self._board[0:6]):
            return True
        
        if all(x == 0 for x in self._board[7:13]):
            return True
        
        return False
    
    def get_score_1st_player(self):
        return sum(self._board[0:7])
    
    def get_score_2nd_player(self):
        return sum(self._board[7:14])
    