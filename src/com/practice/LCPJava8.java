package com.practice;

public class LCPJava8 {

//	private static int sSuffix[];
//	private static int L;
//
//	// suffix array in O(n*log(n))
//	public static int[] suffixArray(CharSequence str) {
//		int n = str.length();
//		Integer[] order = new Integer[n];
//		for (int i = 0; i < n; i++)
//			order[i] = n - 1 - i;
//
//		// stable sort of characters. java8 lambda syntax
//		 Arrays.sort(order, (a, b) -> Character.compare(str.charAt(a),
//		 str.charAt(b)));
//
//		// sa[i] - suffix on i'th position after sorting by first len characters
//		// rank[i] - position of the i'th suffix after sorting by first len
//		// characters
//		int[] sa = new int[n];
//		int[] rank = new int[n];
//		for (int i = 0; i < n; i++) {
//			sa[i] = order[i];
//			rank[i] = str.charAt(i);
//		}
//
//		for (int len = 1; len < n; len *= 2) {
//			int[] r = rank.clone();
//			for (int i = 0; i < n; i++) {
//				// condition s1 + len < n simulates 0-symbol at the end of the
//				// string
//				// a separate class is created for each suffix followed by
//				// 0-symbol
//				rank[sa[i]] = i > 0 && r[sa[i - 1]] == r[sa[i]]
//						&& sa[i - 1] + len < n
//						&& r[sa[i - 1] + len / 2] == r[sa[i] + len / 2] ? rank[sa[i - 1]]
//						: i;
//			}
//			// Suffixes are already sorted by first len characters
//			// Now sort suffixes by first len * 2 characters
//			int[] cnt = new int[n];
//			for (int i = 0; i < n; i++)
//				cnt[i] = i;
//			int[] s = sa.clone();
//			for (int i = 0; i < n; i++) {
//				// s[i] - order of suffixes sorted by first len characters
//				// (s[i] - len) - order of suffixes sorted only by second len
//				// characters
//				int s1 = s[i] - len;
//				// sort only suffixes of length > len, others are already sorted
//				if (s1 >= 0)
//					sa[cnt[rank[s1]]++] = s1;
//			}
//		}
//		return sa;
//	}
//
//	public static void buildSuffix(int[] sa) {
//		int n = sa.length;
//		for (int i = 0; i < n; i++)
//			sSuffix[sa[i]] = i;
//	}
//
//	// longest common prefixes array in O(n)
//	public static int[] lcp(int[] sa, CharSequence s) {
//		int n = sa.length;
//		int[] lcp = new int[n - 1];
//		for (int i = 0, h = 0; i < n; i++) {
//			if (sSuffix[i] < n - 1) {
//				for (int j = sa[sSuffix[i] + 1]; Math.max(i, j) + h < s
//						.length() && s.charAt(i + h) == s.charAt(j + h); ++h)
//					;
//				lcp[sSuffix[i]] = h;
//				if (h > 0)
//					--h;
//			}
//		}
//		return lcp;
//	}
//
//	public static MaximumLCP buildLCPArray(int[] lcp, int len) {
//
//		int i;
//		int index = 0;
//		MaximumLCP ans;
//		int max = 0;
//
//		for (i = 1; i < L; i++) {
//			if ((sSuffix[i - 1] < len && sSuffix[i] >= len)
//					|| (sSuffix[i - 1] >= len && sSuffix[i] < len)) {
//				if (lcp[i] > max) {
//					max = lcp[i];
//					index = sSuffix[i] < sSuffix[i - 1] ? sSuffix[i]
//							: sSuffix[i - 1];
//				} else if (lcp[i] == max
//						&& (sSuffix[i] < index || sSuffix[i - 1] < index)) {
//					index = sSuffix[i] < sSuffix[i - 1] ? sSuffix[i]
//							: sSuffix[i - 1];
//				}
//			}
//		}
//
//		ans = new MaximumLCP(max, index);
//
//		return ans;
//	}
//
//	public static void main(String[] args) {
//		int strBLen;
//		int[] suffixArray;
//		int[] lcp;
//		String strA;
//		StringBuilder strBul;
//		FastScannerSlow fastScanner;
//		PrintWriter out;
//		MaximumLCP ans;
//
//		fastScanner = new FastScannerSlow();
//		out = new PrintWriter(System.out);
//		strA = fastScanner.nextLine();
//
//		strBul = new StringBuilder(fastScanner.nextLine());
//		strBul.append("#");
//		strBLen = strBul.length();
//
//		strBul.append(strA);
//		strBul.append("$");
//
//		// str = strBul.toString();
//		L = strBul.length();
//
//		suffixArray = Main.suffixArray(strBul);
//
//		Main.buildSuffix(suffixArray);
//
//		lcp = Main.lcp(suffixArray, strBul);
//
//		ans = Main.buildLCPArray(lcp, strBLen);
//
//		// ans = new MaximumLCP(0, 0);
//
//		if (ans.max == 0)
//			out.println(0);
//		else {
//			out.println(strBul.substring(ans.index, ans.index + ans.max));
//			out.println(ans.max);
//		}
//
//		out.flush();
//		out.close();
//
//	}
//
}
//
//class MaximumLCP {
//	int max;
//	int index;
//
//	public MaximumLCP(int max, int index) {
//		this.max = max;
//		this.index = index;
//	}
//}

