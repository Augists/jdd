package jdd.util;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestAllocator {

   @Test public void testStats() {

		assertEquals("No allocation at begining",
			Allocator.getStatsCount(Allocator.TYPE_INT), 0);

		Allocator.allocateIntArray(10);
		Allocator.allocateIntArray(12);
		Allocator.allocateIntArray(8);
		Allocator.showStats();

		assertEquals("int allocator stats: count",
			Allocator.getStatsCount(Allocator.TYPE_INT), 3);
		assertEquals("int allocator stats: size",
			Allocator.getStatsTotal(Allocator.TYPE_INT), 30);
		assertEquals("int allocator stats: max",
			Allocator.getStatsMax(Allocator.TYPE_INT), 12);

			assertEquals("int allocator stats: total bytes",
			Allocator.getStatsTotalBytes(), 30 * 4);
    }


}
