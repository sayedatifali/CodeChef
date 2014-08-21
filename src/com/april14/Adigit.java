package com.april14;

import java.io.PrintWriter;

/**
 * http://www.codechef.com/APRIL14/problems/ADIGIT
 * 
 * @author doom
 * 
 */

public class Adigit {

	public static int digits[][] = new int[10][100001];
	public static int[] a = new int[100001];

	public static void main(String[] args) {
		int i, n, m, j;
		int index;
		int val, total;
		String values;
		FastScannerSlow fS = new FastScannerSlow();
		PrintWriter out = new PrintWriter(System.out);

		n = fS.nextInt();
		m = fS.nextInt();

		values = fS.next();

		for (i = 0; i < n; i++) {
			a[i] = values.charAt(i) - '0';
		}

		for (j = 0; j < 10; j++)
			digits[j][0] = 0;

		digits[a[0]][0] = 1;

		for (i = 1; i < n; i++) {
			// sum[i] = sum[i - 1] + a[i];
			for (j = 0; j < 10; j++) {
				digits[j][i] = digits[j][i - 1];
			}
			digits[a[i]][i] = digits[a[i]][i - 1] + 1;
		}

		for (i = 0; i < m; i++) {
			index = fS.nextInt();
			index--;
			val = a[index];

			index--;

			total = 0;

			if (index >= 0) {
				for (j = 0; j < 10; j++) {
					total += Math.abs((val - j) * digits[j][index]);
				}
			}

			out.println(total);

		}

		out.flush();
		out.close();

	}
}
