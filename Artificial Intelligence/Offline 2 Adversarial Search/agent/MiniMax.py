import random

from agent.Agent import Agent

class MiniMaxAlphaBetaAgent(Agent):

    def __init__(self, depth, heuristic):
        self._heuristic = heuristic
        self._depth = depth

    def __str__(self):
        return "MMABPA"+"("+str(self._heuristic)+",d="+str(self._depth)+")"

    def alpha_beta_search(self, state, depth, alpha, beta, first_player):
        if state.is_terminal() or depth == 0:
            return self._heuristic.get_value(state)
        else:
            if first_player:
                value = -1000
                for move in state.get_valid_moves(first_player):
                    next_state, bonus = state.get_next_state(move, first_player)
                    value = max(value, self.alpha_beta_search(next_state, depth - 1, alpha, beta, bonus))
                    if value >= beta:
                        break
                    alpha = max(alpha, value)
                return value
            else:
                value = 1000
                for move in state.get_valid_moves(first_player):
                    next_state, bonus = state.get_next_state(move, first_player)
                    value = min(value, self.alpha_beta_search(next_state, depth - 1, alpha, beta, not bonus))
                    if value <= alpha:
                        break
                    beta = min(beta, value)
                return value

    def get_move(self, state, first_player):
        best_score = -1000 if first_player else 1000
        best_move = None
        for move in state.get_valid_moves(first_player):
            next_state, bonus = state.get_next_state(move, first_player)
            score = self.alpha_beta_search(
                state=next_state,
                depth=self._depth,
                alpha=best_score if first_player else -1000,
                beta=1000 if first_player else best_score,
                first_player=first_player
            )
            if score > best_score if first_player else score < best_score:
                best_score = score
                best_move = move
        if best_move is None:
            return random.choice(state.get_valid_moves(first_player))
        
        print("AI prediction:", best_score)
        print(state)
        print(first_player and "First Player Move:" or "Second Player Move:" , best_move)
        return best_move
