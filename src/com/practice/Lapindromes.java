package com.practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Lapindromes {
	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		FastScannerMinimal in = new FastScannerMinimal(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Lapindrome solver = new Lapindrome();
		int testCount = Integer.parseInt(in.next());
		for (int i = 1; i <= testCount; i++)
			solver.solve(i, in, out);
		out.close();
	}
}

class Lapindrome {

	public static final String sYes = "YES";
	public static final String sNo = "NO";
	public static int i = 0;

	public void solve(int testNumber, FastScannerMinimal in, PrintWriter out) {
		String input;
		int len;
		int upIndex1;
		int lowIndex2;
		int upIndex2;
		char[] inputArray;

		input = in.next();
		inputArray = input.toCharArray();
		len = input.length();
		if (len % 2 != 0) {
			upIndex1 = (len - 3) / 2;
			lowIndex2 = (len + 1) / 2;
			upIndex2 = len - 1;
		} else {
			upIndex1 = len / 2 - 1;
			lowIndex2 = upIndex1 + 1;
			upIndex2 = len - 1;
		}

		char leftArray[] = new char[upIndex1 + 1];
		char rightArray[] = new char[upIndex2 - lowIndex2 + 1];

		for (i = 0; i <= upIndex1; i++) {
			leftArray[i] = inputArray[i];
		}

		for (i = lowIndex2; i <= upIndex2; i++) {
			rightArray[i - lowIndex2] = inputArray[i];
		}

		Arrays.sort(leftArray);
		Arrays.sort(rightArray);

		i = 0;
		while (i <= upIndex1 && leftArray[i] == rightArray[i]) {
			i++;
		}
		if (i == upIndex1 + 1)
			out.println(sYes);
		else
			out.println(sNo);

	}

}

class FastScannerMinimal {
	private BufferedReader br;
	private StringTokenizer st;

	public FastScannerMinimal(InputStream in) {
		this.br = new BufferedReader(new InputStreamReader(in));
	}

	public boolean hasNext() {
		try {
			while (st == null || !st.hasMoreTokens()) {
				final String nextLine = br.readLine();
				if (nextLine == null) {
					return false;
				} else {
					st = new StringTokenizer(nextLine);
				}
			}
		} catch (IOException e) {
			e.printStackTrace(); // To change body of catch statement use File |
									// Settings | File Templates.
		}
		return true;
	}

	public String next() {
		return hasNext() ? st.nextToken() : null;
	}

	public int nextInt() {
		return Integer.parseInt(next());
	}

}
