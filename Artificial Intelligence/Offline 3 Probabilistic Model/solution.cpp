#include<bits/stdc++.h>
using namespace std;
#define DBG(x) cout << #x << " = " << x << endl
#define NL cout << endl
typedef long double real_t;

ostream &operator<<(ostream &os, const vector< vector< real_t > > &p) {
	for (int i = 0; i < p.size(); i++) {
		for (int j = 0; j < p[i].size(); j++) {
			os << setprecision(4)<<fixed<<p[i][j] << " ";
		}
		os << endl;
	}
	return os;
}

int dx[9] = {-1, 0, 1, -1, 0, 1, -1, 0, 1};
int dy[9] = {-1, -1, -1, 0, 0, 0, 1, 1, 1};

const real_t p = 0.9; // cumulative probability for transition to adjacent cell
const real_t sensing_accuracy = 0.85; // probability of correct sensing

inline bool is_neighbor(int x, int y, int x1, int y1) {
	return (abs(x-x1)+abs(y-y1)) <= 1 or (abs(x-x1) == 1 and abs(y-y1) == 1);
}
inline bool is_adjacent(int x,int y,int xx,int yy)
{
	return (abs(x-xx)+abs(y-yy)) == 1;
}

void calculate_belief(const vector<vector< bool > > &blocked,
	 vector< vector< real_t > > &belief,int u,int v,int b) 
{
	int n = belief.size();
	int m = belief[0].size();
	vector< vector< real_t > > partial_belief (n, vector<real_t>(m, 0));

	for(int i=0;i<n;i++)
	{
		for(int j=0;j<m;j++)
		{
			int total_adjacent = 0;
			int total_neighbors = 0;
			for(int k=0;k<9;k++)
			{
				int ii=i+dx[k];
				int jj=j+dy[k];
				if(ii<0 or ii>=n)
					continue;
				if(jj<0 or jj>=m)
					continue;
				if(blocked[ii][jj])
					continue;
				if(is_adjacent(i,j,ii,jj))
					total_adjacent++;
				else 
					total_neighbors++;
			}

			// DBG(i);
			// DBG(j);
			// DBG(total_adjacent);
			// DBG(total_neighbors);
			// NL;
			
			for(int k=0;k<9;k++)
			{
				int ii=i+dx[k];
				int jj=j+dy[k];
				if(ii<0 or ii>=n)
					continue;
				if(jj<0 or jj>=m)
					continue;
				if(blocked[ii][jj])
					continue;
				if(is_adjacent(i,j,ii,jj))
					partial_belief[ii][jj] += belief[i][j] * (p)/total_adjacent;
				else 
					partial_belief[ii][jj] += belief[i][j] * (1-p)/total_neighbors;
				
			}

		}
	}

	// DBG(partial_belief);

	real_t sum = 0;
	for(int i=0;i<n;i++)
	{
		for(int j=0;j<m;j++)
		{
			belief[i][j] = partial_belief[i][j] * 
			(is_neighbor(i,j,u,v) xor b ? 1-sensing_accuracy : sensing_accuracy);
			sum += belief[i][j];
		}
	}

	for(int i=0;i<n;i++)
	{
		for(int j=0;j<m;j++)
		{
			belief[i][j] = belief[i][j] * 100/ sum;
		}
	}
}

int main()
{
	freopen("input.txt", "r", stdin);

	

	int n,m,k;
	cin>>n>>m>>k;

	vector< vector< bool > > blocked(n,vector<bool>(m,false));

	while(k--)
	{
		int x,y;
		cin>>x>>y;
		blocked[x][y] = true;
	}

	int total_unblocked = 0;
	for(int i = 0; i < n; i++)
		for(int j = 0; j < m; j++)
			if(!blocked[i][j])
				total_unblocked++;
	
	// DBG(total_unblocked);

	real_t total_probability = 0;

	vector< vector< real_t > > belief(n, vector<real_t>(m, 0));
	for(int i=0;i<n;i++)
	{
		for(int j=0;j<m;j++)
		{
			if(blocked[i][j])
				belief[i][j] = 0;
			else
				belief[i][j] = 100.0/total_unblocked;
			total_probability += belief[i][j];
		}
	}


	int current_time = 0;

	cout<<"Time "<<current_time<<endl;
	cout<<belief<<endl;
	cout<<"Total probability: "<<total_probability<<endl;
	cout<<endl;

	char cmd;
	while(cin>>cmd)
	{
		if(cmd == 'Q')
			break;
		if(cmd == 'R')
		{
			int u,v;
			bool b;
			cin>>u>>v>>b;
			current_time++;
			
			calculate_belief(blocked,belief,u,v,b);
			total_probability=0;
			for(int i=0;i<n;i++)
				for(int j=0;j<m;j++)
					total_probability+=belief[i][j];
			
			cout<<"Time "<<current_time<<endl;
			cout<<belief<<endl;
			cout<<"Total probability: "<<total_probability<<endl;
			cout<<endl;

		}
		else if(cmd == 'C')
		{
			real_t max_probability = -1;
			vector<pair<int,int> > max_probability_cells;

			for(int i=0;i<n;i++)
			{
				for(int j=0;j<m;j++)
				{
					if(blocked[i][j])
						continue;
					if(belief[i][j] > max_probability)
					{
						max_probability = belief[i][j];
						max_probability_cells.clear();
						max_probability_cells.push_back(make_pair(i,j));
					}
					else if(belief[i][j] == max_probability)
					{
						max_probability_cells.push_back(make_pair(i,j));
					}
				}
			}

			// DBG(max_probability);
			// for(auto i: max_probability_cells)
			// {
			// 	cout<<i.first<<" "<<i.second<<endl;
			// }

			cout<<"Max Probable Cells:"<<endl;
			for(auto i: max_probability_cells)
			{
				cout<<"{"<<i.first<<","<<i.second<<"} "<<endl;
			}
			cout<<endl;

		}
		else assert(0);
	}
	return 0;
}
