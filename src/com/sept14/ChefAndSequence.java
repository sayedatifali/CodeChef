package com.sept14;

import java.io.PrintWriter;
import java.util.HashSet;

/**
 * http://www.codechef.com/SEPT14/problems/UASEQ
 * 
 * @author sultan.of.swing
 *
 */

public class ChefAndSequence {

	public FasterScanner mFScanner;
	public PrintWriter mOut;
	public int N;
	public int K;
	public long mArray[];
	public long mNewArray[];
	public long mTArray[];
	public HashSet<Long> mDiffs;

	public ChefAndSequence() {
		mFScanner = new FasterScanner();
		mOut = new PrintWriter(System.out);
	}

	public void solve() {
		int i;
		long a;
		long d;

		N = mFScanner.nextInt();
		K = mFScanner.nextInt();
		mDiffs = new HashSet<>();

		mArray = new long[N];
		mNewArray = new long[N];
		mTArray = new long[N];
		mNewArray[0] = Long.MIN_VALUE;

		for (i = 0; i < N; i++)
			mArray[i] = mFScanner.nextLong();

		a = 0;
		
		for (i = 1; i < N; i++) {
			d = mArray[i] - mArray[i - 1];
			if (mDiffs.contains(d))
				continue;
			mDiffs.add(d);
			
			a = mArray[i] - i * d;
			
			buildAndCheckArray(a, d);
		}
		
		for (i = 0; i < N; i++)
			mOut.print(mNewArray[i] + " ");

	}

	public void buildAndCheckArray(long a, long d) {
		int i;
		int k;

		k = 0;

		if (mNewArray[0] == Long.MIN_VALUE || mNewArray[0] > a
				|| (mNewArray[0] == a && d < mNewArray[1] - mNewArray[0])) {
			for (i = 0; i < N; i++) {
				mTArray[i] = a + i * d;
			}

			k = getDiff();

			if (k <= K)
				copy();
		}

	}

	public int getDiff() {
		int i;
		int diff;
		diff = 0;

		for (i = 0; i < N; i++)
			if (mArray[i] != mTArray[i])
				diff++;

		return diff;
	}

	public void copy() {
		for (int i = 0; i < N; i++)
			mNewArray[i] = mTArray[i];
	}

	public void flush() {
		mOut.flush();
	}

	public void close() {
		mOut.close();
	}

	public static void main(String[] args) {
		ChefAndSequence mSol = new ChefAndSequence();
		mSol.solve();
		mSol.flush();
		mSol.close();
	}

}
