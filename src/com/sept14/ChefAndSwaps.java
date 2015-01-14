package com.sept14;

import java.io.PrintWriter;
import java.util.Arrays;

/**
 * http://www.codechef.com/SEPT14/problems/CHEFINV
 * 
 * @author sultan.of.swing
 *
 */

public class ChefAndSwaps {

	public FasterScanner mFScanner;
	public PrintWriter mOut;
	public int mArray[];
	public int mArrayCopy[];
	public long mBit[];
	public long mBitDiff[];
	public int N;
	public int Q;
	public int mSqrt;
	public Query mQueries[];
	public long mAns[];

	public ChefAndSwaps() {
		mFScanner = new FasterScanner();
		mOut = new PrintWriter(System.out);
	}

	public void solve() {
		int i;
		int l, r;
		int index;
		int num;
		int numPos;
		long origInv;
		long inv;
		long ll, lg, rl, rg;
		Query query;

		N = mFScanner.nextInt();
		Q = mFScanner.nextInt();
		mSqrt = (int) Math.sqrt(N);

		mArray = new int[N];
		mArrayCopy = new int[N];
		mBit = new long[N + 10];
		mBitDiff = new long[N + 10];
		mQueries = new Query[Q];
		mAns = new long[Q];

		for (i = 0; i < N; i++) {
			mArray[i] = mFScanner.nextInt();
			mArrayCopy[i] = mArray[i];
		}

		for (i = 0; i < Q; i++) {
			l = mFScanner.nextInt() - 1;
			r = mFScanner.nextInt() - 1;
			mQueries[i] = new Query(i, l, r);
		}

		Arrays.sort(mArrayCopy);

		for (i = 0; i < N; i++) {
			num = mArray[i];
			numPos = lowerBound(0, N, num);
			mArray[i] = numPos + 1;
		}

		origInv = 0;
		for (i = N - 1; i >= 0; i--) {
			num = mArray[i];
			origInv += queryBit(num - 1, mBit);
			updateBit(num, 1, mBit);
		}

		Arrays.sort(mQueries);

		r = mQueries[Q - 1].right;
		index = r - 1;

		for (i = Q - 1; i >= 0; i--) {

			query = mQueries[i];

			if (mQueries[i].left == mQueries[i].right
					|| mArray[query.left] == mArray[query.right]) {
				mAns[query.index] = origInv;
				continue;
			} else if (query.right - query.left == 1) {
				if (mArray[query.right] == mArray[query.left]) {
					inv = origInv;
				} else if (mArray[query.right] > mArray[query.left]) {
					inv = origInv + 1;
				} else {
					inv = origInv - 1;
				}
				mAns[query.index] = inv;
				continue;
			}

			while (r < query.right) {
				updateBit(mArray[r], 1, mBitDiff);
				r++;
			}

			while (r > query.right) {
				r--;
				updateBit(mArray[r], -1, mBitDiff);
			}

			while (index > query.left) {
				updateBit(mArray[index], 1, mBitDiff);
				index--;
			}

			while (index < query.left) {
				index++;
				updateBit(mArray[index], -1, mBitDiff);
			}

			inv = origInv;
			lg = queryBit(mArray[query.left] - 1, mBitDiff);
			rg = queryBit(mArray[query.right] - 1, mBitDiff);
			num = query.right - query.left - 1;

			ll = num - queryBit(mArray[query.left], mBitDiff);
			rl = num - queryBit(mArray[query.right], mBitDiff);

			inv += (ll - lg + rg - rl);

			if (mArray[query.right] > mArray[query.left])
				inv += 1;
			else if (mArray[query.right] < mArray[query.left])
				inv -= 1;

			mAns[query.index] = inv;

		}

		for (i = 0; i < Q; i++)
			mOut.println(mAns[i]);

		return;

	}

	public void updateBit(int idx, long val, long[] bit) {
		while (idx <= N) {
			bit[idx] += val;
			idx += (idx & -idx);
		}
	}

	public long queryBit(int idx, long[] bit) {
		long sum = 0;
		while (idx > 0) {
			sum += bit[idx];
			idx -= (idx & -idx);
		}
		return sum;
	}

	public long querySingle(int idx, long[] bit) {
		return queryBit(idx, bit) - queryBit(idx - 1, bit);
	}

	public int lowerBound(int first, int last, int val) {

		int index;
		int count, step;

		count = last - first;
		first = 0;

		while (count > 0) {
			index = first;
			step = count / 2;
			index += step;
			if (mArrayCopy[index] < val) {
				first = ++index;
				count -= step + 1;
			} else
				count = step;
		}

		return first;

	}

	class Query implements Comparable<Query> {
		int index;
		int left;
		int right;
		long ans;

		public Query(int index, int left, int right) {
			this.index = index;
			this.left = left;
			this.right = right;
			this.ans = 0;
		}

		@Override
		public int compareTo(Query o) {
			// TODO Auto-generated method stub
			if (this.right / mSqrt < o.right / mSqrt)
				return -1;
			if (this.right / mSqrt > o.right / mSqrt)
				return 1;
			return Integer.compare(o.left, this.left);
		}

	}

	public void flush() {
		mOut.flush();
	}

	public void close() {
		mOut.close();
	}

	public static void main(String[] args) {
		ChefAndSwaps mSol = new ChefAndSwaps();
		mSol.solve();
		mSol.flush();
		mSol.close();
	}

}
