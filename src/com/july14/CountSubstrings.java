package com.july14;

import java.io.PrintWriter;

import com.io.FasterScanner;

public class CountSubstrings {

	public FasterScanner mFScanner;
	public PrintWriter mOut;

	public CountSubstrings() {
		mFScanner = new FasterScanner();
		mOut = new PrintWriter(System.out);
	}

	public void solve() {
		int T;
		int N;
		int k;
		String str;

		T = mFScanner.nextInt();

		while (T-- > 0) {
			N = mFScanner.nextInt();
			str = mFScanner.nextLine();

			k = 0;
			for (int i = 0; i < N; i++) {
				if (str.charAt(i) == '1')
					k++;
			}

			k = k * (k + 1);
			k /= 2;

			mOut.println(k);
		}

		close();

	}

	public void close() {
		mOut.flush();
		mOut.close();
	}

	public static void main(String[] args) {
		CountSubstrings mSol = new CountSubstrings();
		mSol.solve();
	}

}
