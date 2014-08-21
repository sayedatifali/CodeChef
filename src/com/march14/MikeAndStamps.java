package com.march14;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

// Search for maximum independent set
// Based on http://web.cecs.pdx.edu/~mperkows/temp/HOM1/findMaxClique.pdf
public class MikeAndStamps {

	static void findMaximumIndependentSet(List<Integer> cur,
			List<Integer> result, boolean[][] graph, int[] oldSet, int ne,
			int ce) {
		int nod = 0;
		int minnod = ce;
		int fixp = -1;
		int s = -1;

		for (int i = 0; i < ce && minnod != 0; i++) {
			int p = oldSet[i];
			int cnt = 0;
			int pos = -1;

			for (int j = ne; j < ce; j++)
				if (graph[p][oldSet[j]]) {
					if (++cnt == minnod)
						break;
					pos = j;
				}

			if (minnod > cnt) {
				minnod = cnt;
				fixp = p;
				if (i < ne) {
					s = pos;
				} else {
					s = i;
					nod = 1;
				}
			}
		}

		int[] newSet = new int[ce];

		for (int k = minnod + nod; k >= 1; k--) {
			int sel = oldSet[s];
			oldSet[s] = oldSet[ne];
			oldSet[ne] = sel;

			int newne = 0;
			for (int i = 0; i < ne; i++)
				if (!graph[sel][oldSet[i]])
					newSet[newne++] = oldSet[i];

			int newce = newne;
			for (int i = ne + 1; i < ce; i++)
				if (!graph[sel][oldSet[i]])
					newSet[newce++] = oldSet[i];

			cur.add(sel);
			if (newce == 0) {
				if (result.size() < cur.size()) {
					result.clear();
					result.addAll(cur);
				}
			} else if (newne < newce) {
				if (cur.size() + newce - newne > result.size())
					findMaximumIndependentSet(cur, result, graph, newSet,
							newne, newce);
			}

			cur.remove(cur.size() - 1);
			if (k > 1)
				for (s = ++ne; !graph[fixp][oldSet[s]]; s++)
					;
		}
	}

	public static List<Integer> maximumIndependentSet(boolean[][] graph) {
		int n = graph.length;
		int[] all = new int[n];
		for (int i = 0; i < n; i++)
			all[i] = i;
		List<Integer> res = new ArrayList<Integer>();
		findMaximumIndependentSet(new ArrayList<Integer>(), res, graph, all, 0,
				n);
		return res;
	}

	public static void main(String[] args) throws IOException {

		int i, j;
		int N;
		int num;
		int stamp;
		int offers;
		boolean[][] g;
		int[] start;
		int[] end;
		HashSet<Integer> stamps[];
		FastScannerSlow sFastScannerSlow;
		PrintWriter out = new PrintWriter(System.out);

		sFastScannerSlow = new FastScannerSlow();

		N = sFastScannerSlow.nextInt();
		offers = sFastScannerSlow.nextInt();
		stamps = new HashSet[offers];
		start = new int[offers];
		end = new int[offers];

		g = new boolean[offers][offers];

		// Store all offers including the start and end stamps
		for (i = 0; i < offers; i++) {
			num = sFastScannerSlow.nextInt();
			stamps[i] = new HashSet<Integer>();

			for (j = 0; j < num; j++) {
				stamp = sFastScannerSlow.nextInt();
				if (j == 0) {
					start[i] = stamp;
				}
				if (j == num - 1)
					end[i] = stamp;
				stamps[i].add(stamp);
			}

		}

		// Build graph
		for (i = 0; i < offers; i++) {
			for (j = i + 1; j < offers; j++) {

				if (end[j] < start[i] || start[j] > end[i])
					continue;
				
				if(common(stamps[i], stamps[j])) {
					g[i][j] = true;
					g[j][i] = true;
				}

			}
		}
		
		List<Integer> res = maximumIndependentSet(g);
		
		out.println(res.size());

		out.flush();
		out.close();

	}

	public static boolean common(HashSet<Integer> s1, HashSet<Integer> s2) {

		if (s1.size() < s2.size()) {

			for (int s : s1) {
				if (s2.contains(s))
					return true;
			}

			return false;
		} else {
			for (int s : s2) {
				if (s1.contains(s))
					return true;
			}

			return false;
		}
	}
}