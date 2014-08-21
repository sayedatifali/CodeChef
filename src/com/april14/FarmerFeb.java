package com.april14;

import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * http://www.codechef.com/APRIL14/problems/POTATOES
 * 
 * @author doom
 * 
 */

public class FarmerFeb {

	public static int MAX = 2010;
	public static boolean sieve[] = new boolean[MAX];
	public static ArrayList<Integer> primes;
	
	public static void main(String [] args) {
		int T, n;
		int i, x, y, z;
		int val;
		FastScannerSlow fS = new FastScannerSlow();
		PrintWriter out = new PrintWriter(System.out);
		primes = new ArrayList<Integer>(1500);
		
		buildSieve();
		
		n = primes.size();
		T = fS.nextInt();
		
		while (T-- > 0) {
			x = fS.nextInt();
			y = fS.nextInt();
			z = x + y;
			val = 0;
			for (i = 0; i < n; i++) {
				val = primes.get(i);
				if (val > z)
					break;
			}
			out.println(val - z);
		}
		
		out.flush();
		out.close();
		
		
	}
	
	public static void buildSieve() {
		int i, j;
		
		sieve[0] = true;
		sieve[1] = true;
		
		for (i = 2; i < MAX; i++) {
			if (sieve[i])
				continue;
			
			j = i * 2;
			for (; j < MAX; j += i) {
				sieve[j] = true;
			}
			
			primes.add(i);
		}
//		
//		for (i = 0; i < primes.size(); i++)
//			System.out.print(primes.get(i) + " ");
	}
}
