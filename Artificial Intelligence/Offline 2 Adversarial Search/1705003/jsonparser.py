import json
import numpy as np
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt


agents = ["RandomAgent", "MMABPA(H1)", "MMABPA(H2)", "MMABPA(H3)", "MMABPA(H4)",
          "MMABPA(H5)", "MMABPA(H6)"]

inp = """[[[8, 53, 39], [1, 4, 95], [1, 0, 99], [0, 0, 100], [0, 0, 100], [0, 0, 100], [2, 1, 97]],
[[1, 94, 5], [7, 67, 26], [0, 48, 52], [6, 49, 45], [7, 49, 44], [5, 49, 46], [4, 64, 32]],
[[0, 100, 0], [4, 75, 21], [8, 43, 49], [5, 64, 31], [12, 52, 36], [6, 58, 36], [9, 83, 8]],
[[0, 100, 0], [5, 84, 11], [9, 56, 35], [4, 62, 34], [4, 54, 42], [6, 59, 35], [6, 87, 7]],
[[0, 100, 0], [4, 80, 16], [6, 35, 59], [3, 65, 32], [2, 50, 48], [3, 65, 32], [3, 74, 23]],
[[0, 100, 0], [6, 76, 18], [13, 46, 41], [14, 55, 31], [24, 51, 25], [12, 47, 41], [1, 72, 27]],
[[1, 97, 2], [3, 58, 39], [6, 15, 79], [3, 30, 67], [0, 0, 100], [3, 15, 82], [10, 32, 58]]]"""

x = json.loads(inp)
n=len(agents)

print("n:",n,"agents:",agents)
arr=np.zeros((n,n),dtype=int)
print("arr:",arr)
for i in range(n):
    for j in range(n):
        arr[i][j]=x[i][j][1]
print("arr:",arr)

f, ax = plt.subplots(figsize=(10, 6))
plt.title("Performance Results", fontsize=18)
ax = sns.heatmap(arr,xticklabels=agents , yticklabels=agents, annot=True, fmt="d", cmap='BuGn', linewidth=0.30)
ax.set_yticklabels(ax.get_yticklabels(), rotation=0, fontsize=8)
ax.set_xticklabels(ax.get_xticklabels(), rotation=0, fontsize=8)
ax.set_xlabel('Player 2')
ax.set_ylabel('Player 1')
plt.yticks(rotation=0)
# ax = sns.heatmap((df*100).astype(int), annot=True, fmt="d", cmap='RdBu', linewidth=0.30)

fig = ax.get_figure()
plt.show()
fig.savefig('performance_results.png')