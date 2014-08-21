package com.march14;

import java.io.IOException;
import java.io.PrintWriter;

public class Walk {

	public static void main(String[] args) throws IOException {

		int i;
		int T;
		int N;
		FastScannerSlow sFastScannerSlow;
		PrintWriter out = new PrintWriter(System.out);

		WalkSolve walkSolve;
		
		sFastScannerSlow = new FastScannerSlow();

		T = sFastScannerSlow.nextInt();

		for (i = 0; i < T; i++) {
			N = sFastScannerSlow.nextInt();
			
			walkSolve = new WalkSolve(N);
			
			for (int j = 0; j < N; j++) {
				walkSolve.setIndex(j, sFastScannerSlow.nextInt());
			}
			out.println(walkSolve.minVelocity(N));
		}
		out.flush();
		out.close();

	}

}

class WalkSolve {
	private static int sAttractiveness[];
	private int N;
	
	static {
		sAttractiveness = new int[100001];
	}
	
	public WalkSolve(int N) {
		this.N = N;
	}
	
	public void setIndex(int i, int val) {
		sAttractiveness[i] = val;
	}

	public int minVelocity(int N) {

		int i;
		int velocity = Integer.MIN_VALUE;

		for (i = 0; i < N; i++) {
			velocity = Math.max(velocity, i + sAttractiveness[i]);
		}

		return velocity;
	}
}