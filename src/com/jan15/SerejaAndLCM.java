package com.jan15;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.TreeMap;

/**
 * http://www.codechef.com/JAN15/problems/SEALCM
 * 
 * @author sultan.of.swing
 *
 */
public class SerejaAndLCM {

	public FasterScanner mFScanner;
	public PrintWriter mOut;
	private int T;
	private static final long MOD = 1000000007;
	private static final int MAXM = 1000;
	private int N, M;
	private int L, R;
	private TreeMap<Integer, Integer> mPrimes[];
	private long mCount[][];

	public SerejaAndLCM() {
		mFScanner = new FasterScanner();
		mOut = new PrintWriter(System.out);
	}

	private void init() {
		int i;

		mPrimes = new TreeMap[MAXM + 1];

		for (i = 0; i <= MAXM; i++)
			mPrimes[i] = new TreeMap<Integer, Integer>();

		mCount = new long[MAXM + 1][MAXM + 1];
	}

	private void precompute() {
		int i;
		int j;
		int count;
		int key;
		int val;

		for (i = 1; i <= MAXM; i++)
			computePrimeFactors(i);

		for (i = 1; i <= MAXM; i++) {
			for (j = 1; j <= MAXM; j++) {
				mCount[i][j] = mCount[i][j - 1];
				count = 0;

				for (Map.Entry<Integer, Integer> entry : mPrimes[i].entrySet()) {
					val = 0;
					key = entry.getKey();

					if (mPrimes[j].containsKey(key)) {
						val = mPrimes[j].get(key);
					}

					if (val < entry.getValue()) {
						count++;
					}
				}

				if (count == mPrimes[i].size())
					mCount[i][j]++;
			}
		}
	}

	private void computePrimeFactors(int num) {
		int temp = num;
		int sqrt;
		int count;

		sqrt = (int) Math.sqrt(num);

		for (int fact = 2; fact <= sqrt && temp != 1; fact++) {
			if (temp % fact == 0) {
				count = 0;
				while (temp % fact == 0) {
					temp /= fact;
					count++;
				}
				mPrimes[num].put(fact, count);
			}
		}

		if (temp > 1) {
			mPrimes[num].put(temp, 1);
		}
	}

	private long modPow(long a, long x, long p) {
		// calculates a^x mod p in logarithmic time.
		long res = 1;
		while (x > 0) {
			if (x % 2 != 0) {
				res = (res * a) % p;
			}
			a = (a * a) % p;
			x /= 2;
		}
		return res;
	}

	private long add(long a, long b) {
		long res = (a + b) % MOD;
		return (res + MOD) % MOD;
	}

	private long solveTestCase(int D) {
		long res = 0;
		int powersD[];
		int size;
		int primesD[];
		int i, j, k, l;
		long temp;

		primesD = new int[4];
		powersD = new int[4];

		res = modPow(M, N, MOD);
		i = 0;

		for (Map.Entry<Integer, Integer> entry : mPrimes[D].entrySet()) {
			powersD[i] = entry.getValue();
			primesD[i] = entry.getKey();
			i++;
		}

		size = mPrimes[D].size();

		// A
		for (i = 0; i < size; i++) {
			temp = modPow(primesD[i], powersD[i], MOD);
			temp = mCount[(int) temp][M];
			temp = modPow(temp, N, MOD);
			res = add(res, -temp);
		}

		// A U B
		for (i = 0; i < size; i++) {
			for (j = i + 1; j < size; j++) {
				temp = modPow(primesD[i], powersD[i], MOD)
						* modPow(primesD[j], powersD[j], MOD);
				temp = mCount[(int) temp][M];
				temp = modPow(temp, N, MOD);
				res = add(res, temp);
			}
		}

		// A U B U C
		for (i = 0; i < size; i++) {
			for (j = i + 1; j < size; j++) {
				for (k = j + 1; k < size; k++) {
					temp = modPow(primesD[i], powersD[i], MOD)
							* modPow(primesD[j], powersD[j], MOD)
							* modPow(primesD[k], powersD[k], MOD);
					temp = mCount[(int) temp][M];
					temp = modPow(temp, N, MOD);
					res = add(res, -temp);
				}
			}
		}

		// A U B U C U D
		for (i = 0; i < size; i++) {
			for (j = i + 1; j < size; j++) {
				for (k = j + 1; k < size; k++) {
					for (l = k + 1; l < size; l++) {
						temp = modPow(primesD[i], powersD[i], MOD)
								* modPow(primesD[j], powersD[j], MOD)
								* modPow(primesD[k], powersD[k], MOD)
								* modPow(primesD[l], powersD[l], MOD);
						temp = mCount[(int) temp][M];
						temp = modPow(temp, N, MOD);
						res = add(res, temp);
					}
				}
			}
		}

		return res;
	}

	public void solve() {
		long res;
		long ans;

		T = mFScanner.nextInt();
		init();
		precompute();

		while (T-- > 0) {
			N = mFScanner.nextInt();
			M = mFScanner.nextInt();
			L = mFScanner.nextInt();
			R = mFScanner.nextInt();

			ans = 0;

			for (int D = L; D <= R; D++) {
				res = solveTestCase(D);
				ans = (ans + res) % MOD;
			}

			ans = (ans + MOD) % MOD;

			mOut.println(ans);
		}
	}

	public void flush() {
		mOut.flush();
	}

	public void close() {
		mOut.close();
	}

	public static void main(String[] args) {
		SerejaAndLCM mSol = new SerejaAndLCM();
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