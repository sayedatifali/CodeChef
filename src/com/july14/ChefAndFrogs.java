package com.july14;

import java.io.PrintWriter;
import java.util.Arrays;

import com.io.FasterScanner;

/**
 * http://www.codechef.com/JULY14/problems/FROGV
 * 
 * @author sultan.of.swing
 *
 */

public class ChefAndFrogs {

	public FasterScanner mFScanner;
	public PrintWriter mOut;
	public Frog mFrog[];
	public int mGroups[];

	public ChefAndFrogs() {
		mFScanner = new FasterScanner();
		mOut = new PrintWriter(System.out);
	}

	public void solve() {
		int i, j;
		int frog1, frog2;
		int N, K, P;
		int last;
		int group;

		N = mFScanner.nextInt();
		K = mFScanner.nextInt();
		P = mFScanner.nextInt();

		mFrog = new Frog[N];
		mGroups = new int[N + 1];

		for (i = 0; i < N; i++) {
			mFrog[i] = new Frog(i + 1, mFScanner.nextInt());
		}

		Arrays.sort(mFrog);

		group = 0;

		for (i = 0; i < N;) {
			last = mFrog[i].frogPos;
			mFrog[i].group = group;
			j = i + 1;
			mGroups[mFrog[i].frog] = group;

			while (j < N && mFrog[j].frogPos - last <= K) {
				mFrog[j].group = group;
				last = mFrog[j].frogPos;
				mGroups[mFrog[j].frog] = group;
				j++;
			}

			i = j;
			group++;
		}

		for (i = 0; i < P; i++) {
			frog1 = mFScanner.nextInt();
			frog2 = mFScanner.nextInt();

			if (mGroups[frog1] == mGroups[frog2])
				mOut.println("Yes");
			else
				mOut.println("No");
		}
		
		close();

	}

	public void close() {
		mOut.flush();
		mOut.close();
	}

	class Frog implements Comparable<Frog> {
		int frog;
		int frogPos;
		int group;

		public Frog(int pos, int frogPos) {
			this.frog = pos;
			this.frogPos = frogPos;
			group = -1;
		}

		@Override
		public int compareTo(Frog arg0) {
			// TODO Auto-generated method stub
			if (this.frogPos < arg0.frogPos)
				return -1;
			else if (this.frogPos > arg0.frogPos)
				return 1;
			return 0;
		}

	}

	public static void main(String[] args) {
		ChefAndFrogs mSol = new ChefAndFrogs();
		mSol.solve();
	}

}
