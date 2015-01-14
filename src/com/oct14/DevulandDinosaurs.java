package com.oct14;

import java.io.PrintWriter;

/**
 * http://www.codechef.com/OCT14/problems/PRLADDU
 * 
 * @author sultan.of.swing
 * 
 */

public class DevulandDinosaurs {

	public FasterScanner mFScanner;
	public PrintWriter mOut;
	public long mArray[];

	public DevulandDinosaurs() {
		mFScanner = new FasterScanner();
		mOut = new PrintWriter(System.out);
	}

	public void solve() {
		int T;
		int n;

		T = mFScanner.nextInt();

		while (T-- > 0) {
			n = mFScanner.nextInt();
			mArray = new long[n];

			for (int i = 0; i < n; i++)
				mArray[i] = mFScanner.nextLong();
			
			solveTestCase(n);

		}
	}

	public void solveTestCase(int n) {
		int i;
		int j;
		long min;
		long minDist = 0;

		i = n - 1;
		j = n - 1;

		while (i >= 0) {

			if (mArray[i] < 0) {

				if (j == i)
					j--;

				while (j >= 0 && mArray[i] != 0) {
					if (mArray[j] <= 0) {
						j--;
						continue;
					}

					min = Math.min(Math.abs(mArray[i]), mArray[j]);

					mArray[i] += min;
					mArray[j] -= min;

					minDist = minDist + Math.abs(i - j) * min;

					if (mArray[i] == 0)
						break;

					j--;
				}
			}
			i--;
		}

		mOut.println(minDist);

	}

	public void flush() {
		mOut.flush();
	}

	public void close() {
		mOut.close();
	}

	public static void main(String[] args) {
		DevulandDinosaurs mSol = new DevulandDinosaurs();
		mSol.solve();
		mSol.flush();
		mSol.close();
	}

}