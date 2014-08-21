package com.march14;

import java.io.PrintWriter;
import java.util.ArrayList;

public class Main {
	static int[] heap;
	static ArrayList<Integer> flips;

	static {
		flips = new ArrayList<Integer>();
	}

	public static void flip(int n) {
		for (int i = 0; i < (n + 1) / 2; ++i) {
			int tmp = heap[i];
			heap[i] = heap[n - i];
			heap[n - i] = tmp;
		}

//		val.add(e)
		flips.add(n);
	}

	public static int[] minmax(int n) {
		int xm, xM;
		xm = xM = heap[0];
		int posm = 0, posM = 0;

		for (int i = 1; i < n; ++i) {
			if (heap[i] < xm) {
				xm = heap[i];
				posm = i;
			} else if (heap[i] > xM) {
				xM = heap[i];
				posM = i;
			}
		}
		return new int[] { posm, posM };
	}

	public static void sort(int n, int dir) {
		if (n == 0)
			return;

		int[] mM = minmax(n);
		int bestXPos = mM[dir];
		int altXPos = mM[1 - dir];
		boolean flipped = false;

		if (bestXPos == n - 1) {
			--n;
		} else if (bestXPos == 0) {
			flip(n - 1);
			--n;
		} else if (altXPos == n - 1) {
			dir = 1 - dir;
			--n;
			flipped = true;
		} else {
			flip(bestXPos);
		}
		sort(n, dir);

		if (flipped) {
			flip(n);
		}
	}

	static void PankCakeSort(int[] numbers) {
		heap = numbers;
		sort(numbers.length, 1);
	}

	public static void main(String[] args) {
		int i;
		int N;
		int[] numbers;
		FastScannerSlow fastScanner = new FastScannerSlow();
		PrintWriter out;
		
		out = new PrintWriter(System.out);
		
		N = fastScanner.nextInt();
		numbers = new int[N];
		
		for (i = 0; i < N; i++) {
			numbers[i] = fastScanner.nextInt();
		}

		PankCakeSort(numbers);
//		System.out.println(pancakes);
		out.println(flips.size());
		
		for (i = 0; i < flips.size(); i++) {
			out.println("1 " + (flips.get(i) + 1));
		}
		
		out.flush();
		out.close();
	}
}

