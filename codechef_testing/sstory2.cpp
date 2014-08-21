#include <iostream>
#include <map>
#include <set>
#include <queue>
#include <stack>
#include <list>
#include <vector>
#include <string>
#include <deque>
#include <bitset>
#include <algorithm>
#include <utility>
#include <functional>
#include <limits>
#include <numeric>
#include <complex>
#include <cassert>
#include <cmath>
#include <memory.h>
#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <ctime>
#include <climits>
#include <iomanip>

using namespace std;

template<typename X> inline X abs(const X& a) { return (a < 0 ? -a : a); }
template<typename X> inline X sqr(const X& a) { return (a * a); }
template<typename X> inline void print(const X& a,int N) {cout<<endl;for(int i=0;i<N;i++)cout<<a[i]<<" ";cout<<endl;}
typedef long long ll;
typedef long double ld;
typedef pair<int,int> pp;
typedef pair<ld, ld> ppld;
typedef unsigned long long ull;
#define FOR(i, n) for(int i = 0; i < int(n); i++)
#define FORD(i, n) for(int i = int(n-1); i >= 0; i--)
#define FORAB(i, a, b) for(int i = int(a); i < int(b); i++)
#define foreach(it, a) for(__typeof((a).begin()) it = (a).begin(); it != (a).end(); it++)
#define pb push_back
#define mp make_pair
#define mset(a, val) memset(a, val, sizeof (a))
#define all(a) (a).begin(), (a).end()
#define rall(a) (a).rbegin(), (a).rend()
#define getcx getchar_unlocked
#define getmid(a,b) (a+(b-a)/2)
#define tr(container, it) for(typeof(container.begin()) it = container.begin(); it != container.end(); it++)
const int INF = int(1e9);
const ll INF64 = ll(INF) * ll(INF);
const ld EPS = 1e-9;
const ld PI = ld(3.1415926535897932384626433832795);
template<typename X> inline void inp(X &n ) {
   int ch=getcx();int sign=1;n=0;
   while( ch < '0' || ch > '9' ){if(ch=='-')sign=-1; ch=getcx();}
   while(  ch >= '0' && ch <= '9' ) n = (n<<3)+(n<<1) + ch-'0', ch=getcx();
   n=n*sign;
}
template<typename X> inline void out(X a) {
    char snum[20]; int i=0;
    do {snum[i++]=a%10+48; a=a/10; }while(a!=0);
    i=i-1;
    while(i>=0) putchar_unlocked(snum[i--]);
    putchar_unlocked('\n');
}

struct SuffixArray {
  const int L;
  string s;
  vector<vector<int> > P;
  vector<pair<pair<int,int>,int> > M;

  SuffixArray(const string &s) : L(s.length()), s(s), P(1, vector<int>(L, 0)), M(L) {
    for (int i = 0; i < L; i++)
      P[0][i] = int(s[i]-'a');
    for (int skip = 1, level = 1; skip < L; skip *= 2, level++) {
      P.push_back(vector<int>(L, 0));
      for (int i = 0; i < L; i++)
        M[i] = make_pair(make_pair(P[level-1][i], i + skip < L ? P[level-1][i + skip] : -1000), i);
      sort(M.begin(), M.end());
      for (int i = 0; i < L; i++)
        P[level][M[i].second] = (i > 0 && M[i].first == M[i-1].first) ? P[level][M[i-1].second] : i;
    }
  }
  vector<int> GetSuffixArray() {
    return P.back();
  }
  // returns the length of the longest common prefix of s[i...L-1] and s[j...L-1]
  int LongestCommonPrefix(int i, int j) {
    int len = 0;
    if (i == j) return L - i;
    for (int k = P.size() - 1; k >= 0 && i < L && j < L; k--) {
      if (P[k][i] == P[k][j]) {
        i += 1 << k;
        j += 1 << k;
        len += 1 << k;
      }
    }
    return len;
  }
};

int main(){
    string s1,s2,s3;
    cin>>s1;
    cin>>s2;
    s3 = s2+'$'+s1+'#';
    // cout<<"s3 = "<<s3<<endl;
    int n2 = s2.size(), max_substr_length = 0, min_a = 0, N;

    SuffixArray SA(s3);
    vector<int> V = SA.GetSuffixArray();
    N = V.size();
    vector<int> sarray(N);

    for(int i=0;i<N;i++)
        sarray[V[i]] = i;

    for(int i=1;i<N;i++){
        int a = min(sarray[i] , sarray[i-1]);
        int b = max(sarray[i] , sarray[i-1]);
        if(a<n2 && b>n2){
          int substr_length = SA.LongestCommonPrefix(a,b);
          if(substr_length > max_substr_length){
            max_substr_length = substr_length;
            min_a = a;
          }
          else if((substr_length==max_substr_length)  &&  (a<min_a))
            min_a = a;
        }
    }

    if(max_substr_length == 0){
      out(0);
      return 0;
    }
    // cout<<"min_a = "<<min_a<<endl;
    // cout<<"maxlen = "<<max_substr_length<<endl;
    for(int i=min_a;i<min_a+max_substr_length;i++)
      cout<<s2[i];
    cout<<endl<<max_substr_length<<endl;
    return 0;
}
