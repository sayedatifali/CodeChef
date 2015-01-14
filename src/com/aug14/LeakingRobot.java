package com.aug14;

import java.io.PrintWriter;

/**
 * http://www.codechef.com/AUG14/problems/CRAWA
 * 
 * @author sultan.of.swing
 * 
 */

public class LeakingRobot {
	public PrintWriter mOut;
	public FasterScanner mFScanner;
	public static final String sYes = "YES";
	public static final String sNo = "NO";

	public LeakingRobot() {
		mOut = new PrintWriter(System.out);
		mFScanner = new FasterScanner();
	}

	public void solve() {
		int T;
		int x;
		int y;
		boolean ans;

		T = mFScanner.nextInt();

		while (T-- > 0) {
			ans = false;
			x = mFScanner.nextInt();
			y = mFScanner.nextInt();
			ans = firstQuadrant(x, y) | secondQuadrant(x, y)
					| thirdQuadrant(x, y) | fourthQuadrant(x, y);
			if (ans)
				mOut.println(sYes);
			else
				mOut.println(sNo);
		}

	}

	public boolean firstQuadrant(int x, int y) {
		int mul;
		int xL;
		int yL;

		if (!(x >= 0 && y >= 0))
			return false;
		if (x == 0 && y == 0)
			return true;
		if (x == 1 && y == 0)
			return true;

		if (x % 2 == 1) {
			// Odd x-coordinate
			yL = x + 1;
			if (y <= yL)
				return true;
		}

		if (y % 2 == 0) {
			// Even y-coordinate
			xL = y - 1;
			if (x <= xL)
				return true;
		}

		return false;
	}

	public boolean secondQuadrant(int x, int y) {
		int xL, yL;
		if (!(x <= 0 && y >= 0))
			return false;
		x = Math.abs(x);

		if (x % 2 == 0) {
			yL = x;
			if (y <= yL)
				return true;
		}

		if (y % 2 == 0) {
			xL = y;
			if (x <= xL)
				return true;
		}

		return false;
	}

	public boolean thirdQuadrant(int x, int y) {
		int xL, yL;
		if (!(x <= 0 && y <= 0))
			return false;
		x = Math.abs(x);
		y = Math.abs(y);
		if (x % 2 == 0) {
			yL = x;
			if (y <= yL)
				return true;
		}

		if (y % 2 == 0) {
			xL = y;
			if (x <= xL)
				return true;
		}
		return false;
	}

	public boolean fourthQuadrant(int x, int y) {
		int xL, yL;
		if (!(x >= 0 && y <= 0))
			return false;
		y = Math.abs(y);

		if (x % 2 == 1) {
			yL = x - 1;
			if (y <= yL)
				return true;
		}

		if (y % 2 == 0) {
			xL = y + 1;
			if (x <= xL)
				return true;
		}

		return false;
	}

	public void flush() {
		mOut.flush();
	}

	public void close() {
		mOut.close();
	}

	public static void main(String[] args) {
		LeakingRobot mSol = new LeakingRobot();
		mSol.solve();
		mSol.flush();
		mSol.close();
	}
}