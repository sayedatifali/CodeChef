package com.practice;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public class WString {
	public static void main(String[] args) {
		int testCount;
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		FastScanner in = new FastScanner(inputStream, outputStream);
		PrintWriter out = new PrintWriter(outputStream);
		WStrings solver = new WStrings();
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

class WStrings {

	char[] testCharArray;

	public void solve(int testCount, FastScanner in, PrintWriter out)
			throws IOException {
		String testString;
		int[] hashIndex;
		int hashCount = 0;
		int i;
		int j = 0;
		int k;
		int len;
		int val1 = 0;
		int val2 = 0;
		int val3 = 0;
		int val4 = 0;
		int maxCount = 0;
		int tempCount = 0;

		testString = in.nextLine();
		testCharArray = testString.toCharArray();
		len = testString.length();
		hashIndex = new int[len];

		for (i = 0; i < len; i++) {
			if (testCharArray[i] == '#') {
				hashIndex[j++] = i;
				hashCount++;
			}
		}
		hashIndex[j] = -1;

		if (hashCount < 3) {
			out.println(0);
			out.flush();
			return;
		}

		for (i = 0; i < hashCount - 2; i++) {
			for (j = i + 1; j < hashCount - 1; j++) {
				for (k = j + 1; k < hashCount; k++) {
					val1 = maxChar(0, hashIndex[i] - 1, false);
					if (val1 == 0)
						continue;
					val2 = maxChar(hashIndex[i] + 1, hashIndex[j] - 1, true);
					if (val2 == 0)
						continue;
					val3 = maxChar(hashIndex[j] + 1, hashIndex[k] - 1, true);
					if (val3 == 0)
						continue;
					val4 = maxChar(hashIndex[k] + 1, len - 1, false);
					if (val4 == 0)
						continue;
					tempCount = val1 + val2 + val3 + val4 + 3;
					if (tempCount > maxCount)
						maxCount = tempCount;
				}
			}
		}

		// out.println(testString);
		out.println(maxCount);
		out.flush();

	}

	public int maxChar(int i, int j, boolean hashCheck) {
		int counted[] = new int[j - i + 1];
		int x, y;
		int temp = 0;
		int maxChar = 0;

		if (i >= j)
			return 0;

		for (x = i; x < j; x++) {
			temp = 1;
			if (counted[x - i] == 1)
				continue;
			if (hashCheck && testCharArray[x] == '#')
				return 0;
			for (y = x + 1; y <= j; y++) {
				if (testCharArray[x] == testCharArray[y]) {
					temp++;
					counted[y - i] = 1;
				}
			}
			if (temp > maxChar)
				maxChar = temp;
		}

		return maxChar;
	}

}