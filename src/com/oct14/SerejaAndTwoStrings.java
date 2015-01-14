package com.oct14;

import java.io.PrintWriter;
import java.util.Arrays;

/**
 * http://www.codechef.com/OCT14/problems/SEATSR
 * 
 * @author sultan.of.swing
 * 
 */

public class SerejaAndTwoStrings {

	public FasterScanner mFScanner;
	public PrintWriter mOut;
	public String mInitialArray;
	public String mFinalArray;
	public int N;
	public int A, B, K;

	public SerejaAndTwoStrings() {
		mFScanner = new FasterScanner();
		mOut = new PrintWriter(System.out);
	}

	public void solve() {
		int T;
		long res;
		long diff;

		T = mFScanner.nextInt();

		while (T-- > 0) {

			mInitialArray = mFScanner.nextString();
			mFinalArray = mFScanner.nextString();
			A = mFScanner.nextInt();
			B = mFScanner.nextInt();
			K = mFScanner.nextInt();

			if (A == 0) {
				mOut.println(0);
				continue;
			} else if (B == 0) {
				diff = A
						* Math.abs(mInitialArray.length()
								- mFinalArray.length());
				if (diff <= K)
					mOut.println(diff);
				else
					mOut.println(-1);
				continue;
			}

			res = levenshtein(mInitialArray, mFinalArray, K);

			mOut.println(res);

		}

	}

	/**
	 * http://stackoverflow.com/questions/3866249/modifying-levenshtein-distance
	 * -algorithm-to-not-calculate-all-distances
	 * 
	 * @param s
	 * @param t
	 * @param threshold
	 * @return
	 */

	public long levenshtein(String s, String t, int threshold) {
		int slen = s.length();
		int tlen = t.length();

		if (tlen > slen) {
			String stmp = s;
			s = t;
			t = stmp;
			int itmp = slen;
			slen = tlen;
			tlen = itmp;
		}

		long[] p = new long[tlen + 1];
		long[] d = new long[tlen + 1];
		long[] dtmp;

		int n = 0;
		
		for (; n < p.length; ++n)
			p[n] = n * A;

		Arrays.fill(d, Integer.MAX_VALUE);

		for (int row = 1; row <= s.length(); ++row) {
			char schar = s.charAt(row - 1);
			d[0] = row * A;

			// set up our threshold window
			int min = Math.max(1, row - threshold);
			int max = Math.min(d.length, row + threshold + 1);

			if (min > 1 && min <= tlen + 1)
				d[min - 1] = Integer.MAX_VALUE;

			for (int col = min; col < max; ++col) {
				if (schar == t.charAt(col - 1))
					d[col] = p[col - 1];
				else
					// min of: diagonal, left, up
					d[col] = Math.min(p[col - 1] + B,
							Math.min(d[col - 1] + A, p[col] + A));
			}
			dtmp = p;
			p = d;
			d = dtmp;
		}

		if (p[tlen] > K)
			return -1;

		return p[tlen];
	}

	public void flush() {
		mOut.flush();
	}

	public void close() {
		mOut.close();
	}

	public static void main(String[] args) {
		SerejaAndTwoStrings mSol = new SerejaAndTwoStrings();
		mSol.solve();
		mSol.flush();
		mSol.close();
	}

}