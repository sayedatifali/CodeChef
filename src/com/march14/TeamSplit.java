package com.march14;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public class TeamSplit {
	public static void main(String[] args) throws IOException {

		int i;
		int T;
		int a, b, c, d, N;
		FastScanner fScanner;
		PrintWriter out = new PrintWriter(System.out);
		Solve solve;

		fScanner = initStreams();

		T = fScanner.nextInt();

		for (i = 0; i < T; i++) {
			N = fScanner.nextInt();
			a = fScanner.nextInt();
			b = fScanner.nextInt();
			c = fScanner.nextInt();
			d = fScanner.nextInt();
			solve = new Solve(a, b, c, d, N);
			out.println(solve.solve());
		}
		out.flush();
		out.close();

	}

	public static FastScanner initStreams() {
		InputStream inputStream;
		OutputStream outputStream;
		FastScanner fScanner;

		inputStream = System.in;
		outputStream = System.out;
		fScanner = new FastScanner(inputStream, outputStream);

		return fScanner;
	}
}

class Solve {

	private static long[] strengths;
	private static long[] map;
	private int N;

	static {
		strengths = new long[3000001];
		map = new long[1000001];
	}

	public Solve(int a, int b, int c, int d, int N) {
		this.N = N;

		for (int i = 0; i < 1000001; i++)
			map[i] = 0;

		strengths[0] = d;

		map[d]++;

		for (int i = 1; i < N; i++) {
			strengths[i] = (a * strengths[i - 1] * strengths[i - 1] + b
					* strengths[i - 1] + c) % 1000000;
			map[(int) strengths[i]]++;
		}

		// Arrays.sort(strengths, 0, N);

	}

	public long solve() {
		long abs;
		int i;
		boolean flag;

		abs = 0;
		flag = true;
		for (i = 0; i < 1000001; i++) {
			if (map[i] % 2 == 1) {
				if (flag)
					abs += i;
				else
					abs -= i;
				flag = !flag;
			}
		}

		return Math.abs(abs);
	}

}