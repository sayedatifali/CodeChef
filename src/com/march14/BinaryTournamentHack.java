package com.march14;

import java.io.IOException;
import java.io.PrintWriter;

public class BinaryTournamentHack {
	
	private static int ans [][];
	
	public static void main(String[] args) throws IOException {

		int i;
		int K;
		int N;
		int arr[];
		FastScannerSlow sFastScannerSlow;
		PrintWriter out = new PrintWriter(System.out);

		sFastScannerSlow = new FastScannerSlow();

		K = sFastScannerSlow.nextInt();

		N = 1 << K;
		
		ans = new int [K + 1][N];
		arr = new int[N];
		
		for (i = 0; i < N; i++) {
			arr[i] = i;
		}
		
		for (int k = 1; k <= K; k++) {
			
		}
		
		out.println(N);

		out.flush();
		out.close();

	}
	
	public void rec(int [] arr) {
		int N;
		
		N = arr.length;
	}

}
