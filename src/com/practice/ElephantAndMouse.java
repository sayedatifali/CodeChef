package com.practice;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public class ElephantAndMouse {
	public static void main(String[] args) {
		int testCount;
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		FastScanner in = new FastScanner(inputStream, outputStream);
		PrintWriter out = new PrintWriter(outputStream);
		ElAndMs solver = new ElAndMs();
		try {
			testCount = in.nextInt();
			// System.out.println(testCount);
			for (int i = 0; i < testCount; i++)
				solver.solve(testCount, in, out);
			// System.out.print(in.nextInt() + ",");
		} catch (Exception e) {
			// TODO: Write catch block
			e.printStackTrace();
		}

		out.close();
	}
}

class ElAndMs {

	private class MinScarePath {
		int val;
		char direction;
	}

	public void solve(int testCount, FastScanner in, PrintWriter out)
			throws IOException {
		int[][] mouse;
		MinScarePath[][] minScarePath;
		int i, j, n, m;

		n = in.nextInt();
		m = in.nextInt();

		mouse = new int[n][m];
		minScarePath = new MinScarePath[n][m];

		for (i = 0; i < n; i++) {
			for (j = 0; j < m; j++) {
				mouse[i][j] = in.nextChar() - '0';
				minScarePath[i][j] = new MinScarePath();
			}
		}

		// for (i = 0; i < n; i++) {
		// for (j = 0; j < m; j++) {
		// out.print(mouse[i][j] + " ");
		// }
		// out.println();
		// }
		// out.println();

		minScarePath[0][0].val = mouse[0][0] + mouse[0][1] + mouse[1][0];
		minScarePath[0][0].direction = 'L';
		for (j = 1; j < m - 1; j++) {
			minScarePath[0][j].val = minScarePath[0][j - 1].val
					+ mouse[0][j + 1] + mouse[1][j];
			minScarePath[0][j].direction = 'L';
		}
		minScarePath[0][m - 1].val = minScarePath[0][m - 2].val  
				+ mouse[1][m - 1];
		minScarePath[0][m - 1].direction = 'L';

		for (i = 1; i < n - 1; i++) {
			minScarePath[i][0].val = minScarePath[i - 1][0].val + mouse[i][1]
					+ mouse[i + 1][0];
			minScarePath[i][0].direction = 'D';
		}
		minScarePath[n - 1][0].val = minScarePath[n - 2][0].val
				+ mouse[n - 1][1];
		minScarePath[n - 1][0].direction = 'D';

		// for (i = 0; i < n; i++) {
		// for (j = 0; j < m; j++) {
		// out.print(minScarePath[i][j].val + " ");
		// }
		// out.println();
		// }
		// out.println();

		for (i = 1; i < n; i++) {
			for (j = 1; j < m; j++) {
				checkPath(minScarePath[i][j], minScarePath[i][j - 1],
						minScarePath[i - 1][j], mouse[i][j - 1],
						mouse[i - 1][j]);
				if (j == m - 1 && i < n - 1) {
					minScarePath[i][j].val += mouse[i + 1][j];
				} else if (j == m - 1 && i == n - 1) {
					continue;
				} else if (i == n - 1 && j < m - 1) {
					minScarePath[i][j].val += mouse[i][j + 1];
				} else {
					minScarePath[i][j].val += mouse[i + 1][j] + mouse[i][j + 1];
				}
			}
		}

		// for (i = 0; i < n; i++) {
		// for (j = 0; j < m; j++) {
		// out.print(minScarePath[i][j].direction + " ");
		// }
		// out.println();
		// }
		out.println(minScarePath[n - 1][m - 1].val);
		out.flush();

		// System.out.println(n);
		// System.out.println(m);

	}

	public void checkPath(MinScarePath minPath, MinScarePath obj1,
			MinScarePath obj2, int val1, int val2) {
		int sum1;
		int sum2;

		if (obj1.direction == 'L') {
			sum1 = obj1.val + val2;
		} else {
			sum1 = obj1.val;
		}

		if (obj2.direction == 'D') {
			sum2 = obj2.val + val1;
		} else {
			sum2 = obj2.val;
		}

		if (sum1 < sum2) {
			minPath.val = sum1;
			minPath.direction = 'L';
		} else if (sum1 > sum2) {
			minPath.val = sum2;
			minPath.direction = 'D';
		} else {
			minPath.val = sum1;
			minPath.direction = '*';
		}
	}
}