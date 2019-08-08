
package jdd.util.jre;

import jdd.util.*;
import jdd.util.math.*;

import java.util.*;


/**
 * print some info about the JRE...
 */
public class JREInfo {
	public static Runtime rt = Runtime.getRuntime();

	public static long usedMemory() { return  rt.totalMemory() - rt.freeMemory(); }
	public static long totalMemory() { return  rt.totalMemory(); }
	public static long freeMemory() { return rt.freeMemory(); }
	public static long maxMemory() { return rt.maxMemory(); }


	/** print out some info about the system and JVM etc. */
	public static void show() {
		Properties prop = System.getProperties();

		JDDConsole.out.println("Using JDD version " + jdd.Version.VERSION +" on " + (new Date()).toString() + "\n");
		JDDConsole.out.print("Using " + prop.getProperty("java.vendor") + " JRE " + prop.getProperty("java.version"));
		String jit = prop.getProperty("java.compiler");
		if(jit != null)  JDDConsole.out.print(", " + jit +  " JIT in");
		JDDConsole.out.println(" "+ prop.getProperty("java.vm.name") );

		JDDConsole.out.println("OS " + prop.getProperty("os.name") + " on " + rt.availableProcessors() + " " + prop.getProperty("os.arch") + " CPU(s)");
		JDDConsole.out.printf("JRE memory: total=%s, reserved=%s\n",
			Digits.prettify1024(rt.maxMemory()),  Digits.prettify1024(usedMemory()));
	}

	public static void main(String [] args) {
		show();
	}
}



