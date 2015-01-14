package com.march14;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * http://www.codechef.com/MARCH14/problems/ANUGCD
 * 
 * AC in Codechef! :D :D
 * 
 * Using segment trees
 * 
 * @author sultan.of.swing
 *
 */

public class AnuGCD {

	public PrintWriter mOut;
	public FasterScanner mFScanner;
	public int N, M;
	public int mA[];
	public Node mSegTree[][];
	public ArrayList<Integer> mPrimes;
	public ArrayList<Integer> mPrimeIndexes[];
	public int mSmallPrimesLen;
	public int mPrimesLen;
	public int mPrimesId[];
	public static final int sMaxNum = 100003;
	public static final int sMaxPrime = 350;

	public AnuGCD() {
		mOut = new PrintWriter(System.out);
		mFScanner = new FasterScanner();
		mPrimesId = new int[sMaxNum];
		mPrimes = new ArrayList<Integer>();
	}

	public int getMaxLen(int N) {
		int max = N;

		max |= (max >> 1);
		max |= (max >> 2);
		max |= (max >> 4);
		max |= (max >> 8);
		max |= (max >> 16);

		max = (max + 1) << 1;

		return max;

	}

	public void initSegTree() {
		int i;
		int size;

		mSegTree = new Node[mPrimesLen][];

		for (i = 0; i < mPrimesLen; i++) {
			if (mPrimeIndexes[i].size() == 0)
				continue;
			size = getMaxLen(mPrimeIndexes[i].size());
			mSegTree[i] = new Node[size];
		}
	}

	// Build seg tree on all the prime factors i.e. each prime factor has a
	// segment tree over the values which are divisible by this prime factor
	public void buildSegTree() {
		int i;
		int N;

		for (i = 0; i < mPrimesLen; i++) {
			N = mPrimeIndexes[i].size();
			if (N == 0)
				continue;
			buildSegTree(1, 0, N - 1, mPrimeIndexes[i], i);
		}
	}

	public void buildSegTree(int node, int begin, int end,
			ArrayList<Integer> indexes, int segTree) {

		int mid;
		int index;

		if (begin > end)
			return;

		if (begin == end) {
			index = indexes.get(begin);
			mSegTree[segTree][node] = new Node(index, mA[index], 1);
			return;
		}

		mid = begin + ((end - begin) >> 1);

		buildSegTree(2 * node, begin, mid, indexes, segTree);
		buildSegTree(2 * node + 1, mid + 1, end, indexes, segTree);

		mSegTree[segTree][node] = new Node();

		mSegTree[segTree][node].merge(mSegTree[segTree][2 * node],
				mSegTree[segTree][2 * node + 1]);

	}

	// Query by checking bounds in the Node struct. The startIndex and endIndex
	// are the actual indexes in the original array containing the max value
	// divisible by a prime factor.
	public Node querySegTree(int node, int begin, int end, int qBegin,
			int qEnd, int segTree) {

		int mid;
		Node left, right, res;

		if (mSegTree[segTree] == null || qBegin > mSegTree[segTree][node].endIndex || qEnd < mSegTree[segTree][node].startIndex)
			return null;

		if (qBegin <= mSegTree[segTree][node].startIndex
				&& qEnd >= mSegTree[segTree][node].endIndex)
			return mSegTree[segTree][node];

		mid = begin + ((end - begin) >> 1);

		left = querySegTree(2 * node, begin, mid, qBegin, qEnd, segTree);
		right = querySegTree(2 * node + 1, mid + 1, end, qBegin, qEnd, segTree);

		if (left == null)
			return right;
		if (right == null)
			return left;

		res = new Node();
		res.merge(left, right);

		return res;
	}

	public void sieveOfErastothenes() {
		int i;
		long j;
		int id;
		boolean primes[];

		primes = new boolean[sMaxNum];
		Arrays.fill(mPrimesId, -1);

		for (i = 2; i < sMaxNum; i++)
			primes[i] = true;

		id = 0;

		for (i = 2; i < sMaxNum; i++) {
			if (!primes[i])
				continue;
			for (j = 1L * i * i; j < sMaxNum; j += i) {
				primes[(int) j] = false;
			}
			mPrimes.add(i);
			mPrimesId[i] = id++;
		}

		mSmallPrimesLen = 70;
		mPrimesLen = mPrimes.size();
	}

