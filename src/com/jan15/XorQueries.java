package com.jan15;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;

/**
 * http://www.codechef.com/JAN15/problems/XRQRS
 *
 * TLE in CodeChef Subtask 2 :(
 * @author sultan.of.swing
 *
 */
class XorQueries {

	public static FasterScanner mFScanner;
	public static PrintWriter mOut;
	private static int M;
	private static final int APPEND = 0;
	private static final int MAXIMIZE_XOR = 1;
	private static final int DEL_LAST_K_NUM = 2;
	private static final int COUNT_NUMS_LESS_THAN = 3;
	private static int L, R, K;
	private static final int MAXN = 5 * 100000;
	private static final int MAX_XOR_NODES = 1 << 20;
	private static final int MAX_BITS = 18;
	private static Node mRoot[] = new Node[MAXN + 1];
	private static ArrayList<Integer> mXorNodes[] = new ArrayList[MAX_XOR_NODES];
	private static int mNum[] = new int[MAXN + 1];
	private static Node mNullNode;

	public static void init() {
		mFScanner = new FasterScanner();
		mOut = new PrintWriter(System.out);
		for (int i = 0; i < MAX_XOR_NODES; i++)
			mXorNodes[i] = new ArrayList<Integer>();
	}

	private static void solve() {
		int type;
		int num;
		int i = 0;
		int index;
		int lessL;
		int lessR;
		int res;

		init();
		M = mFScanner.nextInt();

		index = 1;
		mNullNode = new Node(0);
		mNullNode.left = mNullNode.right = mNullNode;
		mRoot[0] = build(0, MAXN, -1);

		for (i = 0; i < M; i++) {
			type = mFScanner.nextInt();

			if (type == APPEND) {
				num = mFScanner.nextInt();
				if (mRoot[index] == null)
					mRoot[index] = update(mRoot[index - 1], 0, MAXN, num);
				else
					updateReuse(mRoot[index - 1], mRoot[index], 0, MAXN, num);
				insertXor(1, num, index - 1, MAX_BITS);
				mNum[index - 1] = num;
				index++;
			} else if (type == MAXIMIZE_XOR) {
				L = mFScanner.nextInt() - 1;
				R = mFScanner.nextInt() - 1;
				num = mFScanner.nextInt();
				res = queryXor(1, num, L, R, MAX_BITS);
				mOut.println(res);
//				mOut.println(res ^ num);
			} else if (type == DEL_LAST_K_NUM) {
				K = mFScanner.nextInt();
				index -= K;
				for (int j = index + K - 2; K > 0; K--, j--) {
					num = mNum[j];
					deleteXor(1, num, j, MAX_BITS);
				}
			} else if (type == COUNT_NUMS_LESS_THAN) {
				L = mFScanner.nextInt();
				R = mFScanner.nextInt();
				num = mFScanner.nextInt();
				lessL = 0;
				if (L - 1 != 0)
					lessL = queryLessThanNum(mRoot[L - 1], 0, MAXN, 0, num);
				lessR = queryLessThanNum(mRoot[R], 0, MAXN, 0, num);
				mOut.println(lessR - lessL);
			} else {
				// Kth order statistic
				L = mFScanner.nextInt();
				R = mFScanner.nextInt();
				K = mFScanner.nextInt();
				res = queryKthOrder(mRoot[L - 1], mRoot[R], 0, MAXN, K);
				mOut.println(res);
			}
		}
	}

	private static void insertXor(int node, int value, int key, int msb) {
		if (msb < 0)
			return;

		int bit = (value & (1 << msb)) >> msb;

		mXorNodes[2 * node + bit].add(key);
		insertXor(2 * node + bit, value, key, msb - 1);
	}

	private static void deleteXor(int node, int value, int key, int msb) {
		if (msb < 0)
			return;

		int bit = (value & (1 << msb)) >> msb;
		int last = mXorNodes[2 * node + bit].size() - 1;
		mXorNodes[2 * node + bit].remove(last);
		deleteXor(2 * node + bit, value, key, msb - 1);
	}

