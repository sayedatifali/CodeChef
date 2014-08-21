package com.june14;

import java.io.PrintWriter;

import com.io.FastScannerSlow;

/**
 * http://www.codechef.com/JUNE14/problems/LEBLOCKS
 * 
 * @author doom
 * 
 */

public class LittleElephantAndBlocks {

	public int T;
	public FastScannerSlow mFScanner;
	public PrintWriter mOut;
	public int N, K;
	public Data[] mData;

	public LittleElephantAndBlocks() {
		mFScanner = new FastScannerSlow();
		mOut = new PrintWriter(System.out);
	}

	public long solveTestCase() {
		int i, j;
		int size = 0;
		int k;
		int index[];
		long count = 0;

		for (i = 0; i < N; i++)
			size += mData[i].A;

		index = new int[size];

		do {
			j = 0;
			k = mData[0].A;
			for (i = 0; i < size; i++) {
				if (i == k) {
					j++;
					k += mData[j].A;
				}
				index[i] = j;
			}

			i = 0;
			j = i + K;

			while (j < size) {

				if (mData[index[i]].C == mData[index[j]].C)
					count++;

				i++;
				j++;
			}

		} while (nextPerm(mData));

		mOut.println("Count: " + count);

		return count;
	}

	public void solve() {
		int A, C;
		double ans;
		long fact;

		T = mFScanner.nextInt();

		while (T-- > 0) {
			N = mFScanner.nextInt();
			K = mFScanner.nextInt();

			mData = new Data[N];

			for (int i = 0; i < N; i++) {
				A = mFScanner.nextInt();
				C = mFScanner.nextInt();

				mData[i] = new Data(i, A, C);

			}

			ans = solveTestCase();
			fact = factorial(N);
			ans /= fact;
			mOut.println("Factorial " + N + ": " + fact);
			mOut.println(ans);

		}

		close();
	}

	public long factorial(int n) {
		if (n == 0)
			return 1L;
		return n * factorial(n - 1);
	}

	public boolean nextPerm(Data[] a) {

		int i;
		int j;
		int N;
		Data temp;

		N = a.length;
		i = N - 2;

		for (; i >= 0; i--) {
			if (a[i].rank < a[i + 1].rank)
				break;
		}
		if (i < 0)
			return false;

		for (j = N - 1; j >= i; j--) {
			if (a[j].rank > a[i].rank) {
				temp = a[j];
				a[j] = a[i];
				a[i] = temp;
				break;
			}
		}

		for (j = i + 1; j < (N + i + 1) / 2; j++) {
			temp = a[N - j + i];
			a[N - j + i] = a[j];
			a[j] = temp;
		}

		return true;
	}

	public void close() {
		mOut.flush();
		mOut.close();
	}

	public static void main(String[] args) {

		LittleElephantAndBlocks mSol = new LittleElephantAndBlocks();

		mSol.solve();

	}

	class Data {
		int rank;
		int A;
		int C;

		public Data(int rank, int A, int C) {
			this.rank = rank;
			this.A = A;
			this.C = C;
		}
	}

}
