package com.march14;

import java.io.PrintWriter;
import java.util.Arrays;

public class StoryWithStrings {

	private static Entry entry[];
	private static int P[][];
	// Indicates the suffix of a string starting from index sSuffix[i] is ranked
	// i in the lexicographic sorted way
	private static int sSuffix[];
	private static int sLcp[];
	private static int entryLen = 500010;
	private int L;
	private int K;

	/*
	 * static { entry = new Entry[entryLen]; P = new int[19][entryLen];
	 * 
	 * for (int i = 0; i < entryLen; i++) { entry[i] = new Entry(); } }
	 */

	public StoryWithStrings(String str) {
		int i;
		int level;
		int skip;
		L = str.length();

		initEntries(L);

		K = 32 - Integer.numberOfLeadingZeros(L - 1) + 1;

		P = new int[K][L];
		sSuffix = new int[L];
		sLcp = new int[L];

		for (i = 0; i < L; i++) {
			P[0][i] = str.charAt(i);
		}

		for (level = 1, skip = 1; skip < L; skip *= 2, level++) {
			for (i = 0; i < L; i++) {
				entry[i].nr[0] = P[level - 1][i];
				entry[i].nr[1] = (i + skip < L) ? P[level - 1][i + skip] : -1;
				entry[i].index = i;
			}

			Arrays.sort(entry);

			for (i = 0; i < L; i++) {
				P[level][entry[i].index] = ((i > 0)
						&& (entry[i].nr[0] == entry[i - 1].nr[0]) && (entry[i].nr[1] == entry[i - 1].nr[1])) ? P[level][entry[i - 1].index]
						: i;
			}

		}
		level--;

		for (i = 0; i < L; i++) {
			sSuffix[P[level][i]] = i;
		}

		// print2DIntArray(P);

		// System.out.println("K = " + K + "; level = " + level);

	}

	public void print2DIntArray(int[][] array) {
		int i, j;

		System.out.println("Printing 2D array:");

		for (i = 0; i < array.length; i++) {
			for (j = 0; j < array[i].length; j++) {
				System.out.print(array[i][j] + " ");
			}
			System.out.println();
		}

		System.out.println();
	}

	public MaximumLCP buildLCPArray(int len) {

		int i;
		int index = 0;
		MaximumLCP ans;
		int max = 0;

		for (i = 1; i < L; i++) {
			if ((sSuffix[i - 1] < len && sSuffix[i] >= len)
					|| (sSuffix[i - 1] >= len && sSuffix[i] < len)) {
				sLcp[i] = lcp(sSuffix[i], sSuffix[i - 1]);
				if (sLcp[i] > max) {
					max = sLcp[i];
					index = sSuffix[i] < sSuffix[i - 1] ? sSuffix[i]
							: sSuffix[i - 1];
				} else if (sLcp[i] == max
						&& (sSuffix[i] < index || sSuffix[i - 1] < index)) {
					index = sSuffix[i] < sSuffix[i - 1] ? sSuffix[i]
							: sSuffix[i - 1];
				}
			}
		}

		ans = new MaximumLCP(max, index);

		return ans;
	}

	public int lcp(int i, int j) {
		int len = 0;

		if (i == j)
			return L - i;

		for (int k = K - 1; k >= 0 && i < L && j < L; k--) {
			if (P[k][i] == P[k][j]) {
				i += (1 << k);
				j += (1 << k);
				len += (1 << k);
			}
		}

		return len;
	}

	public int[][] getSuffixArray() {
		return P;
	}

	public void initEntries(int N) {
		int i;
		entry = new Entry[N];
		for (i = 0; i < N; i++) {
			entry[i] = new Entry();
		}
	}

	public static void main(String[] args) {
		int i, j;
		int strALen, strBLen, len;
		String strA, strB;
		String str;
		FastScannerSlow fastScanner;
		PrintWriter out;
		StoryWithStrings StoryWithStrings;
		MaximumLCP ans;

		fastScanner = new FastScannerSlow();
		out = new PrintWriter(System.out);
		// strA = "abcabc";
		strA = fastScanner.nextLine() + "$";
		strB = fastScanner.nextLine() + "#";

		strALen = strA.length();
		strBLen = strB.length();

		str = strB + strA;
		len = str.length();

		StoryWithStrings = new StoryWithStrings(str);

		/*
		 * System.out.println("Enter the two indexes: "); i =
		 * fastScanner.nextInt(); j = fastScanner.nextInt();
		 * 
		 * System.out.println("LCP performed on string: \"" + str + "\"");
		 * System.out.println("LCP of suffixes starting from indexes (" + i +
		 * "," + j + "): " + StoryWithStrings.lcp(i, j));
		 */
		ans = StoryWithStrings.buildLCPArray(strBLen);

		if (ans.max == 0)
			out.println(0);
		else {
			out.println(str.substring(ans.index, ans.index + ans.max));
			out.println(ans.max);
		}

		out.flush();
		out.close();

	}

}

class Entry implements Comparable<Entry> {
	public int nr[];
	public int index;

	public Entry() {
		nr = new int[2];
	}

	@Override
	public int compareTo(Entry arg0) {
		// TODO Auto-generated method stub
		// if (this.nr[0] == arg0.nr[0]) {
		// if (this.nr[1] < arg0.nr[1])
		// return -1;
		// else if (this.nr[1] > arg0.nr[1])
		// return 1;
		// else
		// return 0;
		// }
		//
		// if (this.nr[0] < arg0.nr[0])
		// return -1;
		// else if (this.nr[0] > arg0.nr[0])
		// return 1;
		//
		// return 0;

		return this.nr[0] == arg0.nr[0] ? (this.nr[1] <= arg0.nr[1] ? -1 : 1)
				: (this.nr[0] <= arg0.nr[0] ? -1 : 1);
	}
}

class MaximumLCP {
	int max;
	int index;

	public MaximumLCP(int max, int index) {
		this.max = max;
		this.index = index;
	}
}