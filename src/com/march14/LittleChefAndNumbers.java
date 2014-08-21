package com.march14;

import java.io.IOException;
import java.io.PrintWriter;

public class LittleChefAndNumbers {

	public static int[] numbers;
	public static final int sMAXN = 100001;

	static {
		numbers = new int[sMAXN];
	}

	public static void main(String[] args) throws IOException {

		int i;
		int T;
		int N;
		FastScannerSlow sFastScannerSlow;
		PrintWriter out = new PrintWriter(System.out);

		sFastScannerSlow = new FastScannerSlow();

		T = sFastScannerSlow.nextInt();

		for (i = 0; i < T; i++) {
			N = sFastScannerSlow.nextInt();

			for (int j = 0; j < N; j++) {
				numbers[j] = sFastScannerSlow.nextInt();
			}
			out.println(numPairs(N));
		}
		out.flush();
		out.close();

	}

	public static long numPairs(int N) {

		int i;
		long twos = 0;
		long NN;
		long pairs;

		NN = N;

		for (i = 0; i < N; i++) {
			if (numbers[i] == 0 || numbers[i] == 1)
				NN--;
			if (numbers[i] == 2)
				twos++;
		}

		pairs = (NN * (NN - 1)) / 2;

		pairs -= (twos * (twos - 1)) / 2;

		return pairs;
	}

}