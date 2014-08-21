package com.april14;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CountingMatrices {

	public static int MAX = 1250 * 1250;
	public static int MAXN = 2500;
	public static ArrayList<Integer> primes;
	public static long mem[];
	public static long ans[];

	public static void buildSieve() {
		int i, j;
		boolean notPrime[] = new boolean[MAX + 1];

		for (i = 2; i <= MAX; i++) {

			for (j = i * 2; j <= MAX; j += i)
				notPrime[j] = true;

			if (!notPrime[i]) {
				primes.add(i);
			}
		}
	}

	public static void buildmem() {
		int i, j;
		long fact;
		mem[3] = 3;
		mem[2] = 1;

		for (i = 4; i <= MAX; i++) {
			fact = factors(i - 1);
			// System.out.println("Num factors of " + (i - 1) + ": " + fact);
			mem[i] = mem[i - 1] + fact;
		}
	}

	public static long factors(long num) {
		long factor = 1;
		int t;
		for (int p : primes) {
			if (num == 1)
				break;

			if (num % p == 0) {
				t = 0;
				while (num % p == 0) {
					num /= p;
					t += 1;
				}
				factor *= (t + 1);
			}

		}
		return factor;
	}

	public static void generateAns() {
		int i, j;
		int N;

		for (N = 3; N <= MAXN; N++) {
			// System.out.println("N = " + N);
			for (i = 1; i <= N; i++) {
				j = N - i;
				ans[N] += mem[i * j];
				// System.out.println("(i, j) : (" + i + "," + j + ")");
			}
		}

	}

	public static void main(String[] args) throws FileNotFoundException {
		int i, j;
		PrintWriter out = new PrintWriter("counting_matrix.txt");
		primes = new ArrayList<Integer>();
		mem = new long[MAX + 1];
		ans = new long[MAXN + 1];

		double startTime = System.currentTimeMillis();
		buildSieve();
		buildmem();
//		generateAns();
		double endTime = System.currentTimeMillis();

		System.out.println("Total time taken: " + (endTime - startTime) / 1000 + " seconds");

		out.print("{");

		for (i = 3; i <= MAXN; i++) {
			out.print(ans[i] + ", ");
			System.out.println(ans[i]);
		}

		out.print("}");
		out.flush();
		out.close();

		// for (int pr : primes)
		// System.out.println(pr);

		// for (i = 0; i < MAX; i++)
		// System.out.println(mem[i]);
	}

}
