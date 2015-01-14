package com.oct14;

import java.io.PrintWriter;
import java.util.HashSet;

/**
 * http://www.codechef.com/OCT14/problems/CHEFSQUA
 * 
 * @author sultan.of.swing
 * 
 */

public class ChefAndSquare {

	public FasterScanner mFScanner;
	public PrintWriter mOut;
	public int N;
	public int X[];
	public int Y[];
	public HashSet<Long> hashSet;

	public ChefAndSquare() {
		mFScanner = new FasterScanner();
		mOut = new PrintWriter(System.out);
	}

	public void solve() {

		int i;
		int j;
		long K;
		int x1, x2, y1, y2;
		long hash;
		int min = 2;
		int ans;

		N = mFScanner.nextInt();

		hashSet = new HashSet<Long>();
		X = new int[N];
		Y = new int[N];
		K = 30000000;

		for (i = 0; i < N; i++) {
			X[i] = mFScanner.nextInt() + 1000000;
			Y[i] = mFScanner.nextInt() + 1000000;
			hash = 1L * X[i] + 1L * Y[i] * K;
			hashSet.add(hash);
		}

		ans = 2;

		if (N < 3) {
			mOut.println(4 - N);
			return;
		}

		for (i = 0; i < N; i++) {

			for (j = i + 1; j < N; j++) {

				min = 2;

				if (X[i] == X[j] && Y[i] == Y[j])
					continue;

				x1 = X[i] + X[j] + Y[j] - Y[i];
				y1 = Y[i] + Y[j] + X[i] - X[j];

				x2 = X[i] + X[j] + Y[i] - Y[j];
				y2 = Y[i] + Y[j] + X[j] - X[i];

				if (x1 % 2 != 0 || y1 % 2 != 0 || x2 % 2 != 0 || y2 % 2 != 0) {
					continue;
				}

				x1 /= 2;
				y1 /= 2;

				x2 /= 2;
				y2 /= 2;

				hash = 1L * x1 + 1L * y1 * K;
				if (hashSet.contains(hash))
					min--;

				hash = 1L * x2 + 1L * y2 * K;
				if (hashSet.contains(hash))
					min--;

				ans = Math.min(ans, min);

				if (ans == 0)
					break;
			}
			if (ans == 0)
				break;
		}

		mOut.println(ans);
	}

	public void flush() {
		mOut.flush();
	}

	public void close() {
		mOut.close();
	}

	public static void main(String[] args) {
		ChefAndSquare mSol = new ChefAndSquare();
		mSol.solve();
		mSol.flush();
		mSol.close();
	}

}