package com.march14;

import java.io.PrintWriter;

/**
 * http://www.spoj.com/problems/ORDERS/
 * 
 * AC in SPOJ! :D
 * 
 * @author sultan.of.swing
 *
 */

public class Orders {

	public FasterScanner mFScanner;
	public PrintWriter mOut;
	public int mBIT[];
//	public boolean mSegment[];
	public int mSegLeaves[];
	public int mAns[];
	public int mArray[];
	public int mMax;
	public int mN;
	public static final int sMAX = 200009;
	public static final int sMAXSeg = sMAX * 18;
	
	public Orders() {
		mFScanner = new FasterScanner();
		mOut = new PrintWriter(System.out);
		mBIT = new int[sMAX];
		mArray = new int[sMAX];
//		mSegment = new boolean[sMAXSeg];
		mSegLeaves = new int[sMAXSeg];
		mAns = new int[sMAX];
	}
	
	public void clearBIT(int N) {
		for (int i = 0; i <= N; i++) {
			mBIT[i] = 0;
		}
	}
	
	public void update(int idx, int val) {
		while (idx < mMax) {
			mBIT[idx] += val;
			idx += (idx & -idx);
		}
	}
	
	public int query(int idx) {
		int sum = 0;
		
		while (idx > 0) {
			sum += mBIT[idx];
			idx -= (idx & -idx);
		}
		
		return sum;
	}
	
	public void buildSegTree(int node, int begin, int end) {
		int mid;
		
		if (begin > end)
			return;
		
		if (begin == end) {
//			mSegment[node] = true;
			mSegLeaves[node] = 1;
			return;
		}
		
		mid = begin + ((end - begin) >> 1);
		
		buildSegTree(2 * node, begin, mid);
		buildSegTree(2 * node + 1, mid + 1, end);

		mSegLeaves[node] = mSegLeaves[2 * node] + mSegLeaves[2 * node + 1];
		
	}
	
	public int query(int node, int begin, int end, int rank) {
		int mid;
		int left, right;
		
		left = right = -1;
		
		if (begin == end)
			return begin;
		
		mid = begin + ((end - begin) >> 1);
		
		if (rank <= mSegLeaves[2 * node]) {
			left = query(2 * node, begin, mid, rank);
		} else {
			rank -= mSegLeaves[2 * node];
			right = query(2 * node + 1, mid + 1, end, rank);
		}
		
		if (right == -1)
			return left;
		
		return right;
		
	}
	
	public void update(int node, int begin, int end, int index) {
		int mid;
		
		if (index < begin || index > end)
			return;
		
		if (begin == end) {
//			mSegment[node] = false;
			mSegLeaves[node] = 0;
			return;
		}
		
		mid = begin + ((end - begin) >> 1);
		
		update(2 * node, begin, mid, index);
		update(2 * node + 1, mid + 1, end, index);
		
//		mSegment[node] = mSegment[2 * node] | mSegment[2 * node + 1];
		mSegLeaves[node] = mSegLeaves[2 * node] + mSegLeaves[2 * node + 1];
	}
	
	public void solveTestCase() {
		int i;
		int idx;
		int shift;
		
		clearBIT(mMax);
//		resetArray(mMax);
		
		for (i = 0; i < mN; i++) {
			idx = i + 1;
			shift = mArray[i];
			update(idx, -1 - shift);
			update(idx + 1, shift);
//			update(idx, -1);
			idx -= shift;
			update(idx, 1);
		}
		
		for (i = 0; i < mN; i++) {
			mOut.print(i + 1 + query(i + 1));
			mOut.print(" ");
		}
		mOut.println();
	}
	
	/**
	public void printSolution() {
		int i;
		
		for (i = 0; i < mN; i++) {
			mOut.print(mPos[i]);
			mOut.print(" ");
		}
		mOut.println();
	}
	**/
	
	public void solveTestCaseSeg() {
		int i;
		int id;
		int rank;
		
		buildSegTree(1, 0, mN - 1);
		
		for (i = mN - 1; i >= 0; i--) {
			rank = i + 1 - mArray[i];
			id = query(1, 0, mN - 1, rank);
			mAns[i] = id + 1;
			update(1, 0, mN - 1, id);
		}
		
		for (i = 0; i < mN; i++) {
			mOut.print(mAns[i]);
			mOut.print(" ");
		}
		mOut.println();
	}
	
	public void solve() {
		int T;
		int N;
		
		T = mFScanner.nextInt();
		
		while(T-- > 0) {
			N = mFScanner.nextInt();
			
			for (int i = 0; i < N; i++) {
				mArray[i] = mFScanner.nextInt();
			}
			
			mN = N;
			mMax = N + 2;
			solveTestCaseSeg();
//			solveTestCase();
//			printSolution();
		}
		
		close();
	}
	
	public void close() {
		mOut.flush();
		mOut.close();
	}
	
	public static void main(String [] args) {
		Orders mSol = new Orders();
		mSol.solve();
	}
	
}
