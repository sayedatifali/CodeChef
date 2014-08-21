package com.july14;

import java.io.PrintWriter;

import com.io.FasterScanner;

public class ReachThePoint {

	public FasterScanner mFScanner;
	public PrintWriter mOut;

	public ReachThePoint() {
		mFScanner = new FasterScanner();
		mOut = new PrintWriter(System.out);
	}

	public void solve() {
		int T;
		long x, y, ans;
		long min;

		T = mFScanner.nextInt();

		while (T-- > 0) {
			x = Math.abs(mFScanner.nextLong());
			y = Math.abs(mFScanner.nextLong());

			min = Math.min(x, y);
			ans = 2 * min;
			x -= min;
			y -= min;

			if (x == 0 && y > 0) {
				ans += 2 * y;
				if (y % 2 != 0)
					ans -= 1;
			} else if (y == 0 && x > 0) {
				if (x % 2 == 0)
					ans += 2 * x;
				else
					ans += 2 * x + 1;
			}

			mOut.println(ans);
		}

		close();
	}

	public void close() {
		mOut.flush();
		mOut.close();
	}

	public static void main(String[] args) {
		ReachThePoint mSol = new ReachThePoint();
		mSol.solve();
	}

}
