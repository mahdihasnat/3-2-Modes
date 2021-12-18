import multiprocessing
import random

import functools

from agent.Agent import Agent

MAX_HEURISTIC_VALUE = 1000


class MiniMaxAlphaBetaAgent(Agent):

    def __init__(self, depth, heuristic, debug=False):
        self._heuristic = heuristic
        self._depth = depth
        self._print = debug

    def __str__(self):
        return "MMABPA" + "(" + str(self._heuristic) + ",d=" + str(self._depth) + ")"

    def alpha_beta_search(self, state, depth, alpha, beta, first_player):
        if state.is_terminal() or depth == 0:
            return self._heuristic.get_value(state,first_player)
        else:
            if first_player:
                value = -MAX_HEURISTIC_VALUE
                valid_moves=state.get_valid_moves(first_player)
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
                valid_moves=state.get_valid_moves(first_player)
                random.shuffle(valid_moves)
                for move in valid_moves:
                    next_state, bonus = state.get_next_state(move, first_player)
                    value = min(value,
                                self.alpha_beta_search(next_state, depth - (0 if bonus else 1), alpha, beta, not bonus))
                    beta = min(beta, value)
                    if beta <= alpha:
                        break
                return value

    def get_move(self, state, first_player):
        

        best_score = -MAX_HEURISTIC_VALUE if first_player else MAX_HEURISTIC_VALUE
        best_moves = []
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
            if ((score > best_score) if first_player else (score < best_score)):
                best_score = score
                best_moves = [move]
            elif ((score >= best_score) if first_player else (score <= best_score)):
                best_moves.append(move)

        if not best_moves:
            print("best move is none!")
            move = random.choice(state.get_valid_moves(first_player))
        else:
            move = random.choice(best_moves)

        if self._print:
            print("AI prediction:", best_score)
            print(state)
            print(first_player and "First Player Move:" or "Second Player Move:", move)

        return move
