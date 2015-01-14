package com.aug14;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 * http://www.codechef.com/AUG14/problems/TSHIRTS
 * 
 * @author sultan.of.swing
 *
 */

public class LittleElephantsAndTShirts {

	public FasterScanner mFScanner;
	public PrintWriter mOut;
	public int N;
	public int mTShirts;
	public ArrayList<Integer> mArray[];
	public ArrayList<Integer> mTShirtWearingPerson[];
	public long mMem[][];
	public int mMap[];
	public static final long MOD = 1000000007;

	public LittleElephantsAndTShirts() {
		mFScanner = new FasterScanner();
		mOut = new PrintWriter(System.out);
		mMem = new long[101][1024];
		mMap = new int[101];
		mArray = new ArrayList[10];
		mTShirtWearingPerson = new ArrayList[100];
		for (int i = 0; i < 10; i++)
			mArray[i] = new ArrayList<>();

		for (int i = 0; i < 100; i++)
			mTShirtWearingPerson[i] = new ArrayList<Integer>();
	}

	public void solve() {
		int T;
		int token;
		int rank;
		long ans;
		String line;
		StringTokenizer st;
		TreeSet<Integer> treeSet;
		Iterator<Integer> iterator;

		treeSet = new TreeSet<>();

		T = mFScanner.nextInt();

		while (T-- > 0) {
			N = mFScanner.nextInt();
			treeSet.clear();
			for (int i = 0; i < N; i++) {
				mArray[i].clear();
				line = mFScanner.nextLine();
				st = new StringTokenizer(line);
				while (st.hasMoreTokens()) {
					token = Integer.parseInt(st.nextToken());
					mArray[i].add(token);
					treeSet.add(token);
				}
			}

			mTShirts = treeSet.size();
			iterator = treeSet.iterator();
			rank = 0;

			while (iterator.hasNext()) {
				token = iterator.next();
				mMap[token] = rank++;
			}
			
			for (int i = 0; i < mTShirts; i++) {
				mTShirtWearingPerson[i].clear();
			}

			for (int i = 0; i < N; i++) {
				for (int j = 0; j < mArray[i].size(); j++) {
					token = mArray[i].get(j);
					token = mMap[token];
					mArray[i].set(j, token);
					mTShirtWearingPerson[token].add(i);
				}
			}

			for (int i = 0; i <= mTShirts; i++) {
				for (int j = 0; j < (1 << N); j++) {
					mMem[i][j] = -1;
				}
			}

			ans = rec(mTShirts, 0);
			mOut.println(ans);

		}

	}

	public long rec(int n, int bitMask) {

		long res;
		int mask;

		if (bitMask == ((1 << N) - 1))
			return 1;
		
		if (n == 0)
			return 0;
		
		if (mMem[n][bitMask] != -1)
			return mMem[n][bitMask];

		
		res = rec(n - 1, bitMask);

		 for (int person : mTShirtWearingPerson[n - 1]) {
			 mask = 1 << person;
			 if ((mask & bitMask) == mask)
				 continue;
			 mask = mask | bitMask;
			 res += rec(n - 1, mask);
			 res %= MOD;
		 }

		return (mMem[n][bitMask] = res % MOD);
	}

	public void flush() {
		mOut.flush();
	}

	public void close() {
		mOut.close();
	}

	public static void main(String[] args) {
		LittleElephantsAndTShirts mSol = new LittleElephantsAndTShirts();
		mSol.solve();
		mSol.flush();
		mSol.close();
	}

}
