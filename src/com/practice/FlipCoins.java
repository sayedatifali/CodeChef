package com.practice;

import java.io.PrintWriter;
import java.util.StringTokenizer;

import com.io.FasterScanner;

/**
 * http://www.codechef.com/problems/FLIPCOIN
 * 
 * @author doom
 * 
 */

public class FlipCoins {

	public FasterScanner mFScanner;
	public PrintWriter mOut;
	private SegmentTree mSegTree;

	public FlipCoins() {
		mFScanner = new FasterScanner();
		mOut = new PrintWriter(System.out);
	}

	public void solve() {
		int i;
		int N, Q;
		int u, v;
		Node ans;
		String op;
		StringTokenizer st;
		boolean coins[];

		N = mFScanner.nextInt();
		Q = mFScanner.nextInt();
		
		coins = new boolean[N];

		mSegTree = new SegmentTree(N);
		mSegTree.buildSegmentTree(1, 0, N - 1, coins);

		for (i = 0; i < Q; i++) {
			st = new StringTokenizer(mFScanner.nextLine());
			op = st.nextToken();
			u = Integer.parseInt(st.nextToken());
			v = Integer.parseInt(st.nextToken());

			// Update operation
			if (op.equalsIgnoreCase("0")) {
				mSegTree.rangeUpdate(1, 0, N - 1, u, v);
			} else {
				ans = mSegTree.query(1, 0, N - 1, u, v);
				mOut.println(ans.headCount);
			}
		}

		close();

	}

	public void close() {
		mOut.flush();
		mOut.close();
	}

	public static void main(String[] args) {
		FlipCoins mSolution = new FlipCoins();
		mSolution.solve();
	}

	class SegmentTree {
		public int N;
		public int MAXN;
		public Node mTree[];

		public SegmentTree(int N) {
			this.N = N;
			getSize(N);
			mTree = new Node[MAXN];
		}

		public void getSize(int N) {
			MAXN = N;
			MAXN |= (MAXN >> 1);
			MAXN |= (MAXN >> 2);
			MAXN |= (MAXN >> 4);
			MAXN |= (MAXN >> 8);
			MAXN |= (MAXN >> 16);
			MAXN = (MAXN + 1) << 1;
		}

		public void buildSegmentTree(int node, int begin, int end, boolean[] A) {

			int mid;

			if (begin == end) {
				mTree[node] = new Node(A[begin]);
				return;
			}

			mid = begin + ((end - begin) >> 1);

			buildSegmentTree(2 * node, begin, mid, A);
			buildSegmentTree(2 * node + 1, mid + 1, end, A);

			mTree[node] = new Node();
			mTree[node].merge(mTree[2 * node], mTree[2 * node + 1]);

		}

		public void rangeUpdate(int node, int begin, int end, int upBegin,
				int upEnd) {

			int mid;

			if (begin != end) {
				mTree[node].split(mTree[node * 2], mTree[node * 2 + 1], false);
			}
			mTree[node].propagate();

			if (upBegin > end || upEnd < begin) {
				return;
			}

			if (upBegin <= begin && upEnd >= end) {
				mTree[node].flipCoins();
				if (begin != end) {
					mTree[node].split(mTree[2 * node], mTree[2 * node + 1],
							true);
				}
				return;
			}

			mid = begin + ((end - begin) >> 1);

			rangeUpdate(2 * node, begin, mid, upBegin, upEnd);
			rangeUpdate(2 * node + 1, mid + 1, end, upBegin, upEnd);

			mTree[node].merge(mTree[2 * node], mTree[2 * node + 1]);

		}

		public Node query(int node, int begin, int end, int qBegin, int qEnd) {

			int mid;
			
			Node left, right;
			Node res;

			left = right = null;

			if (begin != end)
				mTree[node].split(mTree[node * 2], mTree[node * 2 + 1], false);

			mTree[node].propagate();
			
			if (qBegin > end || qEnd < begin)
				return null;
			
			if (qBegin <= begin && qEnd >= end) {
				return mTree[node];
			}
			
			mid = begin + ((end - begin) >> 1);
			
			if (qBegin <= mid)
				left = query(2 * node, begin, mid, qBegin, qEnd);
			
			if (qEnd > mid)
				right = query(2 * node + 1, mid + 1, end, qBegin, qEnd);
			
			if (left == null)
				return right;
			if (right == null)
				return left;
			
			res = new Node();
			res.merge(left, right);
			
			return res;
		}

	}

	class Node {
		int headCount;
		int tailCount;
		boolean status; // Tails - False
		boolean lazy;

		public Node() {
			this.lazy = false;
			this.headCount = 0;
			this.tailCount = 0;
		}

		public Node(boolean status) {
			this();
			this.status = status;
			if (this.status)
				this.headCount = 1;
			else
				this.tailCount = 1;
		}

		public void split(Node left, Node right, boolean percolate) {
			if (this.lazy || percolate) {
				left.lazy = !left.lazy;
				right.lazy = !right.lazy;
			}
		}

		public void propagate() {
			if (this.lazy) {
				flipCoins();
				this.lazy = false;
			}
		}

		public void flipCoins() {
			int temp;
			temp = this.headCount;
			this.headCount = this.tailCount;
			this.tailCount = temp;
		}

		public void merge(Node left, Node right) {
			this.headCount = left.headCount + right.headCount;
			this.tailCount = left.tailCount + right.tailCount;
		}
	}

}
