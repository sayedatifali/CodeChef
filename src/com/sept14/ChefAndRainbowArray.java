package com.sept14;

import java.io.PrintWriter;

/**
 * http://www.codechef.com/SEPT14/problems/RAINBOWB
 * 
 * @author sultan.of.swing
 *
 */

public class ChefAndRainbowArray {

	public FasterScanner mFScanner;
	public PrintWriter mOut;
	public static final int MOD = 1000000007;

	public ChefAndRainbowArray() {
		mFScanner = new FasterScanner();
		mOut = new PrintWriter(System.out);
	}

	public void solve() {
		int N;
		int rem;
		long ways;

		N = mFScanner.nextInt();

		if (N < 13) {
			mOut.println(0);
			return;
		}

		ways = 1;
		rem = N - 13;

		if (rem % 2 == 1)
			rem -= 1;

		rem /= 2;

		ways = nCr(rem + 6, 6);

		mOut.println(ways);

	}

	public long nCr(long N, long R) {

		long ans = 1;

		for (long r = 1; r <= R; r++) {
			ans = ((ans * (N - r + 1)) % MOD) * modInverse(r, MOD);
			ans %= MOD;
		}

		return ans;

	}

	public long modPow(long a, long x, long p) {
		// calculates a^x mod p in logarithmic time.
		long res = 1;
		while (x > 0) {
			if (x % 2 != 0) {
				res = (res * a) % p;
			}
			a = (a * a) % p;
			x /= 2;
		}
		return res;
	}

	public long modInverse(long a, long p) {
		return modPow(a, p - 2, p);
	}


	public void flush() {
		mOut.flush();
	}

	public void close() {
		mOut.close();
	}

	public static void main(String[] args) {
		ChefAndRainbowArray mSol = new ChefAndRainbowArray();
		mSol.solve();
		mSol.flush();
		mSol.close();
	}

}
