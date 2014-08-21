package com.april14;

import java.io.PrintWriter;

/**
 * http://www.codechef.com/APRIL14/problems/BINTREE
 * 
 * @author doom
 * 
 */

public class BinTree {

	public static void main(String[] args) {
		int i, j;
		int max;
		int min;
		int pi, pj;
		int T;
		int ans;
		FastScannerSlow fS = new FastScannerSlow();
		PrintWriter out = new PrintWriter(System.out);

		T = fS.nextInt();

		while (T-- > 0) {
			i = fS.nextInt();
			j = fS.nextInt();
			ans = 0;
			max = Math.max(i, j);
			min = Math.min(i, j);

			i = max;
			j = min;

			pi = (int) (Math.log(i) / Math.log(2));
			pj = (int) (Math.log(j) / Math.log(2));

			while (pi != pj) {
				pi--;
				i /= 2;
				ans++;
			}

			while (i != j) {
				i /= 2;
				j /= 2;
				ans += 2;
			}

			out.println(ans);

		}

		out.flush();

	}

}
