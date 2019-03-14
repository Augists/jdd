
package jdd.util.math;

import jdd.util.*;
import jdd.bdd.*;

import java.util.*;

/**
 * some common simple operations involving numbers are gathered here
 *
 */

public class Digits {

	public static int log2_ceil(int x) {
		int ret = 1;
		while( (1L << ret) < x)
			ret++;
		return ret;
	}


	public static int closest_log2(int x) {
		int lg2 = log2_ceil(x);
		long d1 = (1L << lg2) - x;
		long d2 = x - (1L << (lg2 -1));
		return d1 < d2 ? lg2 : lg2-1;
	}



	/** given a set of n elements, return the number of unique pairs */
	public static int maxUniquePairs(int n) {
		if(n == 0 || n == 1) return 0;
		if(n == 2) return 1;
		return (n-1) + maxUniquePairs(n-1);
	}


	/** return a number [0..1] as a percent value xx.yy */
	public static double getPercent(double x) {
		return getWithDecimals(100.0 * x, 2);
	}

	/** get number x with n decimals */
	public static double getWithDecimals(double x, int n) {
		double dec = Math.pow(10, n);
		return Math.round( x * dec) / dec;
	}


	/** print the number <tt>n</tt> in the format xxx K/G/M */
	public static void printNumber(long n) { printNumberWith(n, 1000); }

	/** same as printNumber, bit  this time, K = 1024, not 1000 */
	public static void printNumber1024(long n) { 	printNumberWith(n, 1024);	}

	/** internal function to write numbers */
	private static void printNumberWith(long n, long k_) {
		final long m_ =  k_ * k_;
		final long g_ =  k_ * m_;
		final long t_ =  k_ * g_;

		if(n > t_)  JDDConsole.out.print(numberDivided(n,t_)  +"T ");	else
		if(n > g_)  JDDConsole.out.print(numberDivided(n,g_)  +"G ");	else
		if(n > m_)  JDDConsole.out.print(numberDivided(n,m_)  +"M ");	else
		if(n > k_)  JDDConsole.out.print(numberDivided(n,k_)  +"k ");	else
		JDDConsole.out.print(n + " ");
	}


	/**
	 * return <tt>n'</tt> in as  n' = n * div, with two decimals
	 */
	public static double numberDivided(double n, long div) {
		int i = (int) (100 * ( n + div / 200) / div); // 2 for rounding, 100 for two decimals
		return ((double)i) / 100;
	}
}
