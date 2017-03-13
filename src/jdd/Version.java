package jdd;

/**
 * The Version class contains the current version of the JDD library.
 *
 * If you plan to use the latest features in your programs, you should first check
 * with the build version here to ensure that jdd.jar is not outdated
 *
 */

public class Version {
	public static final int build = 108;
	public static final String date = "2017-03";

	public static void main(String[] ignored) {
		System.out.println("JDD build " + build);
	}
}
