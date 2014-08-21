package com.march14;

import java.io.PrintWriter;

public class AnuGCD {
	
	public static void main(String [] args) {
		
	}

}

/**
 * Segment Tree using index beginning from 1. The two children for a parent node
 * n are at 2n and 2n + 1
 * 
 * @author doom
 * 
 */
class SegmentTree {

	private int N;
	private int TN;
	private long A[];
	private int Tree[];

	public SegmentTree(int N) {
		int i;
		TN = getTreeLength(N);
		Tree = new int[TN + 1];
		for (i = 0; i < TN; i++) {
			Tree[i] = -1;
		}
	}

	public SegmentTree(long[] A) {
		int i;

		N = A.length;

		TN = getTreeLength(N);

		this.A = new long[N];

		Tree = new int[TN + 1];

		for (i = 0; i < TN; i++) {
			if (i < N)
				this.A[i] = A[i];
			Tree[i] = -1;
		}
		constructSegmentTree(1, 0, N - 1);
	}

	public int getTreeLength(int N) {
		int TN;
		TN = N;
		TN |= (TN >> 1);
		TN |= (TN >> 2);
		TN |= (TN >> 4);
		TN |= (TN >> 8);
		TN |= (TN >> 16);

		TN = (TN + 1) << 1;

		return TN;
	}

	public void printSegmentTree() {
		int i;
		System.out.println("\nPrinting Segment Tree of length " + TN + ":");
		for (i = 0; i < TN; i++) {
			System.out.print(Tree[i] + " ");
		}
		System.out.println();
	}

	public void printOriginalArray() {
		printOriginalArray(A);
	}

	public void printOriginalArray(long [] A) {
		int i;
		System.out.println("\nPrinting original array of length: " + N + ":");
		for (i = 0; i < N; i++)
			System.out.print(A[i] + " ");
		System.out.println();
	}

	public void constructSegmentTree(int node, int begin, int end) {
		constructSegmentTree(node, begin, end, A);
	}

	public void constructSegmentTree(int node, int begin, int end, long[] A) {
		int mid;

		if (begin == end)
			Tree[node] = begin;

		else {
			mid = begin + (end - begin) / 2;
			constructSegmentTree(2 * node, begin, mid, A);
			constructSegmentTree(2 * node + 1, mid + 1, end, A);

			// System.out.println("node: " + node + "; begin: " + begin +
			// "; end: " + end);

			if (A[Tree[2 * node]] <= A[Tree[2 * node + 1]])
				Tree[node] = Tree[2 * node];
			else
				Tree[node] = Tree[2 * node + 1];

		}
	}

	public void updateValue(int index, long newVal) {
		updateValue(index, newVal, A);
	}

	public void updateValue(int index, long newVal, long[] A) {

		if (index < 0 || index >= N)
			return;

		A[index] = newVal;

		update(1, 0, N - 1, index, A);

	}

	private void update(int node, int begin, int end, int index, long[] A) {
		int mid;

		if (index < begin || index > end)
			return;

		if (begin == end)
			Tree[node] = begin;

		else {

			mid = begin + (end - begin) / 2;

			update(2 * node, begin, mid, index, A);
			update(2 * node + 1, mid + 1, end, index, A);

			if (A[Tree[2 * node]] <= A[Tree[2 * node + 1]])
				Tree[node] = Tree[2 * node];
			else
				Tree[node] = Tree[2 * node + 1];

		}

	}

	public int query(int node, int i, int j) {
		return query(node, 0, N - 1, i, j, A);
	}

	public int query(int node, int begin, int end, int i, int j, long[] A) {

		int q1;
		int q2;
		int mid;

		if (i > end || j < begin)
			return -1;

		// System.out.println("node: " + node + "; begin: " + begin + "; end: "
		// + end + "; i: " + i + "; j: " + j);

		if (i <= begin && j >= end)
			return Tree[node];

		mid = begin + (end - begin) / 2;

		q1 = query(2 * node, begin, mid, i, j, A);

		q2 = query(2 * node + 1, mid + 1, end, i, j, A);

		if (q1 == -1)
			return q2;

		if (q2 == -1)
			return q1;

		if (A[q1] <= A[q2])
			return q1;

		return q2;
	}

	public static void main(String[] args) {
		int i;
		int j;
		int N;
		long A[];
		FastScannerSlow fastScanner;
		PrintWriter out;
		SegmentTree segmentTree;

		fastScanner = new FastScannerSlow();
		out = new PrintWriter(System.out);

		System.out.println("Enter the length of the random array:");
		N = fastScanner.nextInt();
		A = new long[N];

		out.println("Generated array:");

		for (i = 0; i < N; i++) {
			A[i] = (long) (Math.random() * 100);
			out.print(A[i] + " ");
		}

		out.flush();

		segmentTree = new SegmentTree(A);

		segmentTree.printSegmentTree();

		out.flush();

		for (i = 0; i < N; i++) {
			for (j = i; j < N; j++) {
				out.println("Query range (" + i + " -> " + j + "): "
						+ segmentTree.query(1, i, j));
			}
		}
		out.flush();
		segmentTree.printSegmentTree();

		segmentTree.updateValue(9, 0);
		
		segmentTree.printOriginalArray();
		
		for (i = 0; i < N; i++) {
			for (j = i; j < N; j++) {
				out.println("Query range (" + i + " -> " + j + "): "
						+ segmentTree.query(1, i, j));
			}
		}
		out.flush();
		
		segmentTree.printSegmentTree();
		out.close();
	}

}
