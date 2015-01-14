package com.jan15;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.InputMismatchException;

/**
 * http://www.codechef.com/JAN15/problems/QSET
 * 
 * @author sultan.of.swing
 *
 */

public class QueriesOnString {

	public FasterScanner mFScanner;
	public PrintWriter mOut;
	private static final int REPLACE = 1;
	private int N;
	private int M;
	private char[] mDigits;
	private SegmentTree mSegTree;

	public QueriesOnString() {
		mFScanner = new FasterScanner();
		mOut = new PrintWriter(System.out);
	}

	public void solve() {
		int type;
		int left, right;
		int index, val;
		Node res;
		char value;

		N = mFScanner.nextInt();
		M = mFScanner.nextInt();

		mDigits = mFScanner.nextCharArray(N);
		mSegTree = new SegmentTree(N, mDigits);

		while (M-- > 0) {
			type = mFScanner.nextInt();

			if (type == REPLACE) {
				index = mFScanner.nextInt() - 1;
				val = mFScanner.nextInt();
				value = (char) ('0' + val);
				mSegTree.update(1, 0, N - 1, index, value);
			} else {
				left = mFScanner.nextInt() - 1;
				right = mFScanner.nextInt() - 1;
				res = mSegTree.query(1, 0, N - 1, left, right);
				mOut.println(res.res);
			}
		}

	}

	class SegmentTree {
		private Node mTree[];
		private char[] A;
		private int TN;

		public SegmentTree(int N, char[] A) {
			TN = getMax(N);
			mTree = new Node[TN];
			this.A = A;
			build(1, 0, N - 1);
		}

		private int getMax(int N) {
			TN = N;
			TN = TN | (TN >> 1);
			TN = TN | (TN >> 2);
			TN = TN | (TN >> 4);
			TN = TN | (TN >> 8);
			TN = TN | (TN >> 16);

			TN = (TN + 1) << 1;

			return TN;
		}

		public void build(int node, int begin, int end) {

			if (begin > end)
				return;

			if (begin == end) {
				mTree[node] = new Node(A[begin]);
				return;
			}

			int mid = begin + ((end - begin) >> 1);

			build(2 * node, begin, mid);
			build(2 * node + 1, mid + 1, end);

			mTree[node] = new Node();
			mTree[node].merge(mTree[2 * node], mTree[2 * node + 1]);
		}

		public void update(int node, int begin, int end, int index, char value) {

			if (index < begin || index > end)
				return;

			if (begin == end) {
				mTree[node] = new Node(value);
				A[index] = value;
				return;
			}
			int mid = begin + ((end - begin) >> 1);

			update(2 * node, begin, mid, index, value);
			update(2 * node + 1, mid + 1, end, index, value);

			mTree[node].merge(mTree[2 * node], mTree[2 * node + 1]);
		}

		public Node query(int node, int begin, int end, int qBegin, int qEnd) {

			if (qBegin > end || qEnd < begin)
				return null;

			if (qBegin <= begin && qEnd >= end) {
				return mTree[node];
			}
			int mid = begin + ((end - begin) >> 1);
			Node left, right;
			Node res;

			left = query(2 * node, begin, mid, qBegin, qEnd);
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

		long lmod[];
		long rmod[];
		long res;
		int numMod;

		public Node() {
			lmod = new long[3];
			rmod = new long[3];
			res = 0;
			numMod = 0;
		};

		public Node(char c) {
			this();
			numMod = c - '0';
			numMod %= 3;
			lmod[numMod] = rmod[numMod] = 1;
			if (numMod == 0)
				res = 1;
		}

		public void merge(Node left, Node right) {
			this.res = left.res + right.res;
			this.res += (left.rmod[1] * right.lmod[2] + left.rmod[2]
					* right.lmod[1] + left.rmod[0] * right.rmod[0]);
			for (int i = 0; i < 3; i++) {
				this.lmod[i] = left.lmod[i] + right.lmod[(3 - left.numMod + i) % 3];
				this.rmod[i] = right.rmod[i]
						+ left.rmod[(3 - right.numMod + i) % 3];
			}
			this.numMod = (left.numMod + right.numMod) % 3;
		}

	}

	public void flush() {
		mOut.flush();
	}

	public void close() {
		mOut.close();
	}

	public static void main(String[] args) {
		QueriesOnString mSol = new QueriesOnString();
		mSol.solve();
		mSol.flush();
		mSol.close();
	}

	class FasterScanner {
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