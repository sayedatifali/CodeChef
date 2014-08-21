package com.practice;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;

public class Predicts {
	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		FastScannerMinimal in = new FastScannerMinimal(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		Predict solver = new Predict();
		int testCount = Integer.parseInt(in.next());
		for (int i = 1; i <= testCount; i++)
			solver.solve(i, in, out);
		out.close();
	}
}

class Predict {

	public static final BigDecimal sMean = new BigDecimal("0.5");
	public static final BigDecimal sTwo = new BigDecimal("2");
	public static final BigDecimal sOne = new BigDecimal("1");
	public static final BigDecimal sTT = new BigDecimal("10000");

	public void solve(int testNumber, FastScannerMinimal in, PrintWriter out) {
		BigDecimal ans;
		BigDecimal K;

		K = new BigDecimal(in.next());
		ans = K.multiply(sTwo);
		ans = ans.add(sOne);
		ans = ans.multiply(sTT);
		ans = ans.multiply(sOne.subtract(K));
		if (K.compareTo(sMean) > 0) {
			ans = ans.add(sTT.multiply(K.multiply(sTwo).subtract(sOne)));
		}
		System.out.printf("%1.5f\n", ans.setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue());
//		out.println(ans);
//		out.flush();
	}

}