package com.practice;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;

public class ChaOrNot {
	public static void main(String[] args) {
		int testCount;
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		FastScanner in = new FastScanner(inputStream, outputStream);
		PrintWriter out = new PrintWriter(outputStream);
		CharOrNot solver = new CharOrNot();
		try {
			testCount = in.nextInt();
			System.out.println(testCount);
			// for (int i = 0; i < testCount; i++)
			// System.out.print(in.nextInt() + ",");
			solver.solve(testCount, in, out);
		} catch (Exception e) {
			// TODO: Write catch block
		}

		out.close();
	}
}

class CharOrNot {

	public void solve(int testCount, FastScanner in, PrintWriter out)
			throws IOException {
		int max = 2;
		int nomax = 2;
		int i;
		int j;
		int k;
		int x = 0;
		int y = 0;
		int array[] = new int[testCount];
		int ap[][] = new int[testCount][testCount];
		int noap[][] = new int[testCount][testCount];
		for (i = 0; i < testCount; i++) {
			array[i] = in.nextInt();
			for (j = 0; j < testCount; j++) {
				ap[i][j] = 2;
				noap[i][j] = 2;
			}
		}
		Arrays.sort(array);

		int n = 1;

		for (j = testCount - 2; j >= 1; j--) {
			i = j - 1;
			k = j + 1;
			while (i >= 0 && k < testCount) {
				if (array[i] + array[k] < 2 * array[j]) {
					if (noap[i][j] < noap[j][k] + 1)
						noap[i][j] = noap[j][k] + 1;
					// nomax = Math.max(nomax, noap[i][j]);
					if (nomax < noap[i][j]) {
						x = i;
						y = j;
						nomax = noap[i][j];
					}
					out.println("noap[" + i + "][" + j + "] = " + noap[i][j]);
					k++;
				} else if (array[i] + array[k] > 2 * array[j]) {
					if (noap[i][j] < noap[j][k] + 1)
						noap[i][j] = noap[j][k] + 1;
					// nomax = Math.max(nomax, noap[i][j]);
					if (nomax < noap[i][j]) {
						x = i;
						y = j;
						nomax = noap[i][j];
					}
					out.println("noap[" + i + "][" + j + "] = " + noap[i][j]);
					i--;
				} else {
					ap[i][j] = ap[j][k] + 1;
					max = Math.max(max, ap[i][j]);
					// out.println("ap[" + i + "][" + j + "] = " + ap[i][j]);
					i--;
					k++;
				}
				// out.println("noap[" + i + "][" + j + "] = " + noap[i][j]);
			}
		}
		out.println(nomax);
		
		while(nomax-- > 0) {
			out.print(array[x] + " ");
			x = y;
			int m = nomax;
			if (nomax < 2)
				m = 2;
			i = 0;
			while(noap[x][i] != m) {
				i++;
			}
			y = i;
		}

	}

}