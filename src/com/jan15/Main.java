package com.jan15;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Random;

/**
 * http://www.codechef.com/JAN15/problems/SEAND2
 * 
 * @author sultan.of.swing
 *
 */

public class Main {

	public FasterScanner mFScanner;
	public PrintWriter mOut;
	private int T;
	private int N;
	private int[][] mB;
	private char[][] mNum;
	private char[][] mMin;
	private char[][] mTemp;
	private HashMap<Integer, Integer> mMod[];
	private long mOpsTestCase;
	private static final long MAX_OPS = 47500000000000L;
	private static long MAX_OPS_TEST_CASE;
	private int mLen;
	private Random mRandom;

	public Main() {
		mFScanner = new FasterScanner();
		mOut = new PrintWriter(System.out);
	}

	public void solve() {
		long n;
		long temp;
		int val;
		
		T = mFScanner.nextInt();
		mMod = new HashMap[T];
		mNum = new char[T][];
		mB = new int[T][];
		mMin = new char[T][];
		mTemp = new char[T][];

		n = 0;
		for (int i = 0; i < T; i++) {
			mNum[i] = mFScanner.nextString().toCharArray();
			N = mFScanner.nextInt();
			mB[i] = mFScanner.nextIntArray(N);
			mMin[i] = new char[mNum[i].length];
			mTemp[i] = new char[mNum[i].length];
			System.arraycopy(mNum[i], 0, mMin[i], 0, mNum[i].length);

			mMod[i] = new HashMap<Integer, Integer>();

			for (int j = 0; j < mB[i].length; j++) {
				val = 0;
				if (mMod[i].containsKey(mB[i][j])) {
					val = mMod[i].get(mB[i][j]);
				}
				val += 1;
				mMod[i].put(mB[i][j], val);
			}

			temp = 1L * mNum[i].length * mMod[i].size();
			n += temp;
		}

		MAX_OPS_TEST_CASE = MAX_OPS / n;

		for (int i = 0; i < T; i++) {
			mOpsTestCase = 0;
			mLen = mNum[i].length;
			mRandom = new Random();
			solveTestCase(i);

			for (int j = 0; j < mLen; j++) {
				mOut.print(mMin[i][j]);
			}
			mOut.println();
		}
	}

	private void solveTestCase(int t) {
		int i;
		int mod;
		int tempMod;
		int minMod;
		char[] temp;
		int val;

		minMod = Integer.MAX_VALUE;

		mod = 0;
		for (int modVal : mMod[t].keySet()) {
			tempMod = 0;
			for (i = 0; i < mLen; i++) {
				tempMod = (tempMod * 10 + (mNum[t][i] - '0')) % modVal;
			}
			val = mMod[t].get(modVal);
			mod += val * tempMod;
		}

		mOpsTestCase += (mLen * mMod[t].size());

		minMod = mod;

		while (mOpsTestCase < MAX_OPS_TEST_CASE) {
			shuffle(t);

			mod = getMod(t);

			if (mod < minMod) {
				temp = mMin[t];
				mMin[t] = mTemp[t];
				mTemp[t] = temp;
				minMod = mod;
			}
		}

	}

	private int getMod(int t) {
		int i, tempMod, mod;
		int val;

		mod = 0;
		for (int modVal : mMod[t].keySet()) {
			tempMod = 0;
			for (i = 0; i < mLen; i++) {
				tempMod = (tempMod * 10 + (mTemp[t][i] - '0')) % modVal;
			}
			val = mMod[t].get(modVal);
			mod += tempMod * val;
		}

		mOpsTestCase += (mMod[t].size() * mLen);

		return mod;
	}

	private void shuffle(int t) {
		int randIndex;
		int j = 0;
		char temp;

		for (int i = mLen - 1; i >= 0; i--) {
			randIndex = randInt(0, i);
			mTemp[t][j] = mNum[t][randIndex];
			temp = mNum[t][randIndex];
			mNum[t][randIndex] = mNum[t][i];
			mNum[t][i] = temp;
			j++;
		}
		mOpsTestCase += mLen;
	}

	private int randInt(int min, int max) {
		return mRandom.nextInt((max - min) + 1) + min;
	}

	public void flush() {
		mOut.flush();
	}

	public void close() {
		mOut.close();
	}

	public static void main(String[] args) {
		Main mSol = new Main();
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