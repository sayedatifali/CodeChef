/*
* http://www.codechef.com/JAN15/problems/XRQRS
*
* @author sultan.of.swing
*/

#include <iostream>
#include <vector>
#include <cstdio>
#include <algorithm>

using namespace std;

#define MAXN 500000
#define APPEND 0
#define MAXIMIZE_XOR 1
#define DEL_LAST_K_NUM 2
#define COUNT_NUMS_LESS_THAN 3
#define MUL 12
#define MAX_BITS 18

int node_index = 0;
int arr[MAXN];
int num_node = 0;
vector<int> xor_nodes[(1<<20)];
struct node{
	node *left, *right;
	int count;
};
node nodes[MUL*MAXN];
node* root[MAXN + 1];
node *null_node;

inline node* create_new_node(int val){
	nodes[node_index].count = val;
	nodes[node_index].left = NULL;
	nodes[node_index].right = NULL;
	return &nodes[node_index++];
}

node* update(node* prev, int begin, int end, int index){
	if (index < begin || index > end)
		return prev;

	node* new_node = create_new_node(0);

	if (begin == end) {
		new_node->count = prev->count + 1;
		return new_node;
	}
	int mid = begin + ((end - begin) >> 1);

	new_node->left = update(prev->left, begin, mid, index);
	new_node->right = update(prev->right, mid + 1, end, index);
	
	new_node->count = new_node->left->count + new_node->right->count;

	return new_node;
}

void update_xor(int node, int value, int key, int msb) {
	if (msb < 0){
		return;
	}

	int bit = (value & (1<<msb))>>msb;

	xor_nodes[2*node + bit].push_back(key);
	update_xor(2*node+bit, value, key, msb - 1);
}
void update_xor_delete(int node, int value, int key, int msb) {
	if (msb < 0){
		return;
	}

	int bit = (value & (1<<msb))>>msb;

	if(xor_nodes[2*node+bit].size() > 0)
		xor_nodes[2*node + bit].erase(xor_nodes[2*node+bit].end()-1);

	update_xor_delete(2*node+bit, value, key, msb - 1);
}
inline bool find(int node, int p, int q) {
	vector<int>::iterator a, b;
	a = lower_bound(xor_nodes[node].begin(), xor_nodes[node].end(), p);
	bool status = (a != xor_nodes[node].end() && (*a) <= q);
	return status;
}
int query_xor(int node, int a, int p, int q, int msb) {
	if (msb < 0){
		return 0;
	}

	int bit = (a & (1<<msb))>>msb;
	int oposite = !bit;

	if (find(2*node+oposite, p, q)) {
		return (1<<msb) + query_xor(2*node+oposite, a, p, q, msb - 1);
	} else {
		return query_xor(2*node+bit, a, p, q, msb - 1);
	}
}
int query_less_than_num(node* root, int begin, int end, int x){
	
	if(begin == end){
		return root->count;
	}

	int mid = begin + ((end - begin) >> 1);

	if(x > mid){
		return root->left->count + query_less_than_num(root->right, mid + 1, end, x);
	}
	else{
		return query_less_than_num(root->left, begin, mid, x);
	}
}
int query_kth_order(node* nodeL, node* nodeR, int begin, int end, int k){
	
	if(begin == end){
		return begin;
	}

	int mid = begin + ((end - begin) >> 1);
	int count = nodeR->left->count - nodeL->left->count;

	if(count >= k){
		return query_kth_order(nodeL->left, nodeR->left, begin, mid, k);
	}
	else{
		return query_kth_order(nodeL->right, nodeR->right, mid + 1, end, k-count);
	}
}

int main(){
	int M, type, x, L, R, k;
	scanf("%d", &M);
	null_node = create_new_node(0);
	null_node->left = null_node->right = null_node;
	root[num_node] = null_node;
	for(int i=0;i<M;i++){
		scanf("%d", &type);
		if(type == APPEND){
			scanf("%d", &x);
			num_node++;
			root[num_node] = update(root[num_node-1], 0, MAXN, x);
			update_xor(1, x, num_node, MAX_BITS);
			arr[num_node] = x;
		}
		else if(type == MAXIMIZE_XOR){
			scanf("%d %d %d", &L, &R, &x);
			printf("%d\n",x ^ query_xor(1, x, L, R, MAX_BITS));
		}
		else if(type == DEL_LAST_K_NUM){
			scanf("%d", &k);
			while(k--){
				update_xor_delete(1, arr[num_node], num_node, MAX_BITS);
				num_node--;
			}
		}
		else if(type == COUNT_NUMS_LESS_THAN){
			scanf("%d %d %d", &L, &R, &x);
			printf("%d\n", query_less_than_num(root[R], 0, MAXN, x) - query_less_than_num(root[L-1], 0, MAXN, x));
		}
		else {
			scanf("%d %d %d", &L, &R, &k);
			printf("%d\n", query_kth_order(root[L-1], root[R], 0, MAXN, k));
		}
	}
	return 0;
}