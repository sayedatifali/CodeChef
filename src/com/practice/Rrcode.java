package com.practice;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public class Rrcode {
	public static void main(String[] args) {
		int testCount;
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		FastScanner in = new FastScanner(inputStream, outputStream);
		PrintWriter out = new PrintWriter(outputStream);
		RRCodes solver = new RRCodes();
		try {
			testCount = in.nextInt();
			// System.out.println(testCount);
			for (int i = 0; i < testCount; i++)
				solver.solve(in, out);
			// System.out.print(in.nextInt() + ",");
		} catch (Exception e) {
			// TODO: Write catch block
			e.printStackTrace();
		}

		out.close();
	}
}

class RRCodes {

	public static String sXOR = "XOR";
	public static String sAND = "AND";
	public static String sOR = "OR";

	public void solve(FastScanner in, PrintWriter out) throws IOException {

		int i;
		int j;
		int N;
		int K;
		int answer;
		int array[];
		int op;
		int temp = 0;
		String operator;

		N = in.nextInt();
		K = in.nextInt();
		answer = in.nextInt();

		array = new int[N];

		for (i = 0; i < N; i++) {
			array[i] = in.nextInt();
		}
		operator = in.nextToken();

		if (K != 0) {
			if (operator.equalsIgnoreCase(sXOR))
				op = 0;
			else if (operator.equalsIgnoreCase(sAND))
				op = 1;
			else
				op = 2;

			if (op == 1) {
				for (j = 0; j < N; j++) {
					answer &= array[j];
				}
			} else if (op == 2) {
				for (j = 0; j < N; j++) {
					answer |= array[j];
				}
			} else {
				for (j = 0; j < N; j++) {
					temp ^= array[j];
				}
				if (K % 2 == 0)
					temp ^= temp;
				answer ^= temp;
			}
		}

		out.println(answer);
		out.flush();

	}

}