package com.jan15;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;

/**
 * http://www.codechef.com/JAN15/problems/RANKA
 * 
 * @author sultan.of.swing
 *
 */
public class RankA {

	public FasterScanner mFScanner;
	public PrintWriter mOut;
	private int N;
	private int K;
	private ArrayList<Integer> mX;
	private ArrayList<Integer> mY;
	private static final int BOARD_SIDE = 9;
	private static final int BOARD_SIZE = 81;

	public RankA() {
		mFScanner = new FasterScanner();
		mOut = new PrintWriter(System.out);
	}

	public void solve() {
		int i;

		N = mFScanner.nextInt();
		mX = new ArrayList<Integer>();
		mY = new ArrayList<Integer>();

		K = 1;

		solveTestCase();

		for (i = 0; i < N; i++) {
			mOut.print(mX.get(i));
			mOut.print(" ");
			mOut.print(mY.get(i));
			mOut.println();
		}

	}

	public void solveTestCase() {

		int i = 1;
		int j = 1;
		int countBlack = 0;
		int countWhite = 0;

		while (mX.size() < N) {

			for (i = 1; i <= BOARD_SIDE; i++) {
				for (j = 1; j <= BOARD_SIDE; j++) {
					mX.add(i);
					mY.add(j);
					countBlack++;

					if (countBlack == BOARD_SIZE - K) {
						break;
					} else {
						// Pass
						mX.add(0);
						mY.add(0);
					}
				}
				if (countBlack == BOARD_SIZE - K) {
					break;
				}
			}

			if (countBlack == BOARD_SIZE - K) {
				if (j == BOARD_SIDE) {
					mX.add(i + 1);
					mY.add(1);
				} else {
					mX.add(i);
					mY.add(j + 1);
				}
				countWhite++;
				K += 1;
				countBlack = 0;
			}

			if (countWhite == 78)
				break;

		}

		K = 1;
		countBlack = 0;
		countWhite = 0;
		int count = 2;

		// mOut.println("Switching Strategies at " + mX.size());

		mX.add(1);
		mY.add(2);
		mX.add(1);
		mY.add(1);
		mX.add(1);
		mY.add(3);
		mX.add(0);
		mY.add(0);

		while (mX.size() < N) {

			for (j = 1; j <= BOARD_SIDE; j++) {
				for (i = BOARD_SIDE; i > 0; i--) {

					if (count != 0 && indexPenalty(i, j)) {
						count--;
						countBlack++;
						continue;
					}

					mX.add(i);
					mY.add(j);
					countBlack++;
					if (countBlack == BOARD_SIZE - K)
						break;
					else {
						mX.add(0);
						mY.add(0);
					}
				}
				if (countBlack == BOARD_SIZE - K)
					break;
			}

			if (countBlack == BOARD_SIZE - K) {
				if (i == 1) {
					mX.add(BOARD_SIDE);
					mY.add(j + 1);
				} else {
					mX.add(i - 1);
					mY.add(j);
				}
				countWhite++;
				K += 1;
				countBlack = 0;
			}
		}

	}

	private boolean indexPenalty(int i, int j) {
		if (i == 1 && j == 2)
			return true;
		if (i == 1 && j == 3)
			return true;
		return false;
	}

	public void flush() {
		mOut.flush();
	}

	public void close() {
		mOut.close();
	}

	public static void main(String[] args) {
		RankA mSol = new RankA();
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