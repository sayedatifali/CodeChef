package com.oct14;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * http://www.codechef.com/problems/TRIPS
 * 
 * @author sultan.of.swing
 * 
 */

public class Trips {

	public FasterScanner mFScanner;
	public PrintWriter mOut;
	public int N;
	public int M;
	public ArrayList<Integer> mAdj[];
	public ArrayList<Integer> mDist[];
	public int mDistance[];
	public boolean mMarked[];
	public int mTree[];
	public int mLevel[];
	public int mLCA[][];
	public int mLCAStrength[][];
	public int mLog;
	public Query queries[];
	public Queue<Integer> mBfsQueue;

	public Trips() {
		mFScanner = new FasterScanner();
		mOut = new PrintWriter(System.out);
	}

	public void solve() {
		int i;
		int u, v;
		int w;
		int dist;
		int lastStrength;
		int strength;
		int steps;
		int res[];
		int sqrtN;
		int root;
		StepsInfo res1;
		StepsInfo res2;

		N = mFScanner.nextInt();

		mAdj = new ArrayList[N];
		mDist = new ArrayList[N];
		mMarked = new boolean[N];
		mDistance = new int[N];
		mBfsQueue = new LinkedList<Integer>();

		sqrtN = (int) Math.sqrt(N);

		for (i = 0; i < N; i++) {
			mAdj[i] = new ArrayList<Integer>();
			mDist[i] = new ArrayList<Integer>();
		}

		for (i = 0; i < N - 1; i++) {

			u = mFScanner.nextInt() - 1;
			v = mFScanner.nextInt() - 1;
			dist = mFScanner.nextInt();

			mAdj[u].add(v);
			mAdj[v].add(u);
			mDist[u].add(dist);
			mDist[v].add(dist);
		}

		mLog = 32 - Integer.numberOfLeadingZeros(N - 1);
		mLCA = new int[N][mLog];
		mLCAStrength = new int[N][mLog];
		mTree = new int[N];
		mLevel = new int[N];

		// root = (int) (Math.random() * (N - 1));
		root = 0;
		rootTree(root);
		preprocess();

		M = mFScanner.nextInt();

		res = new int[M];
		queries = new Query[M];

		for (i = 0; i < M; i++) {
			u = mFScanner.nextInt() - 1;
			v = mFScanner.nextInt() - 1;
			strength = mFScanner.nextInt();
			queries[i] = new Query(u, v, strength, i);
		}

		Arrays.sort(queries);
		lastStrength = -1;

		for (i = 0; i < M; i++) {

			u = queries[i].u;
			v = queries[i].v;
			strength = queries[i].strength;

			w = lca(u, v);

			if (strength <= sqrtN) {

				if (lastStrength != strength) {
					preprocessSmall(strength);
					lastStrength = strength;
				}

				res1 = moveUpSmall(u, w);
				res2 = moveUpSmall(v, w);

			} else {
				res1 = moveUp(u, w, strength);
				res2 = moveUp(v, w, strength);
			}

			steps = res1.numSteps + res2.numSteps;
			steps += Math.ceil((res1.distRemaining + res2.distRemaining)
					/ strength);

			res[queries[i].queryIndex] = steps;
		}

		for (i = 0; i < M; i++)
			mOut.println(res[i]);
	}

	public void rootTree(int root) {
		mTree[root] = -1;
		mLevel[root] = 0;
		mDistance[root] = 0;
		bfs(root);
	}

	public void bfs(int root) {

		int node;
		int index;
		mBfsQueue.add(root);
		mMarked[root] = true;
		index = 0;

		while (!mBfsQueue.isEmpty()) {

			node = mBfsQueue.poll();
			index = 0;

			for (int v : mAdj[node]) {
				if (!mMarked[v]) {
					mTree[v] = node;
					mLevel[v] = mLevel[node] + 1;
					mDistance[v] = mDistance[node] + mDist[node].get(index);
					mMarked[v] = true;
					mBfsQueue.add(v);
				}
				index++;
			}
		}
	}

