package com.jan15;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;

/**
 * http://www.codechef.com/JAN15/problems/ONEKING
 * 
 * @author sultan.of.swing
 *
 */
public class OneDimensionalKingdoms {

	public FasterScanner mFScanner;
	public PrintWriter mOut;
	private int T;
	private int N;
	private Kingdom mKingdoms[];

	public OneDimensionalKingdoms() {
		mFScanner = new FasterScanner();
		mOut = new PrintWriter(System.out);
	}

	public void solve() {
		int left, right, i;
		int bombs = 0;

		T = mFScanner.nextInt();

		while (T-- > 0) {

			N = mFScanner.nextInt();
			mKingdoms = new Kingdom[N];

			for (i = 0; i < N; i++) {
				left = mFScanner.nextInt();
				right = mFScanner.nextInt();
				mKingdoms[i] = new Kingdom(left, right);
			}

			Arrays.sort(mKingdoms);

			left = mKingdoms[0].left;
			right = mKingdoms[0].right;
			bombs = 1;

			for (i = 1; i < N; i++) {
				if (mKingdoms[i].left <= right) {
					right = Math.min(mKingdoms[i].right, right);
				} else {
					left = mKingdoms[i].left;
					right = mKingdoms[i].right;
					bombs++;
				}
			}

			mOut.println(bombs);

		}

	}

	class Kingdom implements Comparable<Kingdom> {
		int left, right;

		public Kingdom(int left, int right) {
			this.left = left;
			this.right = right;
		}

		@Override
		public int compareTo(Kingdom o) {
			// TODO Auto-generated method stub
			int ret = Integer.compare(this.left, o.left);
			if (ret == 0)
				ret = Integer.compare(o.right, this.right);
			return ret;
		}
	}

	public void flush() {
		mOut.flush();
	}

	public void close() {
		mOut.close();
	}

	public static void main(String[] args) {
		OneDimensionalKingdoms mSol = new OneDimensionalKingdoms();
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