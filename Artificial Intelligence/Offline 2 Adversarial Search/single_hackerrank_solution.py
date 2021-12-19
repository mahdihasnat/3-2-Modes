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
class H1:

    def get_value(self, state,first_player):
        return (state.get_storage_1st_player() - state.get_storage_2nd_player())

    def __str__(self):
        return "H1"
class H2:

    def __init__(self, storage_weight, side_weight):
        self.w1 = storage_weight
        self.w2 = side_weight

    def __str__(self):
        return f"H2({self.w1},{self.w2})"

    def get_value(self, state,first_player):
        return self.w1 * (state.get_storage_1st_player() - state.get_storage_2nd_player()) + self.w2 * (
                sum(state.board[0:6]) - sum(state.board[7:13]))


class H3:

    def __init__(self, storage_weight, side_weight, addition_move_weight):
        # super().__init__(storage_weight, side_weight)
        self.w3 = addition_move_weight
        self.h2=H2(storage_weight, side_weight)

    def __str__(self):
        return f"H3({self.h2.w1},{self.h2.w2},{self.w3})"

    def get_value(self, state,first_player):
        additional_move_count = 0
        if not state.is_terminal() :
            if first_player:
                for i in range(0,6):
                    additional_move_count += state.board[i] == 6-i
            else:
                for i in range(7,13):
                    additional_move_count -= state.board[i] == 13-i
            
        return self.h2.get_value(state,first_player) + self.w3 * additional_move_count


class H4:

	def __init__(self , w1,w2,w3,w4):
		self.h3=H3(w1,w2,w3)
		self.w4=w4
	
	def __str__(self) -> str:
		return f"H4({self.h3.h2.w1},{self.h3.h2.w2},{self.h3.w3},{self.w4})"

	def get_value(self,state,first_player):
		capture_score=0
		if not state.is_terminal():
			if first_player:
				capture_score=state.get_max_capture_1st_player()
			else:
				capture_score=-state.get_max_capture_2nd_player()
				
		return self.h3.get_value(state,first_player)+self.w4*capture_score

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


import random


MAX_HEURISTIC_VALUE = 1000


class MiniMaxAlphaBetaAgent:

    def __init__(self, depth, heuristic, debug=False):
        self._heuristic = heuristic
        self._depth = depth
        self._print = debug

    def __str__(self):
        return "MMABPA" + "(" + str(self._heuristic) + ",d=" + str(self._depth) + ")"

    def alpha_beta_search(self, state, depth, alpha, beta, first_player):
        if state.is_terminal() or depth == 0:
            return self._heuristic.get_value(state, first_player)
        else:
            if first_player:
                value = -MAX_HEURISTIC_VALUE
                valid_moves = state.get_valid_moves(first_player)
                random.shuffle(valid_moves)
                for move in valid_moves:
                    next_state, bonus = state.get_next_state(move, first_player)
                    value = max(value,
                                self.alpha_beta_search(next_state, depth - (0 if bonus else 1), alpha, beta, bonus))
                    alpha = max(alpha, value)
                    if beta <= alpha:
                        break
                return value
            else:
                value = MAX_HEURISTIC_VALUE
                valid_moves = state.get_valid_moves(first_player)
                random.shuffle(valid_moves)
                for move in valid_moves:
                    next_state, bonus = state.get_next_state(move, first_player)
                    value = min(value,
                                self.alpha_beta_search(next_state, depth - (0 if bonus else 1), alpha, beta, not bonus))
                    beta = min(beta, value)
                    if beta <= alpha:
                        break
                return value

    def get_best_move(self, state, first_player, move1, move2):
        """
            return best move with same utility
        """
        capture1 = state.stones_in_capture_move(move1, first_player)
        capture2 = state.stones_in_capture_move(move2, first_player)

        if capture1 != capture2:
            return move1 if capture1 > capture2 else move2
        else:
            if capture1 != 0:
                return random.choice([move1, move2])
            else:
                b1 = state.is_bonus_move(move1, first_player)
                b2 = state.is_bonus_move(move2, first_player)

                if b1 and b2:
                    return move1 if move1 > move2 else move2
                elif b1:
                    return move1
                elif b2:
                    return move2
                else:
                    return random.choice([move1, move2])

    def get_move(self, state, first_player):

        best_score = -MAX_HEURISTIC_VALUE if first_player else MAX_HEURISTIC_VALUE
        best_move = None
        for move in state.get_valid_moves(first_player):
            next_state, bonus = state.get_next_state(move, first_player)
            score = self.alpha_beta_search(
                state=next_state,
                depth=self._depth,
                alpha=best_score if first_player else -MAX_HEURISTIC_VALUE,
                beta=MAX_HEURISTIC_VALUE if first_player else best_score,
                first_player=bonus if first_player else (not bonus)
            )
            if self._print:
                print('score:', score)
            if (score > best_score) if first_player else (score < best_score):
                best_score = score
                best_move=move
            elif (score >= best_score) if first_player else (score <= best_score):
                best_move = self.get_best_move(state, first_player, best_move, move)

        if not best_move:
            print("best move is none!")
            best_move = random.choice(state.get_valid_moves(first_player))

        if self._print:
            print("AI prediction:", best_score)
            print(state)
            print(first_player and "First Player Move:" or "Second Player Move:", best_move)

        return best_move


def main():
    first_player=input()=='1'
    state=State()
    state.board[6]=int(input())
    lst=list(map(int,input().split()))
    for i in range(6):
        state.board[i]=lst[i]
    
    state.board[13]=int(input())
    lst=list(map(int,input().split()))
    for i in range(6):
        state.board[i+7]=lst[i]
    
    # print(state)

    agent=MiniMaxAlphaBetaAgent(depth=5,debug=False , heuristic=H6(10,5,3,4,2,7))
    print(agent.get_move(state,first_player))

if __name__ == '__main__':
    main()