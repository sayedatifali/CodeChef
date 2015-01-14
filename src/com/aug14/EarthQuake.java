package com.aug14;

import java.io.PrintWriter;

/**
 * http://www.codechef.com/AUG14/problems/EQUAKE
 * 
 * @author sultan.of.swing
 *
 */

public class EarthQuake {

	public FasterScanner mFScanner;
	public PrintWriter mOut;
	public int N;
	public static final int HEIGHTINDEX1 = 0;
	public static final int HEIGHTINDEX2 = 1;
	public static final int HEIGHTINDEX3 = 2;
	public static final int HEIGHTINDEX4 = 3;
	public static final int HEIGHT1 = 1;
	public static final int HEIGHT2 = 2;
	public static final int HEIGHT3 = 3;
	public static final int HEIGHT4 = 4;
	public static final int UPDATE = 0;
	public static final int NUM_HEIGHTS = 12;
	public static final int MAX_BUILDINGS = 800005;
	public int mHeight[];
	public Node mTree[];

	public EarthQuake() {
		int size;
		mFScanner = new FasterScanner();
		mOut = new PrintWriter(System.out);
		mHeight = new int[MAX_BUILDINGS];
		size = getSize();
		mTree = new Node[size];
		// mOut.println("Array size: " + MAX_BUILDINGS);
		// mOut.println("Seg tree size: " + size);
	}

	public int getSize() {
		int size;

		size = MAX_BUILDINGS;
		size |= (size >> 1);
		size |= (size >> 2);
		size |= (size >> 4);
		size |= (size >> 8);
		size |= (size >> 16);

		size = size + 1;
		size = size << 1;

		return size;
	}

	public void solve() {
		int i;
		int M;
		int op;
		int u, v;
		int force;
		Node ans;

		N = mFScanner.nextInt();

		// mOut.println("N : " + N);

		for (i = 0; i < N; i++) {
			mHeight[i] = mFScanner.nextInt();
		}

		M = mFScanner.nextInt();
		buildSegTree(1, 0, N - 1);

		i = 0;

		while (M-- > 0) {
			op = mFScanner.nextInt();
			if (op == UPDATE) {
				// Update operation
				u = mFScanner.nextInt();
				v = mFScanner.nextInt();
				force = mFScanner.nextInt();
				updateLazy(1, 0, N - 1, u, v, force);
			} else {
				// Query operation
				u = mFScanner.nextInt();
				v = mFScanner.nextInt();
				ans = queryLazy(1, 0, N - 1, u, v);
				mOut.println(ans.rotMax[ans.curMaxIndex]);
				// mOut.println(i);
				// mOut.flush();
				// i++;
			}
		}
	}

	public void buildSegTree(int node, int begin, int end) {

		if (begin == end) {
			int digitIndex;
			digitIndex = getDigits(mHeight[begin]);
			// mOut.println("Node = " + node + "; Begin = " + begin);
			mTree[node] = new Node(mHeight[begin], digitIndex);
			return;
		}

		int mid;

		mid = begin + ((end - begin) >> 1);

		buildSegTree(2 * node, begin, mid);
		buildSegTree(2 * node + 1, mid + 1, end);

		mTree[node] = new Node();
		mTree[node].merge(mTree[2 * node], mTree[2 * node + 1]);

	}

	public Node queryLazy(int node, int begin, int end, int qBegin, int qEnd) {

		if (mTree[node].rot != 0) {
			if (begin == end) {
				mTree[node].update();
			} else {
				mTree[node].propagate(mTree[2 * node], mTree[2 * node + 1]);
			}
		}

		if (qEnd < begin || qBegin > end)
			return null;

		if (qBegin <= begin && qEnd >= end) {
			return mTree[node];
		}

		int mid;
		Node left, right;
		Node n;

		mid = begin + ((end - begin) >> 1);

		left = queryLazy(2 * node, begin, mid, qBegin, qEnd);
		right = queryLazy(2 * node + 1, mid + 1, end, qBegin, qEnd);

		if (left == null)
			return right;
		if (right == null)
			return left;

		n = new Node();
		n.merge(left, right);

		return n;

	}