	private static boolean findXor(int node, int left, int right) {
		int index;
		index = lowerBound(mXorNodes[node], 0, mXorNodes[node].size() - 1, left);

		return index != mXorNodes[node].size()
				&& mXorNodes[node].get(index) <= right;
	}

	private static int queryXor(int node, int num, int left, int right, int msb) {
		if (msb < 0)
			return 0;

		int bit = (num & (1 << msb)) >> msb;
		int opposite = bit ^ 1;

		if (findXor(2 * node + opposite, left, right)) {
			return (opposite << msb)
					+ queryXor(2 * node + opposite, num, left, right, msb - 1);
		} else {
			return (bit << msb) + queryXor(2 * node + bit, num, left, right, msb - 1);
		}
	}

	private static int queryKthOrder(Node nodeL, Node nodeR, int begin,
			int end, int k) {

		int mid;
		int count;

		if (begin == end)
			return begin;

		mid = begin + ((end - begin) >> 1);

		count = nodeR.left.count - nodeL.left.count;

		if (count >= k)
			return queryKthOrder(nodeL.left, nodeR.left, begin, mid, k);

		return queryKthOrder(nodeL.right, nodeR.right, mid + 1, end, k - count);

	}

	private static int queryLessThanNum(Node node, int begin, int end,
			int qBegin, int qEnd) {

		int mid = begin + ((end - begin) >> 1);

		if (begin == end) {
			return node.count;
		}

		if (qEnd > mid)
			return node.left.count
					+ queryLessThanNum(node.right, mid + 1, end, qBegin, qEnd);

		return queryLessThanNum(node.left, begin, mid, qBegin, qEnd);

	}

	private static int lowerBound(ArrayList<Integer> array, int first,
			int last, int val) {

		int count, step, it;

		count = last - first + 1;

		while (count > 0) {
			it = first;
			step = count / 2;
			it += step;

			if (array.get(it) < val) {
				first = ++it;
				count -= step + 1;
			} else {
				count = step;
			}
		}
		return first;
	}

	private static Node build(int begin, int end, int index) {

		if (index < begin || index > end)
			return mNullNode;

		if (begin == end) {
			return new Node(1);
		}

		int mid = begin + ((end - begin) >> 1);
		Node node;

		node = new Node(build(begin, mid, index), build(mid + 1, end, index));

		return node;
	}

	private static Node update(Node prev, int begin, int end, int index) {

		if (index < begin || index > end)
			return prev;

		if (begin == end) {
			return new Node(prev.count + 1);
		}

		int mid = begin + ((end - begin) >> 1);
		Node node;

		node = new Node(update(prev.left, begin, mid, index), update(
				prev.right, mid + 1, end, index));

		return node;
	}

	private static void updateReuse(Node prev, Node cur, int begin, int end,
			int index) {

		if (index < begin || index > end)
			return;

		if (begin == end) {
			cur.count = prev.count + 1;
			return;
		}

		int mid = begin + ((end - begin) >> 1);

		updateReuse(prev.left, cur.left, begin, mid, index);
		updateReuse(prev.right, cur.right, mid + 1, end, index);

		cur.update(cur.left, cur.right);
	}

	static class Node {
		Node left, right;
		int count;

		public Node(int count) {
			this.count = count;
			this.left = this.right = null;
		}

		public Node(Node left, Node right) {
			this(left.count + right.count, left, right);
		}

		public Node(int count, Node left, Node right) {
			this.count = count;
			this.left = left;
			this.right = right;
		}

		public void update(Node left, Node right) {
			this.count = left.count + right.count;
			this.left = left;
			this.right = right;
		}

	}

	public static void flush() {
		mOut.flush();
	}

	public static void close() {
		mOut.close();
	}

	public static void main(String[] args) {
		XorQueries.solve();
		XorQueries.flush();
		XorQueries.close();
	}

	static class FasterScanner {
		private InputStream mIs;
		private byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;

