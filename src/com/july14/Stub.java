package com.july14;

import java.io.PrintWriter;

import com.io.FasterScanner;

public class Stub {
	
	public FasterScanner mFScanner;
	public PrintWriter mOut;
	
	public Stub() {
		mFScanner = new FasterScanner();
		mOut = new PrintWriter(System.out);
	}
	
	public void solve() {
		
	}
	
	public void close() {
		mOut.flush();
		mOut.close();
	}
	
	public static void main(String [] args) {
		Stub mSol = new Stub();
		mSol.solve();
	}

}