//class FastScannerSlow {
//
//	BufferedReader br;
//	StringTokenizer st;
//
//	public FastScannerSlow() {
//		br = new BufferedReader(new InputStreamReader(System.in));
//	}
//
//	String next() {
//		while (st == null || !st.hasMoreElements()) {
//			try {
//				st = new StringTokenizer(br.readLine());
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		return st.nextToken();
//	}
//
//	int nextInt() {
//		return Integer.parseInt(next());
//	}
//
//	long nextLong() {
//		return Long.parseLong(next());
//	}
//
//	double nextDouble() {
//		return Double.parseDouble(next());
//	}
//
//	String nextLine() {
//		String str = "";
//		try {
//			str = br.readLine();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return str;
//	}
//
//	char[] nextCharArray(int N) {
//		int i;
//		char[] array;
//		String str;
//
//		array = new char[N];
//
//		i = 0;
//
//		str = nextLine();
//
//		for (i = 0; i < N && i < str.length(); i++) {
//			array[i] = str.charAt(i);
//		}
//
//		return array;
//	}
//
//	char[][] nextChar2DArray(int M, int N) {
//		int i;
//		char[][] array;
//
//		array = new char[M][N];
//
//		i = 0;
//
//		for (i = 0; i < M; i++) {
//			array[i] = nextCharArray(N);
//		}
//
//		return array;
//	}
//
//	int[] nextIntArray(int N) {
//		int i;
//		int[] array;
//
//		array = new int[N];
//
//		i = 0;
//
//		for (i = 0; i < N; i++) {
//			array[i] = nextInt();
//		}
//
//		return array;
//	}
//
//	int[][] nextInt2DArray(int M, int N) {
//		int i;
//		int[][] array;
//
//		array = new int[M][N];
//
//		i = 0;
//
//		for (i = 0; i < M; i++) {
//			array[i] = nextIntArray(N);
//		}
//
//		return array;
//	}
//
//	long[] nextLongArray(int N) {
//		int i;
//		long[] array;
//
//		array = new long[N];
//
//		i = 0;
//
//		for (i = 0; i < N; i++) {
//			array[i] = nextLong();
//		}
//
//		return array;
//	}
//
//	long[][] nextLong2DArray(int M, int N) {
//		int i;
//		long[][] array;
//
//		array = new long[M][N];
//
//		i = 0;
//
//		for (i = 0; i < M; i++) {
//			array[i] = nextLongArray(N);
//		}
//
//		return array;
//	}
//
//}
