package jdd.bdd;


import org.junit.Test;
import static org.junit.Assert.*;

public class TestCache {
	@Test public void testThree() {
		Cache cache = new Cache(200, 3);

		cache.insert3((byte)2, 1,2,3);
		assertEquals("lookup 3", 3, cache.lookup3((byte) 2,1,2));

		cache.insert3((byte)2, 1,2,5);
		assertEquals("lookup overwritten with 5", 5, cache.lookup3((byte) 2,1,2));
		assertEquals("non-existing entry 1", -1, cache.lookup3((byte) 1,1,2));
		assertEquals("non-existing entry 2", -1, cache.lookup3((byte) 2,2,2));
		assertEquals("non-existing entry 3", -1, cache.lookup3((byte) 2,2,1));
	}


	@Test public void testTwo() {
		Cache cache = new Cache(200, 2);

		cache.insert2((byte)2, 1,3);
		assertEquals("lookup 3", 3, cache.lookup2((byte) 2,1));

		cache.insert2((byte)2, 1,5);
		assertEquals("lookup overwritten with 5", 5, cache.lookup2((byte) 2,1));
		assertEquals("non-existing entry 1", -1, cache.lookup2((byte) 1,1));
		assertEquals("non-existing entry 2", -1, cache.lookup2((byte) 2,2));
	}

	@Test public void testOne() {
		Cache cache = new Cache(200, 1);

		cache.insert1(1,3);
		assertEquals("lookup 3", 3, cache.lookup1(1));

		cache.insert1(1,5);
		assertEquals("lookup overwritten with 5", 5, cache.lookup1(1));
		assertEquals("non-existing entry 1", -1, cache.lookup1(2));
		assertEquals("non-existing entry 2", -1, cache.lookup1(3));

	}
}
