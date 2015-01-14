package com.aug14;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.TreeSet;

/**
 * http://www.codechef.com/AUG14/problems/CLETAB
 * 
 * @author sultan.of.swing
 * 
 */

public class CleaningTables {

	public FasterScanner mFScanner;
	public PrintWriter mOut;
	public TreeSet<Customer> mTreeSet;
	public int mTables;
	public int mCustomers;
	public int mFreq[];
	public boolean mPresent[];
	public int mSeq[];
	public int mNextIndex[];

	public CleaningTables() {
		mFScanner = new FasterScanner();
		mOut = new PrintWriter(System.out);
		mFreq = new int[400];
		mSeq = new int[400];
		mPresent = new boolean[400];
		mNextIndex = new int[400];
		mTreeSet = new TreeSet<Customer>();
	}

	public void solve() {
		int T;
		int i;
		int cust;
		int ans;
		int lastIndex[];

		T = mFScanner.nextInt();
		lastIndex = new int[400];

		while (T-- > 0) {
			mTables = mFScanner.nextInt();
			mCustomers = mFScanner.nextInt();
			Arrays.fill(mFreq, 0);
			Arrays.fill(mPresent, false);
			Arrays.fill(lastIndex, -1);
			ans = 0;
			mTreeSet.clear();

			for (i = 0; i < mCustomers; i++) {
				cust = mFScanner.nextInt() - 1;
				mSeq[i] = cust;
			}

			for (i = mCustomers - 1; i >= 0; i--) {
				cust = mSeq[i];
				if (lastIndex[cust] == -1) {
					mNextIndex[i] = Integer.MAX_VALUE;
				} else {
					mNextIndex[i] = lastIndex[cust];
				}
				lastIndex[cust] = i;
			}
			
			ans = solveTestCase();
			mOut.println(ans);
		}

	}

	public int solveTestCase() {
		int ans;
		int cust;
		int next;
		Customer node;
		Customer curNode;

		ans = 0;

		for (int i = 0; i < mCustomers; i++) {
			cust = mSeq[i];
			next = mNextIndex[i];
			node = new Customer(cust, next);
			curNode = null;
			if (mTreeSet.size() > 0)
				curNode = mTreeSet.first();

			if (curNode != null) {
				if (node.customer == curNode.customer) {
					mTreeSet.pollFirst();
					mTreeSet.add(node);
				} else if (mTreeSet.size() < mTables) {
					mTreeSet.add(node);
					ans++;
				} else {
					mTreeSet.pollLast();
					mTreeSet.add(node);
					ans++;
				}
			} else {
				ans++;
				mTreeSet.add(node);
			}
		}

		return ans;
	}

	public void flush() {
		mOut.flush();
	}

	public void close() {
		mOut.close();
	}

	class Customer implements Comparable<Customer> {
		public int customer;
		public int nextIndex;

		public Customer(int customer, int nextIndex) {
			this.customer = customer;
			this.nextIndex = nextIndex;
		}

		@Override
		public int compareTo(Customer o) {
			// TODO Auto-generated method stub
			if (this.nextIndex < o.nextIndex)
				return -1;
			else if (this.nextIndex > o.nextIndex)
				return 1;
			return 0;
		}
	}

	public static void main(String[] args) {
		CleaningTables mSol = new CleaningTables();
		mSol.solve();
		mSol.flush();
		mSol.close();
	}

}