	public void updateLazy(int node, int begin, int end, int uBegin, int uEnd,
			int rot) {

		if (mTree[node].rot != 0) {
			if (begin == end) {
				mTree[node].update();
			} else {
				mTree[node].propagate(mTree[2 * node], mTree[2 * node + 1]);
			}
		}

		if (uBegin > end || uEnd < begin)
			return;

		if (uBegin <= begin && uEnd >= end) {
			mTree[node].updateRot(rot);
			if (begin == end)
				mTree[node].update();
			else
				mTree[node].propagate(mTree[2 * node], mTree[2 * node + 1]);
			return;
		}

		int mid;

		mid = begin + ((end - begin) >> 1);

		updateLazy(2 * node, begin, mid, uBegin, uEnd, rot);
		updateLazy(2 * node + 1, mid + 1, end, uBegin, uEnd, rot);

		mTree[node].merge(mTree[2 * node], mTree[2 * node + 1]);
	}

	public int getDigits(int num) {
		int digits;
		digits = 0;

		if (num == 0)
			return 1;

		while (num > 0) {
			digits++;
			num /= 10;
		}

		return digits;
	}

	public void flush() {
		mOut.flush();
	}

	public void close() {
		mOut.close();
	}

	class Node {
		int curMaxIndex;
		int rot;
		int rotMax[];
		int height;

		public Node() {
			rot = 0;
			rotMax = new int[NUM_HEIGHTS];
			height = 0;
			curMaxIndex = 0;
		}

		public Node(int height, int digits) {
			this();
			// mOut.println("Index : " + index);
			this.height = height;
			for (int i = 0; i < NUM_HEIGHTS; i++) {
				this.rotMax[i] = rotate(height, digits, i);
			}
		}

		public int rotate(int num, int digits, int rot) {
			int digit0, digit1, digit2, digit3;
			int number = 0;
			if (digits == 1 || rot == 0)
				return num;
			if (digits == 2) {
				rot = rot % 2;
				if (rot == 0)
					return num;
				digit0 = num % 10;
				digit1 = num / 10;
				number = 10 * digit0 + digit1;
				return number;
			} else if (digits == 3) {
				rot = rot % 3;
				if (rot == 0)
					return num;
				digit0 = num % 10;
				num = num / 10;
				digit1 = num % 10;
				num /= 10;
				digit2 = num;
				if (rot == 1) {
					number = 100 * digit1 + 10 * digit0 + digit2;
				}
				if (rot == 2) {
					number = 100 * digit0 + 10 * digit2 + digit1;
				}

			} else if (digits == 4) {
				rot = rot % 4;
				if (rot == 0)
					return num;
				digit0 = num % 10;
				num = num / 10;
				digit1 = num % 10;
				num /= 10;
				digit2 = num % 10;
				num /= 10;
				digit3 = num;
				if (rot == 1) {
					number = digit2 * 1000 + digit1 * 100 + digit0 * 10
							+ digit3;
				} else if (rot == 2) {
					number = digit1 * 1000 + digit0 * 100 + digit3 * 10
							+ digit2;
				} else if (rot == 3) {
					number = digit0 * 1000 + digit3 * 100 + digit2 * 10
							+ digit1;
				}
			}
			return number;
		}

		public void updateRot(int rot) {
			this.rot = this.rot + rot;
		}

		public void merge(Node node1, Node node2) {
			int i;

			for (i = 0; i < NUM_HEIGHTS; i++) {
				this.rotMax[i] = Math.max(node1.rotMax[(node1.curMaxIndex + i) % NUM_HEIGHTS], node2.rotMax[(node2.curMaxIndex + i) % NUM_HEIGHTS]);
			}

			curMaxIndex = 0;
		}

		public void update() {
			rot = rot % 12;
			if (rot == 0)
				return;
			curMaxIndex = (curMaxIndex + rot) % 12;
			rot = 0;
		}

		public void propagate(Node node1, Node node2) {
			rot = rot % 12;
			if (rot == 0)
				return;
			node1.rot = (node1.rot + rot) % 12;
			node2.rot = (node2.rot + rot) % 12;
			this.update();
		}
	}


	public static void main(String[] args) {
		EarthQuake mSol = new EarthQuake();
		mSol.solve();
		mSol.flush();
		mSol.close();
	}

}
