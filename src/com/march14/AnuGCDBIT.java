package com.march14;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * http://www.codechef.com/MARCH14/problems/ANUGCD
 * 
 * AC in Codechef! :D :D
 * 
 * Using BIT
 * 
 * @author sultan.of.swing
 *
 */

public class AnuGCDBIT {

	public PrintWriter mOut;
	public FasterScanner mFScanner;
	public int mN, mM;
	public int mA[];
	public int mBIT[][];
	public ArrayList<Integer> mPrimes;
	public ArrayList<Integer> mPrimeIndexes[];
	public ArrayList<Integer> mCount[];
	public int mSmallPrimesLen;
	public int mPrimesLen;
	public int mPrimesId[];
	public static final int sMaxNum = 100003;
	public static final int sMaxPrime = 350;

	public AnuGCDBIT() {
		mOut = new PrintWriter(System.out);
		mFScanner = new FasterScanner();
		mPrimesId = new int[sMaxNum];
		mPrimes = new ArrayList<Integer>();
		mCount = new ArrayList[sMaxNum];
		for (int i = 0; i < sMaxNum; i++)
			mCount[i] = new ArrayList<Integer>();
	}

	public void initBIT() {
		int i;
		int size;

		mBIT = new int[mPrimesLen][];

		for (i = 0; i < mPrimesLen; i++) {
			size = mPrimeIndexes[i].size();
			if (size == 0)
				continue;
			size += 5;
			mBIT[i] = new int[size];
		}
	}

	public void buildBIT() {
		int i;
		int N;

		for (i = 0; i < mPrimesLen; i++) {
			N = mPrimeIndexes[i].size();
			if (N == 0)
				continue;
			buildBIT(i, mPrimeIndexes[i]);
		}
	}

	private void buildBIT(int bit, ArrayList<Integer> indexes) {
		int i;
		int size;

		size = indexes.size();
		for (i = 1; i <= size; i++) {
			update(bit, i, indexes.get(i - 1), size);
		}
	}

	public void update(int bit, int idx, int index, int size) {
		int max;

		max = size + 3;

		while (idx < max) {
			mBIT[bit][idx] = Math.max(mBIT[bit][idx], mA[index]);
			idx += (idx & -idx);
		}
	}

	public int query(int bit, int begin, int end) {
		int prev;
		int max = -1;

		if (mBIT[bit] == null)
			return max;

		while (end >= begin) {
			prev = end - (end & -end);
			if (prev >= begin) {
				max = Math.max(max, mBIT[bit][end]);
				end = prev;
			} else {
				max = Math.max(max, mA[mPrimeIndexes[bit].get(end - 1)]);
				end--;
			}
		}

		return max;
	}

	public void sieveOfErastothenes() {
		int i;
		long j;
		int id;
		boolean primes[];

		primes = new boolean[sMaxNum];
		Arrays.fill(mPrimesId, -1);

		for (i = 2; i < sMaxNum; i++)
			primes[i] = true;

		id = 0;

		for (i = 2; i < sMaxNum; i++) {
			if (!primes[i])
				continue;
			for (j = 1L * i * i; j < sMaxNum; j += i) {
				primes[(int) j] = false;
			}
			mPrimes.add(i);
			mPrimesId[i] = id++;
		}

		mSmallPrimesLen = 70;
		mPrimesLen = mPrimes.size();
	}

	public void allocatePrimeIndexes() {
		mPrimeIndexes = new ArrayList[mPrimesLen];
		for (int i = 0; i < mPrimesLen; i++)
			mPrimeIndexes[i] = new ArrayList<Integer>();
	}

	public void takeInput() {
		mN = mFScanner.nextInt();
		mM = mFScanner.nextInt();
		mA = new int[mN];
		for (int i = 0; i < mN; i++) {
			mA[i] = mFScanner.nextInt();
		}
	}

	public void processInput() {
		int i;
		int j;
		int num;
		int prime;
		int id;

		for (i = 0; i < mN; i++) {
			num = mA[i];

			mCount[num].add(i);

			for (j = 0; j < mSmallPrimesLen; j++) {
				prime = mPrimes.get(j);
				if (num % prime == 0) {
					mPrimeIndexes[j].add(i);
					while (num % prime == 0) {
						num /= prime;
					}
				}

				if (num == 1 || mPrimesId[num] != -1)
					break;
			}

			if (num > 1) {
				// Prime number left over
				id = mPrimesId[num];
				mPrimeIndexes[id].add(i);
			}

		}
	}

	public int lowerBound(ArrayList<Integer> array, int begin, int end, int val) {
		
		int index, count;
		int step;
		
		count = end - begin + 1;
		
		while (count > 0) {
			index = begin;
			step = count / 2;
			index += step;
			
			if (array.get(index) < val) {
				begin = ++index;
				count -= (step + 1);
			} else {
				count = step;
			}
		}
		
		return begin;
	}

	public int upperBound(ArrayList<Integer> array, int begin, int end, int val) {
		int index, count, step;
		
		count = end - begin + 1;
		
		while (count > 0) {
			index = begin;
			step = count / 2;
			index += step;
			if (val >= array.get(index)) {
				begin = ++index;
				count -= (step + 1);
			} else {
				count = step;
			}
		}
		return begin;
	}

	public void processQueries() {
		int i;
		int j;
		int x;
		int y;
		int N;
		int begin;
		int end;
		int num;
		int prime;
		int id;
		int res;
		int freq;
		int ret;

		for (i = 0; i < mM; i++) {
			res = -1;
			freq = -1;
			num = mFScanner.nextInt();
			x = mFScanner.nextInt() - 1;
			y = mFScanner.nextInt() - 1;

			for (j = 0; j < mSmallPrimesLen; j++) {

				prime = mPrimes.get(j);

				if (num % prime == 0) {
					N = mPrimeIndexes[j].size();
					begin = lowerBound(mPrimeIndexes[j], 0, N - 1, x) + 1;
					end = upperBound(mPrimeIndexes[j], 0, N - 1, y);
					ret = query(j, begin, end);
					if (ret > res) {
						res = ret;
					}
				}

				while (num % prime == 0)
					num /= prime;

				if (num == 1 || mPrimesId[num] != -1)
					break;
			}

			if (num > 1) {
				id = mPrimesId[num];
				N = mPrimeIndexes[id].size();
				begin = lowerBound(mPrimeIndexes[id], 0, N - 1, x) + 1;
				end = upperBound(mPrimeIndexes[id], 0, N - 1, y);
				ret = query(id, begin, end);
				if (ret > res) {
					res = ret;
				}
			}
			
			if (res != -1) {
				freq = upperBound(mCount[res], 0, mCount[res].size() - 1, y) 
						- lowerBound(mCount[res], 0, mCount[res].size() - 1, x);
			}

			mOut.print(res);
			mOut.print(" ");
			mOut.println(freq);
		}
	}

	public void solve() {
		sieveOfErastothenes();
		takeInput();
		allocatePrimeIndexes();
		processInput();
		initBIT();
		buildBIT();
		processQueries();
		close();
	}

	public void close() {
		mOut.flush();
		mOut.close();
	}

	public static void main(String[] args) {
		AnuGCDBIT mSol = new AnuGCDBIT();
		mSol.solve();
	}
}
