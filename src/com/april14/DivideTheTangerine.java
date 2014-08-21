package com.april14;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class DivideTheTangerine {

//	private static ArrayList<Pair> chef;
//	private static ArrayList<Pair> child;
	private static HashSet<Integer> hashSetChild;
	private static HashSet<Integer> hashSetChef;

	public static void main(String[] args) {
		int i, j;
		int n, k, p;
		int a, b;
		int T;
//		double startTime, endTime;
//		Pair pair;
		FastScannerSlow fastScanner = new FastScannerSlow();
		PrintWriter out = new PrintWriter(System.out);

		T = fastScanner.nextInt();
//		startTime = System.currentTimeMillis();
//		child = new ArrayList<Pair>();
//		chef = new ArrayList<Pair>();
		hashSetChef = new HashSet<Integer>();
		hashSetChild = new HashSet<Integer>();

		while (T-- > 0) {
			n = fastScanner.nextInt();
			k = fastScanner.nextInt();
			p = fastScanner.nextInt();

//			chef.clear();
//			child.clear();
			
			hashSetChef.clear();
			hashSetChild.clear();

			for (i = 0; i < k; i++) {
				a = fastScanner.nextInt();
				b = fastScanner.nextInt();
//				chef.add(new Pair(a, b));
				hashSetChef.add(a);
			}

			for (i = 0; i < p; i++) {
				a = fastScanner.nextInt();
				b = fastScanner.nextInt();
//				child.add(new Pair(a, b));
				hashSetChild.add(a);
			}

			if (p > k) {
				out.println("No");
				continue;
			}

			if (possibleTangerine(n, k, p)) {
				out.println("Yes");
				continue;
			}

			out.println("No");

		}

//		endTime = System.currentTimeMillis();
//
//		out.println("Total time taken = " + (endTime - startTime) + " ms");

		out.flush();
		out.close();

	}
	
	public static boolean possibleTangerine(int n, int k, int p) {
		
		for (int entry : hashSetChild) {
			if (!hashSetChef.contains(entry))
				return false;
		}
		
		return true;
	}

//	public static boolean possible(int n, int k, int p) {
//		int i, j, a, b;
//		Pair chefPair;
//		Pair childPair;
//
//		Collections.sort(chef);
//		Collections.sort(child);
//
//		for (i = 0; i < p; i++) {
//			childPair = child.get(i);
//
//			for (j = 0; j < k; j++) {
//				chefPair = chef.get(j);
//				if (childPair.a < chefPair.a && childPair.b < chefPair.a)
//					break;
//
//				if (childPair.a > chefPair.a && childPair.a <= chefPair.b)
//					return false;
//
//				if (childPair.b >= chefPair.a && childPair.b < chefPair.b)
//					return false;
//			}
//
//		}
//
//		return true;
//	}
//
//	private static class Pair implements Comparable<Pair> {
//
//		public int a;
//		public int b;
//
//		public Pair(int a, int b) {
//			this.a = a;
//			this.b = b;
//		}
//
//		@Override
//		public int compareTo(Pair o) {
//			// TODO Auto-generated method stub
//			if (this.a < o.a)
//				return -1;
//			else if (this.a > o.a)
//				return 1;
//			return 0;
//		}
//
//	}

}
