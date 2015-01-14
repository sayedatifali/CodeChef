package com.oct14;

import java.io.PrintWriter;

/**
 * http://www.codechef.com/OCT14/problems/CHEFGR
 * 
 * @author sultan.of.swing
 * 
 */

public class ChefAndGround {

	public FasterScanner mFScanner;
	public PrintWriter mOut;

	public ChefAndGround() {
		mFScanner = new FasterScanner();
		mOut = new PrintWriter(System.out);
	}

	public void solve() {
		int T;
		int N;
		int M;
		int max;
		int total;
		int array[];

		T = mFScanner.nextInt();

		while (T-- > 0) {

			N = mFScanner.nextInt();
			M = mFScanner.nextInt();
			array = new int[N];

			max = Integer.MIN_VALUE;

			for (int i = 0; i < N; i++) {
				array[i] = mFScanner.nextInt();
				max = Math.max(max, array[i]);
			}

			total = 0;

			for (int i = 0; i < N; i++) {
				total += (max - array[i]);
			}

			if ((M - total) % N == 0)
				mOut.println("Yes");
			else
				mOut.println("No");

		}

	}

	public void flush() {
		mOut.flush();
	}

	public void close() {
		mOut.close();
	}

	public static void main(String[] args) {
		ChefAndGround mSol = new ChefAndGround();
		mSol.solve();
		mSol.flush();
		mSol.close();
	}

}