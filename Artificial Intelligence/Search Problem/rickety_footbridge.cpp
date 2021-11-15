#include<bits/stdc++.h>

using namespace std;


typedef long long ll;
typedef pair <int, int> pii;

#define ALL(a) a.begin(), a.end()
#define FastIO ios::sync_with_stdio(false); cin.tie(0);cout.tie(0)
#define IN freopen("input.txt","r+",stdin)
#define OUT freopen("output.txt","w+",stdout)

#define DBG(a) cerr<< "line "<<__LINE__ <<" : "<< #a <<" --> "<<(a)<<endl
#define NL cout<<endl

template < class T1,class T2>
ostream &operator <<(ostream &os,const pair < T1,T2 > &p)
{
    os<<"{"<<p.first<<","<<p.second<<"}";
    return os;
}

const int cost[4] = {1,2,5,10};

class State
{
	public:
	bool light;
	bool person[4];
	State(bool x=0,bool a=0,bool b=0,bool c=0,bool d=0)
	{
		light = x;
		person[0] = a;
		person[1] = b;
		person[2] = c;
		person[3] = d;
	}
	bool operator <(const State & s) const
	{
		if(light != s.light)
			return light < s.light;
		for(int i=0;i<4;i++)
			if(person[i] != s.person[i])
				return person[i] < s.person[i];
		return false;
	}
	bool operator ==(const State & s) const
	{
		if(light != s.light)
			return false;
		for(int i=0;i<4;i++)
			if(person[i] != s.person[i])
				return false;
		return true;
	}
	friend ostream & operator<< (ostream &os, const State &s)
	{
		os<<"light: "<<s.light<<" , ";
		os<<"person: ";
		for(int i=0;i<4;i++)
			os<<s.person[i]<<" ";
		return os;
	}
};


int dijkstra(State s,State t)
{
	map<State ,pair< int,  State>  > parent;
	parent[s] = make_pair(0,s);


	priority_queue<pair<int,State>,vector<pair<int,State>>,greater<pair<int,State>>> pq;
	pq.push(make_pair(0,s));
	
	while(!pq.empty())
	{
		pair<int,State> p = pq.top();
		pq.pop();
		
		if(p.first != parent[p.second].first)
			continue;
		DBG(p);

		if(p.second == t)
		{
			State cur = t;
			do
			{

				cout<<cur<<endl;
				DBG(parent[cur].first);
				cur = parent[cur].second;
				
			} while (!(parent[cur].second == cur));

			return p.first;
		}
		
		s = p.second;

		for(int i=0;i<4;i++)
		{
			if(s.light == s.person[i])
			{
				State s1 = p.second;
				s1.person[i] = !s1.person[i];
				s1.light = !s1.light;
				
				if(parent.find(s1) == parent.end() or parent[s1].first > p.first + cost[i] )
				{
					parent[s1] = {p.first+cost[i],s};
					pq.push(make_pair(p.first+cost[i],s1));
				}
			}
			for(int j=i+1;j<4;j++)
			{
				if(s.light == s.person[i] && s.light == s.person[j])
				{
					State s1 = p.second;
					s1.person[i] = !s1.person[i];
					s1.person[j] = !s1.person[j];
					s1.light = !s1.light;
					if(parent.find(s1) == parent.end() or parent[s1].first > p.first + cost[j] )
					{
						parent[s1] = {p.first+cost[j],s};
						pq.push(make_pair(p.first+cost[j],s1));
					}
					
				}
			}
		}
	}
	
	return -1;
}

int main()
{
	State s(1,1,1,1,1) , t(0,0,0,0,0);
	cout<<dijkstra(s,t);

}