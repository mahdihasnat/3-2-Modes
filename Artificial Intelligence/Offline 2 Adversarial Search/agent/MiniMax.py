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
            return self._heuristic.get_value(state)
        else:
            if first_player:
                value = -MAX_HEURISTIC_VALUE
                for move in state.get_valid_moves(first_player):
                    next_state, bonus = state.get_next_state(move, first_player)
                    value = max(value,
                                self.alpha_beta_search(next_state, depth - (0 if bonus else 1), alpha, beta, bonus))
                    alpha = max(alpha, value)
                    if beta <= alpha:
                        break
                return value
            else:
                value = MAX_HEURISTIC_VALUE
                for move in state.get_valid_moves(first_player):
                    next_state, bonus = state.get_next_state(move, first_player)
                    value = min(value,
                                self.alpha_beta_search(next_state, depth - (0 if bonus else 1), alpha, beta, not bonus))
                    beta = min(beta, value)
                    if beta <= alpha:
                        break
                return value
    #
    # def alpha_beta_search_combined(self, state, depth, alpha, beta, first_player):
    #     if state.is_terminal() or depth == 0:
    #         return self._heuristic.get_value(state)
    #     else:
    #         best_score = -MAX_HEURISTIC_VALUE if first_player else MAX_HEURISTIC_VALUE
    #         for move in state.get_valid_moves(first_player):
    #             next_state, bonus = state.get_next_state(move, first_player)
    #             score = self.alpha_beta_search(next_state, depth - (0 if bonus else 1), alpha, beta, bonus) // problem this line
    #             best_score = max(best_score, score) if first_player else min(best_score, score)
    #             if first_player:
    #                 alpha = max(alpha, best_score)
    #             else:
    #                 beta = min(beta, best_score)
    #             if beta <= alpha:
    #                 break
    #         return best_score

    def get_move_threading(self, state, first_player):
        parameters = []
        for move in state.get_valid_moves(first_player=first_player):
            next_state, bonus = state.get_next_state(move, first_player)
            parameters.append((next_state, self._depth, -MAX_HEURISTIC_VALUE, MAX_HEURISTIC_VALUE,
                               bonus if first_player else (not bonus)))
        with multiprocessing.Pool(multiprocessing.cpu_count()) as pool:
            results = pool.starmap(self.alpha_beta_search, parameters)

        moves = state.get_valid_moves(first_player)
        n = len(moves)
        assert n == len(results)
        best_score = -MAX_HEURISTIC_VALUE if first_player else MAX_HEURISTIC_VALUE
        for i in range(n):
            score = results[i]
            move = moves[i]

            if ((score > best_score) if first_player else (score < best_score)):
                best_score = score
                best_move = move

        if best_move is None:
            print("best move is none!")
            return random.choice(state.get_valid_moves(first_player))
        if self._print:
            print("AI prediction:", best_score)
            print(state)
            print(first_player and "First Player Move:" or "Second Player Move:", best_move)
        return best_move

    def get_move(self, state, first_player):
        # return self.get_move_threading(state,first_player)

        # if first_player:
        #     return self.get_move_1st_player(state)
        # else:
        #     return self.get_move_2nd_player(state)

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