	public void allocatePrimeIndexes() {
		mPrimeIndexes = new ArrayList[mPrimesLen];
		for (int i = 0; i < mPrimesLen; i++)
			mPrimeIndexes[i] = new ArrayList<Integer>();
	}

	public void takeInput() {
		N = mFScanner.nextInt();
		M = mFScanner.nextInt();
		mA = new int[N];
		for (int i = 0; i < N; i++) {
			mA[i] = mFScanner.nextInt();
		}
	}

	public void processInput() {
		int i;
		int j;
		int num;
		int prime;
		int id;

		for (i = 0; i < N; i++) {
			num = mA[i];

			for (j = 0; j < mSmallPrimesLen; j++) {
				prime = mPrimes.get(j);
				if (num % prime == 0) {
					mPrimeIndexes[j].add(i);
					while (num % prime == 0) {
						num /= prime;
					}
				}

				if (num == 1 || mPrimesId[num] != -1)
					break;
			}

			if (num > 1) {
				// Prime number left over
				id = mPrimesId[num];
				mPrimeIndexes[id].add(i);
			}

		}
	}

	public void processQueries() {
		int i;
		int j;
		int x;
		int y;
		int N;
		int num;
		int prime;
		int id;
		Node res;
		Node ret;

		res = new Node();

		for (i = 0; i < M; i++) {
			res.reset(-1, -1);
			num = mFScanner.nextInt();
			x = mFScanner.nextInt() - 1;
			y = mFScanner.nextInt() - 1;

			for (j = 0; j < mSmallPrimesLen; j++) {

				prime = mPrimes.get(j);

				if (num % prime == 0) {
					N = mPrimeIndexes[j].size();
					ret = querySegTree(1, 0, N - 1, x, y, j);
					if (ret != null)
						res.updateMax(ret);
				}

				while (num % prime == 0)
					num /= prime;

				if (num == 1 || mPrimesId[num] != -1)
					break;
			}

			if (num > 1) {
				id = mPrimesId[num];
				N = mPrimeIndexes[id].size();
				ret = querySegTree(1, 0, N - 1, x, y, id);
				if (ret != null)
					res.updateMax(ret);
			}

			mOut.print(res.max);
			mOut.print(" ");
			mOut.println(res.freq);
		}
	}

	public void solve() {
		sieveOfErastothenes();
		takeInput();
		allocatePrimeIndexes();
		processInput();
		initSegTree();
		buildSegTree();
		processQueries();
		close();
	}

	public void close() {
		mOut.flush();
		mOut.close();
	}

	public static void main(String[] args) {
		AnuGCD mSol = new AnuGCD();
		mSol.solve();
	}

	class Node {
		int startIndex;
		int endIndex;
		int max;
		int freq;

		public Node() {
			this.max = this.freq = -1;
		}

		// Stores index of the original array where the value max occurs
		public Node(int index, int max, int freq) {
			this.startIndex = this.endIndex = index;
			this.max = max;
			this.freq = freq;
		}

		// Updates the max value in a range along with its frequency.
		// The range is also updated by using the startIndex and endIndex
		// information.
		public void merge(Node node1, Node node2) {
			if (node1.max > node2.max) {
				this.max = node1.max;
				this.freq = node1.freq;
			} else if (node1.max < node2.max) {
				this.max = node2.max;
				this.freq = node2.freq;
			} else {
				this.max = node1.max;
				this.freq = node1.freq + node2.freq;
			}
			this.startIndex = Math.min(node1.startIndex, node2.startIndex);
			this.endIndex = Math.max(node1.endIndex, node2.endIndex);
		}

		public void reset(int max, int freq) {
			this.max = max;
			this.freq = freq;
		}

		public void updateMax(Node node) {
			if (node.max > this.max) {
				this.max = node.max;
				this.freq = node.freq;
			}
		}
	}

}
