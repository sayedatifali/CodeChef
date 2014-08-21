package com.april14;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class SerejaAndPermutation {

	public static void main(String[] args) {
		int i;
		int T;
		int size;
		int N, k;
//		int aDash[];
		long S;
		ArrayList<Pair> order;
		FastScannerSlow fastScanner = new FastScannerSlow();
		PrintWriter out = new PrintWriter(System.out);

		order = new ArrayList<Pair>();

		T = fastScanner.nextInt();

		while (T-- > 0) {
			N = fastScanner.nextInt();
			k = fastScanner.nextInt();
			S = fastScanner.nextLong();
//			aDash = fastScanner.nextIntArray(N);

			order.clear();

			for (i = 0; i < N; i++) {
				order.add(new Pair(i + 1, fastScanner.nextInt()));
			}

			Collections.sort(order);
			size = order.size();

			for (k = 0; k < size; k++) {
				out.print(order.get(k).index);
				if (k != size - 1)
					out.print(" ");
			}

			out.println();

		}
		out.flush();
		out.close();
	}

	private static class Pair implements Comparable<Pair> {
		int index;
		int val;

		public Pair(int index, int val) {
			this.index = index;
			this.val = val;
		}

		@Override
		public int compareTo(Pair o) {
			// TODO Auto-generated method stub
			if (this.val < o.val)
				return -1;
			else if (this.val > o.val)
				return 1;
			return 0;
		}
	}

}
