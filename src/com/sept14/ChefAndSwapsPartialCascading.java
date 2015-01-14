package com.sept14;

import java.io.PrintWriter;
import java.util.Arrays;

/**
 * http://www.codechef.com/SEPT14/problems/CHEFINV
 * 
 * @author sultan.of.swing
 *
 */

public class ChefAndSwapsPartialCascading {

	public FasterScanner mFScanner;
	public PrintWriter mOut;
	public int N;
	public int M;
	public int MAXN;
	public Node mSegTree[];
	public int mArray[];
	public long mBit[];
	public int mArrayCopy[];
	public int mLeftIndex[];
	public int mRightIndex[];

	public ChefAndSwapsPartialCascading() {
		mFScanner = new FasterScanner();
		mOut = new PrintWriter(System.out);
	}

	public void solve() {
		int i;
		int l, r;
		long inv;
		long diff;
		int lLBPos, rLBPos, lUBPos, rUBPos;
		int res[];
		long origInv;

		N = mFScanner.nextInt();
		M = mFScanner.nextInt();

		setMAXN(N);

		mArray = new int[N];
		mArrayCopy = new int[N];
		mLeftIndex = new int[N];
		mRightIndex = new int[N];
		mBit = new long[N + 10];
		mSegTree = new Node[MAXN];

		for (i = 0; i < N; i++) {
			mArray[i] = mFScanner.nextInt();
			mArrayCopy[i] = mArray[i];
			mSegTree[i] = new Node();
		}

		for (; i < MAXN; i++)
			mSegTree[i] = new Node();

		origInv = getOriginalInversions();
		buildSegmentTree(1, 0, N - 1, mArray);

		for (i = 0; i < M; i++) {
			l = mFScanner.nextInt() - 1;
			r = mFScanner.nextInt() - 1;

			if (l > r) {
				l ^= r;
				r ^= l;
				l ^= r;
			}

			if (mArray[l] == mArray[r]) {
				mOut.println(origInv);
				continue;
			}

			if (l + 1 == r) {
				diff = mArray[l] - mArray[r];
				inv = origInv;
				if (diff != 0)
					inv = inv + (diff > 0 ? -1 : 1);
				mOut.println(inv);
				continue;
			}

			lLBPos = lowerBound(0, N - 1, mArray[l], mSegTree[1].num);
			rLBPos = lowerBound(0, N - 1, mArray[r], mSegTree[1].num);
			lUBPos = upperBound(0, N - 1, mArray[l], mSegTree[1].num);
			rUBPos = upperBound(0, N - 1, mArray[r], mSegTree[1].num);

			res = query(1, 0, N - 1, l + 1, r - 1, lLBPos, lUBPos);
			inv = res[0] - res[1];
			res = query(1, 0, N - 1, l + 1, r - 1, rLBPos, rUBPos);
			inv += (res[1] - res[0]);
			inv += origInv;
			diff = mArray[l] - mArray[r];
			if (diff != 0)
				inv = inv + (diff > 0 ? -1 : 1);
			mOut.println(inv);
		}

		return;

	}

	public void setMAXN(int N) {
		MAXN = N;
		MAXN |= (MAXN >> 1);
		MAXN |= (MAXN >> 2);
		MAXN |= (MAXN >> 4);
		MAXN |= (MAXN >> 8);
		MAXN |= (MAXN >> 16);

		MAXN = (MAXN + 1) << 1;
	}

	public void buildSegmentTree(int node, int begin, int end, int[] A) {

		int mid;

		if (begin == end) {
			mSegTree[node].setLeafNode(A[begin]);
			return;
		}

		mid = begin + ((end - begin) >> 1);

		buildSegmentTree(2 * node, begin, mid, A);
		buildSegmentTree(2 * node + 1, mid + 1, end, A);

		mSegTree[node].merge(mSegTree[2 * node], mSegTree[2 * node + 1]);

	}

