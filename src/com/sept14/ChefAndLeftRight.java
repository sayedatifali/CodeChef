package com.sept14;

import java.io.PrintWriter;

/**
 * http://www.codechef.com/SEPT14/problems/CHEFLR
 * 
 * @author sultan.of.swing
 *
 */

public class ChefAndLeftRight {

	public FasterScanner mFScanner;
	public PrintWriter mOut;
	public static final int MOD = 1000000007;

	public ChefAndLeftRight() {
		mFScanner = new FasterScanner();
		mOut = new PrintWriter(System.out);
	}

	public void solve() {
		int T;
		String S;
		int len;
		int level;
		long ans;

		T = mFScanner.nextInt();

		while (T-- > 0) {
			S = mFScanner.nextLine();
			len = S.length();
			level = 1;
			ans = 1;

			for (; level <= len; level++) {
				if (level % 2 == 1) {
					if (S.charAt(level - 1) == 'l') {
						ans = (ans * 2) % MOD;
					} else {
						ans = (ans * 2 + 2) % MOD;
					}
				} else {
					ans = (ans * 2 - 1) % MOD;
					if (S.charAt(level - 1) == 'r')
						ans = (ans + 2) % MOD;
				}
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
		ChefAndLeftRight mSol = new ChefAndLeftRight();
		mSol.solve();
		mSol.flush();
		mSol.close();
	}

}
