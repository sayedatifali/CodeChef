package com.july14;

import java.io.PrintWriter;

import com.io.FasterScanner;

public class GardenGame {

	public FasterScanner mFScanner;
	public PrintWriter mOut;
	public static int sMOD = 1000000007;
	public int MAX = 100000;
	public int mArray[];

	public GardenGame() {
		mFScanner = new FasterScanner();
		mOut = new PrintWriter(System.out);
	}

	public void solve() {
		int i;
		int T;
		int N;

		T = mFScanner.nextInt();
		mArray = new int[MAX];

		while (T-- > 0) {
			N = mFScanner.nextInt();
			
			for (i = 0; i < N; i++)
				mArray[i] = mFScanner.nextInt();
			
		}

	}

	public void close() {
		mOut.flush();
		mOut.close();
	}

	public static void main(String[] args) {
		GardenGame mSol = new GardenGame();
		mSol.solve();
	}

}