		public FasterScanner() {
			this(System.in);
		}

		public FasterScanner(InputStream is) {
			mIs = is;
		}

		public int read() {
			if (numChars == -1)
				throw new InputMismatchException();
			if (curChar >= numChars) {
				curChar = 0;
				try {
					numChars = mIs.read(buf);
				} catch (IOException e) {
					throw new InputMismatchException();
				}
				if (numChars <= 0)
					return -1;
			}
			return buf[curChar++];
		}

		public String nextLine() {
			int c = read();
			while (isSpaceChar(c))
				c = read();
			StringBuilder res = new StringBuilder();
			do {
				res.appendCodePoint(c);
				c = read();
			} while (!isEndOfLine(c));
			return res.toString();
		}

		public String nextString() {
			int c = read();
			while (isSpaceChar(c))
				c = read();
			StringBuilder res = new StringBuilder();
			do {
				res.appendCodePoint(c);
				c = read();
			} while (!isSpaceChar(c));
			return res.toString();
		}

		public long nextLong() {
			int c = read();
			while (isSpaceChar(c))
				c = read();
			int sgn = 1;
			if (c == '-') {
				sgn = -1;
				c = read();
			}
			long res = 0;
			do {
				if (c < '0' || c > '9')
					throw new InputMismatchException();
				res *= 10;
				res += c - '0';
				c = read();
			} while (!isSpaceChar(c));
			return res * sgn;
		}

		public int nextInt() {
			int c = read();
			while (isSpaceChar(c))
				c = read();
			int sgn = 1;
			if (c == '-') {
				sgn = -1;
				c = read();
			}
			int res = 0;
			do {
				if (c < '0' || c > '9')
					throw new InputMismatchException();
				res *= 10;
				res += c - '0';
				c = read();
			} while (!isSpaceChar(c));
			return res * sgn;
		}

		public double nextDouble() {
			Double next;
			next = Double.parseDouble(nextString());
			return next;
		}

		public boolean isSpaceChar(int c) {
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}

		public boolean isEndOfLine(int c) {
			return c == '\n' || c == '\r' || c == -1;
		}

		public char[] nextCharArray(int N) {
			int i;
			char[] array;
			String str;

			array = new char[N];

			i = 0;

			str = nextLine();

			for (i = 0; i < N && i < str.length(); i++) {
				array[i] = str.charAt(i);
			}

			return array;
		}

		public char[][] nextChar2DArray(int M, int N) {
			int i;
			char[][] array;

			array = new char[M][N];

			i = 0;

			for (i = 0; i < M; i++) {
				array[i] = nextCharArray(N);
			}

			return array;
		}

		public int[] nextIntArray(int N) {
			int i;
			int[] array;

			array = new int[N];

			i = 0;

			for (i = 0; i < N; i++) {
				array[i] = nextInt();
			}

			return array;
		}

		public int[][] nextInt2DArray(int M, int N) {
			int i;
			int[][] array;

			array = new int[M][N];

			i = 0;

			for (i = 0; i < M; i++) {
				array[i] = nextIntArray(N);
			}

			return array;
		}

		public long[] nextLongArray(int N) {
			int i;
			long[] array;

			array = new long[N];

			i = 0;

			for (i = 0; i < N; i++) {
				array[i] = nextLong();
			}

			return array;
		}

		public long[][] nextLong2DArray(int M, int N) {
			int i;
			long[][] array;

			array = new long[M][N];

			i = 0;

			for (i = 0; i < M; i++) {
				array[i] = nextLongArray(N);
			}

			return array;
		}

		public double[] nextDoubleArray(int N) {
			int i;
			double[] array;

			array = new double[N];

			for (i = 0; i < N; i++) {
				array[i] = nextDouble();
			}

			return array;
		}

		public double[][] nextDouble2DArray(int M, int N) {
			int i;
			double[][] array;

			array = new double[M][N];

			for (i = 0; i < M; i++) {
				array[i] = nextDoubleArray(N);
			}

			return array;
		}

	}
}