	public int[] query(int node, int begin, int end, int qBegin, int qEnd,
			int lowerBoundPos, int upperBoundPos) {

		int[] left, right, res;
		int mid;
		Node n;
		int lBoundPos, rBoundPos;

		n = mSegTree[node];

		if (qBegin <= begin && qEnd >= end) {
			res = new int[2];
			res[1] = lowerBoundPos; // Strictly greater
			res[0] = n.N - upperBoundPos; // Strictly smaller
			return res;
		}

		left = right = null;

		mid = begin + ((end - begin) >> 1);

		if (qBegin <= mid) {
			lBoundPos = lowerBoundPos < n.N ? n.lChildLowerBoundPos[lowerBoundPos]
					: mSegTree[2 * node].N;
			rBoundPos = upperBoundPos < n.N ? n.lChildLowerBoundPos[upperBoundPos]
					: mSegTree[2 * node].N;
			left = query(2 * node, begin, mid, qBegin, qEnd, lBoundPos,
					rBoundPos);
		}

		if (qEnd > mid) {
			lBoundPos = lowerBoundPos < n.N ? n.rChildLowerBoundPos[lowerBoundPos]
					: mSegTree[2 * node + 1].N;
			rBoundPos = upperBoundPos < n.N ? n.rChildLowerBoundPos[upperBoundPos]
					: mSegTree[2 * node + 1].N;
			right = query(2 * node + 1, mid + 1, end, qBegin, qEnd, lBoundPos,
					rBoundPos);
		}

		if (left == null)
			return right;

		if (right == null)
			return left;

		res = new int[2];
		res[0] = left[0] + right[0];
		res[1] = left[1] + right[1];

		return res;
	}

	public long getOriginalInversions() {
		int i, num, numPos;
		long origInv;

		Arrays.sort(mArrayCopy);

		for (i = 0; i < N; i++) {
			num = mArray[i];
			numPos = lowerBound(0, N, num, mArrayCopy);
			mArray[i] = numPos + 1;
		}

		origInv = 0;
		for (i = N - 1; i >= 0; i--) {
			num = mArray[i];
			origInv += queryBit(num - 1, mBit);
			updateBit(num, 1, mBit);
		}

		return origInv;
	}

	public void updateBit(int idx, long val, long[] bit) {
		while (idx <= N) {
			bit[idx] += val;
			idx += (idx & -idx);
		}
	}

	public long queryBit(int idx, long[] bit) {
		long sum = 0;
		while (idx > 0) {
			sum += bit[idx];
			idx -= (idx & -idx);
		}
		return sum;
	}

	public int lowerBound(int first, int last, int val, int[] array) {

		int index;
		int count, step;

		count = last - first;

		while (count > 0) {
			index = first;
			step = count / 2;
			index += step;
			if (array[index] < val) {
				first = ++index;
				count -= step + 1;
			} else
				count = step;
		}

		return first;

	}

	public int upperBound(int first, int last, int val, int[] array) {

		int index;
		int count, step;

		count = last - first;
		while (count > 0) {
			index = first;
			step = count / 2;
			index += step;

			if (!(val < array[index])) {
				first = ++index;
				count -= step + 1;
			} else {
				count = step;
			}
		}
		return first;
	}

	class Node {
		int num[];
		int lChildLowerBoundPos[];
		int rChildLowerBoundPos[];
		int N;

		public void setLeafNode(int num) {
			this.N = 1;
			this.num = new int[N];
			lChildLowerBoundPos = new int[N];
			rChildLowerBoundPos = new int[N];
			this.num[0] = num;
		}

		public void merge(Node left, Node right) {
			int i, j;
			int index;
			N = left.N + right.N;
			this.num = new int[N];
			lChildLowerBoundPos = new int[N];
			rChildLowerBoundPos = new int[N];

			i = j = 0;
			index = 0;

			while (i < left.N || j < right.N) {
				if (j >= right.N || (i < left.N && left.num[i] <= right.num[j])) {
					num[index] = left.num[i];
					if (index > 0 && num[index] == num[index - 1]) {
						lChildLowerBoundPos[index] = lChildLowerBoundPos[index - 1];
						rChildLowerBoundPos[index] = rChildLowerBoundPos[index - 1];
					} else {
						lChildLowerBoundPos[index] = i;
						rChildLowerBoundPos[index] = j;
					}
					i++;
				} else {
					num[index] = right.num[j];
					if (index > 0 && num[index] == num[index - 1]) {
						lChildLowerBoundPos[index] = lChildLowerBoundPos[index - 1];
						rChildLowerBoundPos[index] = rChildLowerBoundPos[index - 1];
					} else {
						lChildLowerBoundPos[index] = i;
						rChildLowerBoundPos[index] = j;
					}
					j++;
				}
				index++;
			}

		}

	}

	public void flush() {
		mOut.flush();
	}

	public void close() {
		mOut.close();
	}

	public static void main(String[] args) {
		ChefAndSwapsPartialCascading mSol = new ChefAndSwapsPartialCascading();
		mSol.solve();
		mSol.flush();
		mSol.close();
	}

}
