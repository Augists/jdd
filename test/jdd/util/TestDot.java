
package jdd.util;

import java.io.*;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class TestDot {
	private static final String TESTGRAPH = "graph { a -- b ; a -- c; }";


	@Before public void setup(){
		// Another CI fix, should not be needed
		File f = new File("build");
		if(! f.exists()) {
			f.mkdirs();
		}
	}

	@Test public void testBadFilename() {
		String []names = new String[] { "", "/idontexist/cantwritehere", "a|b", "../mamma-mia!"};

		Dot.setExecuteDot(false);
		for(String name : names){
			String filename = Dot.showString(name, "{}");
			assertEquals("bad filename ''" + name + "''", null, filename);
		}
	}

	@Test public void testExtension() {
		Dot.setExecuteDot(false);

		Dot.setType(Dot.TYPE_EPS);
		String filename1 = Dot.showString("build/graph", "{}");
		assertEquals("EPS filename", "build/graph.ps", filename1);

		Dot.setType(Dot.TYPE_PNG);
		String filename2 = Dot.showString("build/graph", "{}");
		assertEquals("PNG filename", "build/graph.png", filename2);
	}

	@Test public void testExecute() {
		Dot.setExecuteDot(true);
		Dot.setType(Dot.TYPE_PNG);
		Dot.setRemoveDotFile(false);

		String filename = Dot.showString("build/graph", TESTGRAPH);
		assertEquals("PNG filename", "build/graph.png", filename);

		File f = new File(filename);
		assertEquals("created PNG", true, f.exists());
	}
}
