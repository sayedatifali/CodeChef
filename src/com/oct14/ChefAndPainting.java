package com.oct14;

import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * http://www.codechef.com/OCT14/problems/CHEFPNT
 * 
 * @author sultan.of.swing
 * 
 */

public class ChefAndPainting {

	public FasterScanner mFScanner;
	public PrintWriter mOut;
	public int N;
	public int M;
	public int K;
	public int mArray[][];
	public static final int BLACK = 1;
	public static final int WHITE = 0;
	public static final int RED = 2;
	public static final int VERTICAL = 1;
	public static final int HORIZONTAL = 0;

	public ChefAndPainting() {
		mFScanner = new FasterScanner();
		mOut = new PrintWriter(System.out);
	}

	public void solve() {

		int i;
		int j;
		int row, col;
		int path;
		ArrayList<Point> res = new ArrayList<Point>();
		Point point;

		N = mFScanner.nextInt();
		M = mFScanner.nextInt();
		K = mFScanner.nextInt();

		mArray = new int[N][M];

		for (i = 0; i < K; i++) {
			row = mFScanner.nextInt() - 1;
			col = mFScanner.nextInt() - 1;

			mArray[row][col] = BLACK;
		}

		for (i = 0; i < N; i++) {
			for (j = 0; j < M; j++) {

				if (mArray[i][j] == WHITE) {
					path = findOptimalPath(i, j);
					markFromIndex(i, j, path);

					point = new Point(i + 1, j + 1, path);
					res.add(point);
				}

			}
		}

		mOut.println(res.size());

		for (Point p : res) {
			mOut.print(p.i);
			mOut.print(" ");
			mOut.print(p.j);
			mOut.print(" ");
			mOut.println(p.path);
		}

	}

	public int findOptimalPath(int row, int col) {

		int i, j;
		int vert, horiz;

		vert = horiz = 1;

		// Horizontal Path

		i = row;
		j = col - 1;

		while (j >= 0 && mArray[i][j] == WHITE) {
			horiz++;
			j--;
		}

		j = col + 1;

		while (j < M && mArray[i][j] == WHITE) {
			horiz++;
			j++;
		}

		// Vertical Path

		i = row - 1;
		j = col;

		while (i >= 0 && mArray[i][j] == WHITE) {
			vert++;
			i--;
		}

		i = row + 1;

		while (i < N && mArray[i][j] == WHITE) {
			vert++;
			i++;
		}

		if (vert >= horiz)
			return VERTICAL;

		return HORIZONTAL;

	}

	public void markFromIndex(int row, int col, int path) {

		int i, j;

		mArray[row][col] = RED;

		if (path == VERTICAL) {
			i = row - 1;
			j = col;

			while (i >= 0 && mArray[i][j] == WHITE) {
				mArray[i][j] = RED;
				i--;
			}

			i = row + 1;

			while (i < N && mArray[i][j] == WHITE) {
				mArray[i][j] = RED;
				i++;
			}

		} else if (path == HORIZONTAL) {

			i = row;
			j = col - 1;

			while (j >= 0 && mArray[i][j] == WHITE) {
				mArray[i][j] = RED;
				j--;
			}

			j = col + 1;

			while (j < M && mArray[i][j] == WHITE) {
				mArray[i][j] = RED;
				j++;
			}

		}

	}

	class Point {
		int i;
		int j;
		int path;

		public Point(int row, int col, int path) {
			i = row;
			j = col;
			this.path = path;
		}
	}

	public void flush() {
		mOut.flush();
	}

	public void close() {
		mOut.close();
	}

	public static void main(String[] args) {
		ChefAndPainting mSol = new ChefAndPainting();
		mSol.solve();
		mSol.flush();
		mSol.close();
	}

}