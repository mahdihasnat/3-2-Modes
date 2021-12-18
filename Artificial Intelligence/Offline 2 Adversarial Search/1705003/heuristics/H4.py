
from os import stat
from heuristics.H3 import H3

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