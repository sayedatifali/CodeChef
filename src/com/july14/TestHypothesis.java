package com.july14;

import java.io.PrintWriter;

public class TestHypothesis {

	public PrintWriter mOut;

	public TestHypothesis() {
		mOut = new PrintWriter(System.out);
	}

	public void solve() {
		int i;
		int j, k, ll;
		int array[];
		int countThreshold;
		int l = 6;
		int m = 2;
		int count;
		int counter = 0;
		boolean status;

		// l X M
		array = new int[l * m];

		countThreshold = m * m / 2;

		for (i = 0; i < l * m; i++)
			array[i] = i;

		do {

			status = true;

			for (i = 0; i < l * m; i += m) {
				j = (i + m) % (l * m);
				count = 0;

				for (k = 0; k < m; k++) {
					for (ll = 0; ll < m; ll++) {
						if (array[i + k] > array[j + ll])
							count++;
					}
				}

				if (count <= countThreshold) {
					status = false;
					break;
				}

			}

			if (!status)
				continue;

			for (i = 0; i < l * m; i++) {
				System.out.print((array[i] + 1) + " ");
			}
			System.out.println();
			counter++;
			break;

		} while (nextPerm(array));

		System.out.println("Possible correct permutations: " + counter);

	}

	public boolean nextPerm(int[] a) {

		int i;
		int j;
		int N;
		int temp;

		N = a.length;
		i = N - 2;

		for (; i >= 0; i--) {
			if (a[i] < a[i + 1])
				break;
		}
		if (i < 0)
			return false;

		for (j = N - 1; j >= i; j--) {
			if (a[j] > a[i]) {
				temp = a[j];
				a[j] = a[i];
				a[i] = temp;
				break;
			}
		}

		for (j = i + 1; j < (N + i + 1) / 2; j++) {
			temp = a[N - j + i];
			a[N - j + i] = a[j];
			a[j] = temp;
		}

		return true;
	}

	public static void main(String[] args) {

		TestHypothesis mTestHypothesis;

		mTestHypothesis = new TestHypothesis();
		mTestHypothesis.solve();

	}

}
