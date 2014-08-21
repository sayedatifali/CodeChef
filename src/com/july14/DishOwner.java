package com.july14;

import java.io.PrintWriter;

import com.io.FasterScanner;

/**
 * http://www.codechef.com/JULY14/problems/DISHOWN
 * 
 * @author sultan.of.swing
 * 
 */

public class DishOwner {

	public FasterScanner mFScanner;
	public PrintWriter mOut;
	public int mChef[];
	public int mScore[];

	public DishOwner() {
		mFScanner = new FasterScanner();
		mOut = new PrintWriter(System.out);
	}

	public void solve() {
		int i;
		int T;
		int N;
		int Q;
		int x, y;
		int chef;

		T = mFScanner.nextInt();
		mChef = new int[10000];
		mScore = new int[10000];

		while (T-- > 0) {
			N = mFScanner.nextInt();

			for (i = 0; i < N; i++) {
				mChef[i] = i;
				mScore[i] = mFScanner.nextInt();
			}

			Q = mFScanner.nextInt();

			for (i = 0; i < Q; i++) {
				x = mFScanner.nextInt();
				if (x == 0) {
					x = mFScanner.nextInt() - 1;
					y = mFScanner.nextInt() - 1;
					if (!union(x, y)) {
						mOut.println("Invalid query!");
					}
				} else {
					x = mFScanner.nextInt() - 1;
					chef = root(x) + 1;
					mOut.println(chef);
				}
			}

		}

		close();

	}

	public boolean union(int x, int y) {
		int rootX, rootY;

		rootX = root(x);
		rootY = root(y);

		if (rootX == rootY)
			return false;

		if (mScore[rootX] == mScore[rootY])
			return true;

		if (mScore[rootX] > mScore[rootY]) {
			mScore[rootY] = mScore[rootX];
			mChef[rootY] = rootX;
		} else {
			mScore[rootX] = mScore[rootY];
			mChef[rootX] = rootY;
		}

		return true;
	}

	public int root(int x) {
		int max;

		max = mScore[x];

		while (x != mChef[x]) {
			x = mChef[x];
			max = Math.max(max, mScore[x]);
			mScore[x] = max;
		}

		return x;
	}

	public void close() {
		mOut.flush();
		mOut.close();
	}

	public static void main(String[] args) {
		DishOwner mSol = new DishOwner();
		mSol.solve();
	}

}
