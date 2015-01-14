package com.aug14;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * http://www.codechef.com/AUG14/problems/SEASHUF
 * 
 * @author sultan.of.swing
 *
 */

public class SerejaAndShuffling {

	public int N;
	public int mLastIndex;
	public int mMidIndex;
	public int mHalf;
	public boolean mMarked[];
	public long mArray[];
	public long mMinDiff;
	// public static final long MAX_OP = 22000000; // 10 ^ 6
	public static final long MAX_OP = 25000000;
	public ArrayList<Sequence> mSeq;
	ArrayList<Exchange> minSeq;
	public FasterScanner mFScanner;
	public PrintWriter mOut;

	public SerejaAndShuffling() {
		mFScanner = new FasterScanner();
		mOut = new PrintWriter(System.out);
		mMinDiff = Integer.MAX_VALUE;
		mSeq = new ArrayList<>();
	}

	public void solve() {
		int i;

		N = mFScanner.nextInt();
		mLastIndex = N - 1;
		mMidIndex = N / 2 - 1;
		mHalf = N / 2;

		mMarked = new boolean[N];
		mArray = new long[N];

		for (i = 0; i < N; i++) {
			mArray[i] = mFScanner.nextInt();
		}

		findMin();
		buildRotationSequence();

		mOut.println(mSeq.size());
		for (i = 0; i < mSeq.size(); i++) {
			mOut.print(mSeq.get(i).begin + 1);
			mOut.print(" ");
			mOut.println(mSeq.get(i).end + 1);
		}

	}

	public void findMin() {
		int i;
		long op = 0;
		long curDiff;
		long diff;
		long sumFirst = 0;
		long sumLast = 0;
		long curSumLast;
		long curSumFirst;
		int indexFirst;
		int indexLast;
		long moves = 0;
		long oldDiff;
		long oldMove;
		long updatedDiff;
		long tempFirst, tempLast;
		ArrayList<Exchange> curSeq;
		ArrayList<Exchange> tempSeq;
		Random random;

		for (i = 0; i <= mMidIndex; i++)
			sumFirst += mArray[i];

		for (; i < N; i++)
			sumLast += mArray[i];

		mMinDiff = Math.abs(sumLast - sumFirst);

		minSeq = new ArrayList<>();
		curSeq = new ArrayList<>();

		while (op < MAX_OP) {

			diff = Math.abs(sumLast - sumFirst);
			updatedDiff = diff;
			curDiff = diff;
			curSumFirst = sumFirst;
			curSumLast = sumLast;
			curSeq.clear();
			moves = 0;
			long last_op = op;

			random = new Random();

			while (true) {
				op++;
				if (op - last_op > 800)
					break;

				indexFirst = random.nextInt(mHalf);
				indexLast = random.nextInt(mHalf) + mHalf;

				if (mMarked[indexFirst] || mMarked[indexLast])
					continue;

				tempFirst = curSumFirst - mArray[indexFirst]
						+ mArray[indexLast];
				tempLast = curSumLast - mArray[indexLast] + mArray[indexFirst];

				oldDiff = curDiff;
				curDiff = Math.abs(tempLast - tempFirst);

				if (curDiff >= updatedDiff) {
					curDiff = oldDiff;
					continue;
				}
				
				updatedDiff = curDiff;

				oldMove = moves;
				if (indexFirst == mMidIndex)
					moves = moves + 2 * (indexLast - mHalf) + 2;
				else
					moves = moves + (mHalf - indexFirst) + 2
							* (indexLast - mHalf) + 2;

				if (moves > 2 * N) {
					curDiff = oldDiff;
					moves = oldMove;
//					break;
					continue;
				}

				curSumFirst = tempFirst;
				curSumLast = tempLast;

				mMarked[indexFirst] = true;
				mMarked[indexLast] = true;
				curSeq.add(new Exchange(indexFirst, indexLast));

			}

			for (Exchange e : curSeq) {
				mMarked[e.left] = false;
				mMarked[e.right] = false;
			}

			if (curDiff < mMinDiff) {
//				minSeq.clear();
				mMinDiff = curDiff;

				tempSeq = minSeq;
				minSeq = curSeq;
				curSeq = tempSeq;

				// for (i = 0; i < curSeq.size(); i++) {
				// op++;
				// minSeq.add(new Exchange(curSeq.get(i).left,
				// curSeq.get(i).right));
				// }
			}

		}

	}

	public void buildRotationSequence() {
		int i;
		int j;
		int leftNum;
		int rightNum;
		ArrayList<Integer> leftIndex;
		ArrayList<Integer> rightIndex;
		Sequence seq;

		leftIndex = new ArrayList<>();
		rightIndex = new ArrayList<>();

		for (Exchange e : minSeq) {
			mMarked[e.left] = true;
			mMarked[e.right] = true;
			leftIndex.add(e.left);
			rightIndex.add(e.right);
		}

		Collections.sort(leftIndex);
		Collections.sort(rightIndex);

		for (j = 0; j < rightIndex.size(); j++) {
			rightNum = rightIndex.get(j);

			while (rightNum > mHalf) {
				seq = new Sequence(rightNum - 1, rightNum);
				mSeq.add(seq);
				rightNum--;
			}

			i = leftIndex.size() - j - 1;

			leftNum = leftIndex.get(i);

			seq = new Sequence(leftNum, mHalf);
			mSeq.add(seq);
		}
	}

	public void flush() {
		mOut.flush();
	}

	public void close() {
		mOut.close();
	}

	class Sequence {
		public int begin;
		public int end;

		public Sequence(int begin, int end) {
			this.begin = begin;
			this.end = end;
		}
	}

	class Exchange {
		public int left;
		public int right;

		public Exchange(int left, int right) {
			this.left = left;
			this.right = right;
		}
	}

	public static void main(String[] args) {
		SerejaAndShuffling mSol = new SerejaAndShuffling();
		mSol.solve();
		mSol.flush();
		mSol.close();
	}

}
