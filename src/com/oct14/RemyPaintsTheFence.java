package com.oct14;

import java.io.PrintWriter;
import java.util.Arrays;

/**
 * http://www.codechef.com/OCT14/problems/CHEFGR
 * 
 * @author sultan.of.swing
 * 
 */

public class RemyPaintsTheFence {

	public FasterScanner mFScanner;
	public PrintWriter mOut;
	public char mArray[];

	public RemyPaintsTheFence() {
		mFScanner = new FasterScanner();
		mOut = new PrintWriter(System.out);
	}

	public void solve() {
		int T;
		int N;
		int M;
		int index;
		int prevIndex;
		long ans;
		long num;
		long mod = 1000000009;
		char color;
		char prevColor;

		T = mFScanner.nextInt();

		mArray = new char[100001];

		while (T-- > 0) {

			N = mFScanner.nextInt();
			M = mFScanner.nextInt();

			ans = 1;

			Arrays.fill(mArray, '0');

			for (int i = 0; i < M; i++) {
				color = mFScanner.nextString().charAt(0);
				index = mFScanner.nextInt() - 1;
				mArray[index] = color;
			}

			if (M == 1) {
				mOut.println(1);
				continue;
			}

			ans = 1;

			index = 0;

			while (mArray[index] == '0')
				index++;

			prevColor = mArray[index];
			prevIndex = index;

			index++;

			for (; index < N; index++) {
				color = mArray[index];
				if (color == '0')
					continue;
				if (color == prevColor) {
					prevIndex = index;
					continue;
				}

				num = (index - prevIndex);
				ans = (ans * num) % mod;

				prevColor = color;
				prevIndex = index;
			}

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
		RemyPaintsTheFence mSol = new RemyPaintsTheFence();
		mSol.solve();
		mSol.flush();
		mSol.close();
	}

}