package com.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.InputMismatchException;

public class FasterScanner {
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
		return Double.parseDouble(nextString());
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
	
	public String[] nextStringArray(int N) {
		int i;
		String [] array;
		
		array = new String[N];
		
		for (i = 0; i < N; i++) {
			array[i] = nextString();
		}
		
		return array;
	}
	
	public String[][] nextString2DArray(int M, int N) {
		int i;
		String [][] array;
		
		array = new String[M][N];
		
		for (i = 0; i < M; i++) {
			array[i] = nextStringArray(N);
		}
		
		return array;
	}
}
