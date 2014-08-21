package com.march14;

import java.io.PrintWriter;
import java.util.BitSet;

public class MikeAndStampsBitSet {

	private static int ans;
	private static int N, M;
	private static BitSet bitSet[];

	public static void backTrack(int at, int taken) {

		BitSet temp;

		if (at == M) {
			ans = Math.max(ans, taken);
			return;
		}

		if (ans >= taken + M - at)
			return;
		
		backTrack(at + 1, taken);

		temp = (BitSet) bitSet[M].clone();
		temp.and(bitSet[at]);

		if (temp.length() > 0) {
			return;
		}

		bitSet[M].xor(bitSet[at]);

		backTrack(at + 1, taken + 1);

		bitSet[M].xor(bitSet[at]);

	}

	public static void main(String[] args) {

		int i, j;
		int k;
		int stamp;
		PrintWriter out = new PrintWriter(System.out);
		FastScannerSlow fScanner = new FastScannerSlow();

		N = fScanner.nextInt();
		M = fScanner.nextInt();

		ans = 0;
		bitSet = new BitSet[M + 1];

		for (i = 0; i < M; i++) {
			k = fScanner.nextInt();
			bitSet[i] = new BitSet();

			for (j = 0; j < k; j++) {
				stamp = fScanner.nextInt();
				bitSet[i].set(stamp);
			}
		}

		bitSet[M] = new BitSet();

		// out.println("Length = " + bitSet[0].length() + "; size = "
		// + bitSet[M].size());
		// out.println("Length = " + bitSet[M].length() + "; size = "
		// + bitSet[M].size());

		backTrack(0, 0);

		out.println(ans);
		out.flush();
		out.close();

	}

}
