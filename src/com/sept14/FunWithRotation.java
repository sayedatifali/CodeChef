package com.sept14;

import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * http://www.codechef.com/SEPT14/problems/ROTATION
 * 
 * @author sultan.of.swing
 *
 */

public class FunWithRotation {
	public FasterScanner mFScanner;
	public PrintWriter mOut;
	public int mArray[];
	public int N;
	public int M;
	public static final String CLOCKWISE = "C";
	public static final String ANTICLOCKWISE = "A";
	public static final String READ = "R";

	public FunWithRotation() {
		mFScanner = new FasterScanner();
		mOut = new PrintWriter(System.out);
	}

	public void solve() {
		int i;
		int index;
		int beginIndex;
		int shift;
		String str;
		String op;
		StringTokenizer st;

		N = mFScanner.nextInt();
		M = mFScanner.nextInt();

		mArray = new int[N];

		for (i = 0; i < N; i++)
			mArray[i] = mFScanner.nextInt();

		beginIndex = 0;
		
		for (i = 0; i < M; i++) {

			str = mFScanner.nextLine();
			st = new StringTokenizer(str);

			op = st.nextToken();

			if (op.equalsIgnoreCase(CLOCKWISE)) {
				shift = Integer.parseInt(st.nextToken());
				beginIndex = (beginIndex + shift) % N;
			} else if (op.equalsIgnoreCase(ANTICLOCKWISE)) {
				shift = Integer.parseInt(st.nextToken());
				beginIndex = (beginIndex - shift + N) % N;
			} else {
				index = Integer.parseInt(st.nextToken()) - 1;
				index = (beginIndex + index) % N;
				mOut.println(mArray[index]);
			}
		}

	}

	public void flush() {
		mOut.flush();
	}

	public void close() {
		mOut.close();
	}

	public static void main(String[] args) {
		FunWithRotation mSol = new FunWithRotation();
		mSol.solve();
		mSol.flush();
		mSol.close();
	}
}
