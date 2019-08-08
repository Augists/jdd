
package jdd.util;

import java.io.*;

// DOT TYPE ARE:
// ps ps2 hpgl pcl mif pic gd gd2 gif jpg jpeg png wbmp ismap imap cmap vrml
// vtx mp fig svg svgz dia dot canon plain plain-ext xdot

/** simple Dot-class that organizes calls to AT&T DOT */

public class Dot {
	// uses in setType
	public static final int TYPE_EPS = 0, TYPE_PNG = 1, TYPE_DOT = 2, TYPE_FIG = 3;
	public static final int TYPE_GIF = 4, TYPE_JPG = 5;

	private static final String DOT_COMMAND = "dot";
	private static final String [] DOT_TYPES = { "ps", "png", "dot", "fig", "gif", "jpg" };

	private static Runtime rt = Runtime.getRuntime();
	private static int dot_type = TYPE_PNG; /** output type */
	private static boolean run_dot = true;	/** should we actually execute dot or not? */
	private static boolean remove_dot_file = true;	/** remove the dot file after we are done??*/


	/**
	 * Create a DOT file from a String
	 */
	public static String showString(String file, String string)  {
		try {
			if (FileUtility.invalidFilename(file)) {
				System.err.println("[Dot] The filename '" + file + "' is invalid.");
				System.err.println("[Dot] Maybe it contains characters we don't like?");
				return null;
			}
			FileOutputStream fs = new FileOutputStream (file);
			fs.write(string.getBytes() );
			fs.flush();
			fs.close();
			return showDot(file);
		} catch(IOException exx) {
			JDDConsole.out.println("Unable to save graph to the file "+ file + "\nReason: " + exx.toString() );
		}
		return null;
	}

	/**
	 * Run DOT on this file to produce an image.
	 *
	 * <p>
	 * NOTE: unless you call Dot.setRemoveDotFile(false), the file will be REMOVED
	 *
	 * @see #setRemoveDotFile
	 */
	public static String showDot(String infile) {

		// first, check for shell characters
		if (FileUtility.invalidFilename(infile)) {
			System.err.println("[Dot] The filename '" + infile + "' is invalid.");
			System.err.println("[Dot] Maybe it contains characters we don't like?");
			return null;
		}

		try {
			String outfile = infile + "." + DOT_TYPES[dot_type];
			if (run_dot) {
				String [] cmd = new String [] {DOT_COMMAND, "-T", DOT_TYPES[dot_type], infile, "-o", outfile};
				Process p = rt.exec(cmd);
				p.waitFor();
			}

			if (remove_dot_file) {
				FileUtility.delete(infile);
			}
			return outfile;
		} catch (IOException exx) {
			JDDConsole.out.println("Unable to run DOT on " + infile + "\nReason: " + exx.toString());
		} catch (InterruptedException exx) {
			JDDConsole.out.println("DOT interrupted when processing " + infile + "\nReason: " + exx.toString());
		}
		return null;
	}

	public static boolean scalable() {
		return (dot_type == TYPE_DOT) || (dot_type == TYPE_EPS) || (dot_type == TYPE_FIG);
	}
	public static void setType(int t) { dot_type = t; }
	public static void setExecuteDot(boolean b) { run_dot = b; }
	public static void setRemoveDotFile(boolean b) { remove_dot_file = b; }

}

