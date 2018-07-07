package com.joeyorlando.ucf.eel4768;

import org.junit.Assert;
import org.junit.Test;

public class CacheTest {

	private static final double epsilon = 0.00001;
	
	@Test
	public void testLogBaseTwo(){
		Assert.assertEquals(9, Cache.logBaseTwo(512), epsilon);
	}
	
	@Test
	public void testNumIndexBits(){
		Assert.assertEquals(9, Cache.getNumIndexBits(512), epsilon);
	}
	
	@Test
	public void testOffsetBits(){
		long cacheLineSize = 32;	// 32 bytes per line
		Assert.assertEquals(5.0, Cache.getNumOffsetBits(cacheLineSize), epsilon);
	}
	
	@Test
	public void testIndexBits(){
		long cacheLines = 1024;		// 1024 cache lines
		Assert.assertEquals(10.0, Cache.getNumIndexBits(cacheLines), epsilon);
	}
	
	@Test
	public void testTagBits(){
		long cacheLines = 1024;		// 1024 cache lines
		long cacheLineSize = 32;	// 32 bytes per line
		long addressSize = 32;
		Assert.assertEquals(17, Cache.getNumTagBits(Cache.getNumOffsetBits(cacheLineSize), Cache.getNumIndexBits(cacheLines), addressSize), epsilon);
	}
	
	@Test
	public void testFullWithMemoryAddress(){
		long cacheSize = (32 * 1024);		// 32Kb
		long cacheLines = 2048;				// 1024 Lines
		long addressSize = 32;				// 32-bit addresses
		long associativity = 4;				// 4-Way Associativity
		
		long lineSize = (cacheSize / cacheLines);
		Assert.assertEquals(16, lineSize);
		Assert.assertEquals(4, Cache.getNumOffsetBits(lineSize), epsilon);
		Assert.assertEquals(9.0, Cache.getNumIndexBits(cacheLines/associativity), epsilon);
		Assert.assertEquals(19, Cache.getNumTagBits(Cache.getNumOffsetBits(lineSize), Cache.getNumIndexBits(cacheLines/associativity), addressSize), epsilon);
	
		long address = 0x7ad538aL;
		MemoryAddress tst = new MemoryAddress(addressSize, new Double(Cache.getNumOffsetBits(lineSize)).longValue(), new Double(Cache.getNumIndexBits(cacheLines/associativity)).longValue(), address);
		
		Assert.assertEquals(4, tst.offsetSize);
		Assert.assertEquals(9, tst.indexSize);
		Assert.assertEquals(19, tst.tagSize);
		Assert.assertEquals(0b0111101011010101001110001010, address);
		Assert.assertEquals(0b1010, tst.getOffset().longValue());
		Assert.assertEquals(0b011110101101010, tst.getTag().longValue());
		Assert.assertEquals(0b100111000, tst.getSet().longValue());
	}
	
	@Test
	public void testActualMemoryAddress(){
		long cacheSize = (32 * 1024);		// 32Kb
		long addressSize = 64;				// 32-bit addresses
		int associativity = 2;				// 4-Way Associativity
		long blockSize = 64;				// 64B blocks
		
		Cache c = new Cache(cacheSize, blockSize, associativity, addressSize, CacheReplacementPolicy.FIFO);
		
		// Test known address   140734421803704L == 11111111111111101001001001110000010001010111000
		Long address = 140734421803704L;
		Assert.assertEquals(0b11111111111111101001001001110000010001010111000L, address.longValue());
		MemoryAddress tst = new MemoryAddress(addressSize, c.getNumOffsetBits(), c.getNumIndexBits(), address);
		
		Assert.assertEquals(0b111000L, tst.getOffset().longValue());
		Assert.assertEquals(0b10001010L, tst.getSet().longValue());
		Assert.assertEquals(0b111111111111111010010010011100000L, tst.getTag().longValue());
	}
	
	@Test
	public void testExampleCache(){
		// 32Kb cache, 4-Way associativity, 32B line size / block size. 40-bit addresses.
		Cache c = new Cache(32768, 32, 4, 40, CacheReplacementPolicy.FIFO);
		Assert.assertEquals(256, c.getSetLength(), 0.0001);
		Assert.assertEquals(27, c.getNumTagBits(), 0.0001);
		Assert.assertEquals(8, c.getNumIndexBits(), 0.0001);
		Assert.assertEquals(5, c.getNumOffsetBits(), 0.0001);
	}
	
	@Test
	public void testLectureExampleCache(){
		// 32Kb cache, 2-Way associativity, 64B line size / block size. 64-bit addresses.
		Cache c = new Cache(32768, 64, 2, 64, CacheReplacementPolicy.FIFO);
		Assert.assertEquals(256, c.getSetLength(), 0.0001);
		Assert.assertEquals(8, c.getNumIndexBits(), 0.0001);
		Assert.assertEquals(6, c.getNumOffsetBits(), 0.0001);
		
		// Test known address   140734421803704L == 11111111111111101001001001110000010001010111000
		Long address = 0x024564L;
		MemoryAddress tst = new MemoryAddress(64, c.getNumOffsetBits(), c.getNumIndexBits(), address);
		Assert.assertEquals(0x15, tst.getSet().longValue());
	}

	
}
 