	public void preprocess() {
		int i;
		int j;

		for (i = 0; i < mLog; i++) {
			for (j = 0; j < N; j++) {
				if (i == 0) {
					mLCA[j][i] = mTree[j];
				} else if (mLCA[j][i - 1] != -1) {
					mLCA[j][i] = mLCA[mLCA[j][i - 1]][i - 1];
				} else {
					mLCA[j][i] = -1;
				}
			}
		}
	}

	public void preprocessSmall(int strength) {
		int i;
		int j;

		for (i = 0; i < mLog; i++) {
			for (j = 0; j < N; j++) {
				if (i == 0) {
					mLCAStrength[j][i] = singleJump(j, strength);
				} else if (mLCAStrength[j][i - 1] != -1) {
					mLCAStrength[j][i] = mLCAStrength[mLCAStrength[j][i - 1]][i - 1];
				} else {
					mLCAStrength[j][i] = -1;
				}
			}
		}
	}

	public int lca(int u, int v) {
		int i;
		int log;

		if (mLevel[u] < mLevel[v]) {
			u ^= v;
			v ^= u;
			u ^= v;
		}

		for (log = 0; (1 << log) <= mLevel[u]; log++)
			;
		log--;

		for (i = log; i >= 0; i--) {
			if (mLevel[u] - (1 << i) >= mLevel[v]) {
				u = mLCA[u][i];
			}
		}

		if (u == v)
			return u;

		for (i = log; i >= 0; i--) {
			if (mLCA[u][i] != -1 && mLCA[u][i] != mLCA[v][i]) {
				u = mLCA[u][i];
				v = mLCA[v][i];
			}
		}

		return mTree[u];
	}

	public int singleJump(int u, int strength) {
		int i;
		int v;
		int st = mDistance[u];

		for (i = mLog - 1; i >= 0; i--) {

			v = mLCA[u][i];

			if (v == -1 || st - mDistance[v] > strength)
				continue;

			u = v;
		}

		return u;
	}

	public StepsInfo moveUp(int u, int w, int strength) {

		int numSteps = 0;
		int distRemaining = 0;
		int v;

		while (u != w) {

			v = singleJump(u, strength);

			if (mLevel[v] > mLevel[w]) {
				numSteps += 1;
				u = v;
			} else {
				distRemaining = mDistance[u] - mDistance[w];
				break;
			}
		}

		return new StepsInfo(numSteps, distRemaining);

	}

	public StepsInfo moveUpSmall(int u, int w) {

		int v;
		int numSteps = 0;

		for (int i = mLog - 1; i >= 0; i--) {

			v = mLCAStrength[u][i];

			if (v == -1 || mLevel[v] <= mLevel[w])
				continue;

			u = v;

			numSteps += (1 << i);

		}

		return new StepsInfo(numSteps, mDistance[u] - mDistance[w]);
	}

	class StepsInfo {
		int numSteps;
		double distRemaining;

		public StepsInfo(int numSteps, int distRemaining) {
			this.numSteps = numSteps;
			this.distRemaining = distRemaining;
		}
	}

	// TODO: Use primitive data types instead of objects in case of TLE

	class Query implements Comparable<Query> {
		int queryIndex;
		int u, v;
		int strength;

		public Query(int u, int v, int strength, int queryIndex) {
			this.u = u;
			this.v = v;
			this.strength = strength;
			this.queryIndex = queryIndex;
		}

		@Override
		public int compareTo(Query o) {
			// TODO Auto-generated method stub
			return Integer.compare(this.strength, o.strength);
		}
	}

	public void flush() {
		mOut.flush();
	}

	public void close() {
		mOut.close();
	}

	public static void main(String[] args) {
		Trips mSol = new Trips();
		try {
			mSol.solve();
		} catch (Exception e) {
			e.printStackTrace();
		}
		mSol.flush();
		mSol.close();
	}

}