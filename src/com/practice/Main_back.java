package com.practice;
import java.io.PrintWriter;
import java.util.Arrays;

import com.io.FastScannerSlow;

public class Main_back {

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
	static {
		P = new int[20][entryLen];
		sSuffix = new int[entryLen];
		sLcp = new int[entryLen];
	}

	public Main_back(String str) {
		int i;
		int level;
		int skip;
		L = str.length();

		initArrays(L);

		K = 32 - Integer.numberOfLeadingZeros(L - 1) + 1;

		for (i = 0; i < L; i++) {
			P[0][i] = str.charAt(i);
		}

		for (level = 1, skip = 1; skip < L; skip *= 2, level++) {
			for (i = 0; i < L; i++) {
				entry[i].nr[0] = P[level - 1][i];
				entry[i].nr[1] = (i + skip < L) ? P[level - 1][i + skip] : -1;
				entry[i].index = i;
			}

			Arrays.sort(entry, 0, L);

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

	public void initArrays(int N) {
		int i;
		entry = new Entry[N];
		for (i = 0; i < N; i++) {
			entry[i] = new Entry();
			sLcp[i] = 0;
			sSuffix[i] = 0;
			P[0][i] = 0;
		}		
	}

	public static void main(String[] args) {
		int strBLen;
		String strA;
		StringBuilder strBul;
		String str;
		FastScannerSlow fastScanner;
		PrintWriter out;
		Main_back Main;
		MaximumLCP ans;

		fastScanner = new FastScannerSlow();
		out = new PrintWriter(System.out);
		strA = fastScanner.nextLine();
		
		strBul = new StringBuilder(fastScanner.nextLine());
		strBul.append("#");
		strBLen = strBul.length();
		
		strBul.append(strA);
		strBul.append("$");		

		str = strBul.toString();

		Main = new Main_back(str);

		ans = Main.buildLCPArray(strBLen);

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
				
		if (this.nr[0] == arg0.nr[0]) {
			if (this.nr[1] < arg0.nr[1])
				return -1;
			else if (this.nr[1] > arg0.nr[1])
				return 1;
			else
				return 0;
		}

		if (this.nr[0] < arg0.nr[0])
			return -1;
		else if (this.nr[0] > arg0.nr[0])
			return 1;

		return 0;

//		return this.nr[0] == arg0.nr[0] ? (this.nr[1] <= arg0.nr[1] ? -1 : 1)
//				: (this.nr[0] <= arg0.nr[0] ? -1 : 1);
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