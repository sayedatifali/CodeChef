package com.sept14;

import java.io.PrintWriter;

public class Test {
	
	public FasterScanner mFScanner;
	public PrintWriter mOut;
	
	public Test() {
		mFScanner = new FasterScanner();
		mOut = new PrintWriter(System.out);
	}
	
	public void solve() {
		int N;
		
		
	}
	
	public void flush() {
		mOut.flush();
	}
	
	public void close() {
		mOut.close();
	}
	
	public static void main(String [] args) {
		Test mSol = new Test();
		mSol.solve();
		mSol.flush();
		mSol.close();
	}

}
