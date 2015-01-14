package com.oct14;

import java.io.PrintWriter;
import java.util.Arrays;

/**
 * http://www.codechef.com/OCT14/problems/PRPOTION
 * 
 * @author sultan.of.swing
 * 
 */

public class MagicalGirlAndColoredLiquidPotion {

	public FasterScanner mFScanner;
	public PrintWriter mOut;

	public MagicalGirlAndColoredLiquidPotion() {
		mFScanner = new FasterScanner();
		mOut = new PrintWriter(System.out);
	}

	public void solve() {
		int T;
		int rgb[];
		int nr, ng, nb;
		int m;

		T = mFScanner.nextInt();
		rgb = new int[3];

		while (T-- > 0) {
			nr = mFScanner.nextInt();
			ng = mFScanner.nextInt();
			nb = mFScanner.nextInt();
			m = mFScanner.nextInt();

			rgb[0] = rgb[1] = rgb[2] = Integer.MIN_VALUE;

			for (int i = 0; i < nr; i++)
				rgb[0] = Math.max(rgb[0], mFScanner.nextInt());
			for (int i = 0; i < ng; i++)
				rgb[1] = Math.max(rgb[1], mFScanner.nextInt());
			for (int i = 0; i < nb; i++)
				rgb[2] = Math.max(rgb[2], mFScanner.nextInt());

			for (int i = 0; i < m; i++) {
				Arrays.sort(rgb);
				rgb[2] /= 2;
			}

			Arrays.sort(rgb);

			mOut.println(rgb[2]);
		}
	}

	public void flush() {
		mOut.flush();
	}

	public void close() {
		mOut.close();
	}

	public static void main(String[] args) {
		MagicalGirlAndColoredLiquidPotion mSol = new MagicalGirlAndColoredLiquidPotion();
		mSol.solve();
		mSol.flush();
		mSol.close();
	}

}