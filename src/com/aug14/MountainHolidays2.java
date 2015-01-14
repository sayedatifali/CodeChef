package com.aug14;

import java.io.PrintWriter;
import java.util.HashMap;

/**
 * http://www.codechef.com/AUG14/problems/MOU2H
 * 
 * @author sultan.of.swings
 *
 */

public class MountainHolidays2 {

	public FasterScanner mFScanner;
	public PrintWriter mOut;
	public int N;
	public int mArray[];
	public int mHeight[];
	public static final long MOD = 1000000009;

	public MountainHolidays2() {
		mFScanner = new FasterScanner();
		mOut = new PrintWriter(System.out);
		mArray = new int[1000000];
		mHeight = new int[1000000];
	}

	public void solve() {
		int T;
		int ans;

		T = mFScanner.nextInt();

		while (T-- > 0) {
			N = mFScanner.nextInt();
			for (int i = 0; i < N; i++) {
				mArray[i] = mFScanner.nextInt();
				if (i > 0) {
					mHeight[i - 1] = mArray[i] - mArray[i - 1];
				}
			}
			
			ans = solveDP(N - 1);
			mOut.println(ans);
		}
	}
	
	public int solveDP(int N) {
		long [] dp;
		HashMap<Integer, Integer> last;
		
		dp = new long[N + 1];
		last = new HashMap<>();
		
		dp[0] = 1;
		
		for (int i = 1; i <= N; i++) {
			dp[i] = (dp[i - 1] * 2) % MOD;
			if (last.containsKey(mHeight[i - 1])) {
				dp[i] = (dp[i] + MOD - dp[last.get(mHeight[i - 1]) - 1]) % MOD;
			}
			last.put(mHeight[i - 1], i);
		}
		
		
		return (int) ((dp[N] + MOD - 1) % MOD);
	}

	public void flush() {
		mOut.flush();
	}

	public void close() {
		mOut.close();
	}

	public static void main(String[] args) {
		MountainHolidays2 mSol = new MountainHolidays2();
		mSol.solve();
		mSol.flush();
		mSol.close();
	}

}
