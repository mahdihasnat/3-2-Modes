///****In the name of ALLAH, most Gracious, most Compassionate****//

#pragma GCC target("avx2")
#pragma GCC optimization("O3")
#pragma GCC optimization("unroll-loops")

#include <bits/stdc++.h>
using namespace std;

typedef long long ll;
typedef pair<int, int> pii;

#define ALL(a) a.begin(), a.end()
#define FastIO                   \
    ios::sync_with_stdio(false); \
    cin.tie(0);                  \
    cout.tie(0)
#define IN freopen("input.txt", "r+", stdin)
#define OUT freopen("output.txt", "w+", stdout)

#define DBG(a) cerr << "line " << __LINE__ << " : " << #a << " --> " << (a) << endl
#define NL cout << endl

template <class T1, class T2>
ostream &operator<<(ostream &os, const pair<T1, T2> &p)
{
    os << "{" << p.first << "," << p.second << "}";
    return os;
}

class A
{
public:
    int x;
    A(int x)
    {
        cout << ("constructor A:" + to_string(x)) <<" mem:"<<this <<endl;
        this->x = x;
    }
    A(const A &a)
    {
        DBG("copy constructor A");
        x = a.x;
    }
    A &operator=(const A &a)
    {
        DBG("assignment operator A");
        x = a.x;
        return *this;
    }
    A * clone()
    {
        return new A(*this);
    }
    ~A()
    {
        cout << ("destructor A:" + to_string(x)) <<" mem:"<<this<< endl;
        x = -1;
    }
    bool operator==(const A &a) const
    {
        DBG("equivalence operator");
        return x == a.x;
    }
    bool operator!=(const A &a) const
    {
        DBG("non eq operator");
        return x == a.x;
    }
    bool operator<(const A &a) const
    {
        //cout << ("less " + to_string(x) + " " + to_string(a.x)) << endl;
        return x < a.x;
    }

    friend ostream &operator<<(ostream &os, const A &a)
    {
        return os << "A.x=" << a.x;
    }
};

class mapCmp
{
public:
    bool operator()(A *a, A *b) const
    {
        return *(a) < *(b);
    }
};

class uniqCmp
{
public:
    bool operator()(const unique_ptr<A> &lhs, const unique_ptr<A> &rhs) const
    {
        return *lhs < *rhs;
    }
};

ostream &operator<<(ostream &os, const unique_ptr<A> &a)
{
    return os << (*(a));
}

void testuniqpointer()
{
    map<unique_ptr<A>, int, uniqCmp> mp;
    A *a = new A(3);
    A *b = new A(1);
    DBG(a);
    DBG(b);

    unique_ptr<A> ua(a);
    unique_ptr<A> ub(b);

    DBG(*(ua));
    DBG(*(ub));

    mp[move(ua)] = 1;
    mp[move(ub)] = 2;
    DBG(&mp);
    for (const pair<const unique_ptr<A>, int> &i : mp)
    {
        cout << i << " ";
    }
    NL;
    {
        unique_ptr<A> uc(new A(3));
        
        if(mp.find(uc) != mp.end())
        {
            DBG("found");
        }
        else DBG("not found");
        {
            DBG(mp[move(uc)]);
            DBG("outofscope");
        }
    }
    DBG(&mp);
    for (const pair<const unique_ptr<A>, int> &i : mp)
    {
        cout << i << " ";
    }
    NL;
}
void testnormalpointer()
{
    map<A *, int, mapCmp> mp;
    A *a = new A(3);
    A *b = new A(1);


    mp[a] = 1;
    mp[b] = 2;

    

    DBG(&mp);
    for (auto i : mp)
    {
        cout << i << " ";
    }
    NL;

    A *c = new A(1);



    DBG(c);

    if(mp.find(c) == mp.end())
    {
        DBG("not found");
    }
    else DBG("found");

    DBG(mp[c]);
    mp[c] = 3;
    A* d = c->clone();
    DBG(mp[d]);

    DBG(&mp);
    for (auto i : mp)
    {
        cout << i << " ";
    }
    NL;
    
}

int32_t main()
{
    FastIO;
    
    //testuniqpointer();
    testnormalpointer();

    return 0;
}
