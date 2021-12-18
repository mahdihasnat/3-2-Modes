import copy
from builtins import staticmethod


class State:

    def __init__(self):
        self.board = [
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
            ret += "%2d " % self.board[i]
        ret += "\n"
        ret += "2nd->  "
        ret += "%2d " % self.board[13]
        ret += " " * 18
        ret += "%2d " % self.board[6]
        ret += "  <-1st\n"
        ret += "          "
        for i in range(0, 6):
            ret += "%2d " % self.board[i]
        ret += "\n"
        ret += "          "
        for i in range(1, 7):
            ret += "%2d " % i
        ret += "\n"
        return ret

    @staticmethod
    def capture_event():
        pass
        # print("capturing opponents stones")

    def move_1st_player(self, move):
        """
        :return: ( next_state,true if bonus move )
        """
        if move < 1 or 6 < move:
            raise Exception("Invalid move , move number not in range")

        if self.board[move - 1] == 0:
            raise Exception("Invalid move , Board doesnot contains stone in that position")

        next_state = copy.deepcopy(self)

        total_stone = next_state.board[move - 1]
        next_state.board[move - 1] = 0

        add_stone_index = move

        for _ in range(total_stone):
            if add_stone_index == 13:
                add_stone_index = 0
            next_state.board[add_stone_index] += 1
            add_stone_index += 1

        last_stone_index = add_stone_index - 1

        # check if last stone is put on bowl
        if last_stone_index == 6:
            return next_state, True

        # check if capture of stone is possible
        opposite_index = 12 - last_stone_index
        if 0 <= last_stone_index <= 5 and next_state.board[last_stone_index] == 1 and next_state.board[
            opposite_index] > 0:
            State.capture_event()
            next_state.board[6] += next_state.board[opposite_index] + 1
            next_state.board[last_stone_index] = 0
            next_state.board[opposite_index] = 0

        return next_state, False

    def move_2nd_player(self, move):
        """
        :return: ( next_state,true if bonus move )
        """

        if move < 1 or 6 < move:
            raise Exception("Invalid move , move number not in range")

        move += 7

        if self.board[move - 1] == 0:
            raise Exception("Invalid move , Board doesnot contains stone in that position")

        next_state = copy.deepcopy(self)

        total_stone = next_state.board[move - 1]
        next_state.board[move - 1] = 0

        add_stone_index = move

        for _ in range(total_stone):
            if add_stone_index == 14:
                add_stone_index = 0
            if add_stone_index == 6:
                add_stone_index = 7

            next_state.board[add_stone_index] += 1
            add_stone_index += 1

        last_stone_index = add_stone_index - 1

        # check if last stone is put on bowl
        if last_stone_index == 13:
            return next_state, True

        # check if capture of stone is possible
        opposite_index = 12 - last_stone_index
        if 7 <= last_stone_index <= 12 and next_state.board[last_stone_index] == 1 and next_state.board[
            opposite_index] > 0:
            State.capture_event()
            next_state.board[13] += next_state.board[opposite_index] + 1
            next_state.board[last_stone_index] = 0
            next_state.board[opposite_index] = 0

        return next_state, False

    def get_next_state(self, move, first_player):
        """
        :return: ( next_state,true if bonus move )
        """
        if first_player:
            return self.move_1st_player(move)
        else:
            return self.move_2nd_player(move)

    def is_terminal(self):
        """
        :return: true if game is over
        """
        if all(x == 0 for x in self.board[0:6]):
            return True

        if all(x == 0 for x in self.board[7:13]):
            return True

        return False

    def get_storage_1st_player(self):
        return self.board[6]

    def get_storage_2nd_player(self):
        return self.board[13]

    def get_score_1st_player(self):
        return sum(self.board[0:7])

    def get_score_2nd_player(self):
        return sum(self.board[7:14])

    def get_valid_moves(self, first_player):
        """
        :return: list of valid moves
        """
        if first_player:
            return [i + 1 for i, x in enumerate(self.board[0:6]) if x > 0]
        else:
            return [i + 1 for i, x in enumerate(self.board[7:13]) if x > 0]

    def get_max_capture_1st_player(self):
        ret=0
        for i in range(6):
            if self.board[i] >0 and self.board[i]+i <6 and self.board[i+self.board[i]] == 0 and self.board[12 - i - self.board[i]] > 0:
                ret = max(ret , 1+ self.board[12 - i - self.board[i]])
        return ret
    
    def get_max_capture_2nd_player(self):
        ret =0
        for i in range(7,13):
            if self.board[i] >0 and self.board[i]+i <13 and self.board[i+self.board[i]] == 0 and self.board[12 - i - self.board[i]] > 0:
                ret = max(ret , 1+ self.board[12 - i - self.board[i]])
        return ret
    
    def stones_in_capture_move(self,move,first_player):
        """
            1<=move<=6
        """
        if first_player:
            stone=self.board[move-1]
            if stone == 0:
                return 0
            target=move-1 + stone

            if target >= 13:
                target-=13
                if target < move -1 and self.board[target] == 0:
                    return self.board[12-target]+1 if self.board[12-target] != 0 else 0
                else:
                    return 0
            elif target >=6:
                return 0
            elif self.board[target] == 0:
                return self.board[12-target]+1 if self.board[12-target] != 0 else 0
            else:
                return 0       
        else:
            stone=self.board[move+6]
            if stone == 0:
                return 0
            target=move+6 + stone

            if target <= 12 and self.board[target] == 0:
                return self.board[12-target]+1 if self.board[12-target] != 0 else 0
            elif target >=20:
                target-=13
                if target < move+6 and self.board[target] ==0:
                    return self.board[12-target]+1 if self.board[12-target] != 0 else 0
            return 0
    def is_bonus_move(self,move,first_player):
        if first_player :
            return self.board[move-1] == 7- move
        else:
            return self.board[6+move] == 7- move