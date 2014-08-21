package com.june14;

/**
 * http://www.codechef.com/JUNE14/problems/DIGJUMP
 */

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import com.io.FastScannerSlow;

public class ChefAndDigitJumps {

	public static int minJump;
	public static int[] numbers;
	public static ArrayList<Integer>[] adj;
	public static int N;
	public static boolean marked[];
	public static boolean digitMark[];
	public static int mLevels[];

	public static void main(String[] args) {
		int i;
		String str;
		FastScannerSlow fastScanner = new FastScannerSlow();
		PrintWriter out = new PrintWriter(System.out);

		str = fastScanner.nextLine();
		N = str.length();

		adj = new ArrayList[10];
		digitMark = new boolean[10];

		for (i = 0; i < 10; i++)
			adj[i] = new ArrayList<Integer>();

		numbers = new int[N];
		mLevels = new int[N];

		for (i = 0; i < N; i++) {
			numbers[i] = str.charAt(i) - '0';
			adj[numbers[i]].add(i);
			mLevels[i] = Integer.MIN_VALUE;
		}

		marked = new boolean[N];
		minJump = minJumpBFS();

		out.println(minJump);

		out.flush();
		out.close();
	}

	public static int minJumpBFS() {
		int res;
		int i, j;
		int index;
		int level;
		int num;
		Queue<Integer> queue = new LinkedList<>();

		queue.add(0);
		marked[0] = true;
		res = 0;

		level = 0;
		mLevels[0] = 0;

		while (!queue.isEmpty()) {
			i = queue.poll();

			if (mLevels[i] != level) {
				level = mLevels[i];
				res += 1;
			}

			if (i == N - 1)
				break;

			if (i > 0 && !marked[i - 1]) {
				queue.add(i - 1);
				mLevels[i - 1] = level + 1;
				marked[i - 1] = true;
			}
			if (i < N - 1 && !marked[i + 1]) {
				queue.add(i + 1);
				mLevels[i + 1] = level + 1;
				marked[i + 1] = true;
			}

			num = numbers[i];

			if (digitMark[num])
				continue;

			digitMark[num] = true;

			for (j = 0; j < adj[num].size(); j++) {

				index = adj[num].get(j);

				if (!marked[index] && index != i - 1 && index != i + 1) {
					queue.add(index);
					mLevels[index] = level + 1;
					marked[index] = true;
				}
			}
		}

		return res;
	}
}