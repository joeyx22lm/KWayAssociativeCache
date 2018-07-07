package com.joeyorlando.ucf.eel4768.project1;

import org.junit.Assert;
import org.junit.Test;

import com.joeyorlando.Log;
import com.joeyorlando.ucf.eel4768.Cache;
import com.joeyorlando.ucf.eel4768.CacheReplacementPolicy;
import com.joeyorlando.ucf.eel4768.project1.MainActivity;

/*
 * Example k-way problems and answers courtesy of 
 * https://cseweb.ucsd.edu/classes/sp09/cse141/Slides/10_Caches_detail.pdf
 */
public class MainActivityTest {
	
	// Allowed difference due to floating point inprecision.
	private static final double fpEpsilon = 0.00001;
	
	// Allowed difference according to project specification.
	private static final double epsilon = 0.01;

	@Test
	public void run8WayLRUMiniFETest(){
		MainActivity ma = new MainActivity("32768", "8", "0", "MINIFE.t");
		ma.main(new String[]{"32768", "8", "0", "MINIFE.t"});
		
		double writeMissRatio = 1-((double)ma.runCache.write_hit / ((double)ma.runCache.write_hit+(double)ma.runCache.write_miss));
		Assert.assertTrue((writeMissRatio - 0.035244) <= epsilon);
		
		double readMissRatio = 1-((double)ma.runCache.read_hit / ((double)ma.runCache.read_hit+(double)ma.runCache.read_miss));
		Assert.assertTrue(writeMissRatio - 0.069830 <= epsilon);
		
		double totalRatio = 1-(((double)ma.runCache.read_hit+(double)ma.runCache.write_hit)/(((double)ma.runCache.write_miss+(double)ma.runCache.write_hit)+((double)ma.runCache.read_miss+(double)ma.runCache.read_hit)));
		Assert.assertTrue(totalRatio - 0.065413 <= epsilon);
	}
	
	@Test
	public void run8WayFIFOMiniFETest(){
		MainActivity ma = new MainActivity("32768", "8", "1", "MINIFE.t");
		ma.main(new String[]{"32768", "8", "1", "MINIFE.t"});
		
		double writeMissRatio = 1 - ((double)ma.runCache.write_hit / ((double)ma.runCache.write_hit+(double)ma.runCache.write_miss));
		Assert.assertTrue(writeMissRatio - 0.036739 <= epsilon);
		
		double readMissRatio = 1 - ((double)ma.runCache.read_hit / ((double)ma.runCache.read_hit+(double)ma.runCache.read_miss));
		Assert.assertTrue(writeMissRatio - 0.072263 <= epsilon);
		
		double totalRatio = 1 - (((double)ma.runCache.read_hit+(double)ma.runCache.write_hit)/(((double)ma.runCache.write_miss+(double)ma.runCache.write_hit)+((double)ma.runCache.read_miss+(double)ma.runCache.read_hit)));
		Assert.assertTrue(totalRatio - 0.067624 <= epsilon);
	}
	
	@Test
	public void run2WayLRUMiniFETest(){
		MainActivity ma = new MainActivity("32768", "2", "0", "MINIFE.t");
		ma.main(new String[]{"32768", "2", "0", "MINIFE.t"});
		
		double writeMissRatio = 1 - ((double)ma.runCache.write_hit / ((double)ma.runCache.write_hit+(double)ma.runCache.write_miss));
		Assert.assertTrue(writeMissRatio - 0.036865 <= epsilon);
		
		double readMissRatio = 1 - ((double)ma.runCache.read_hit / ((double)ma.runCache.read_hit+(double)ma.runCache.read_miss));
		Assert.assertTrue(writeMissRatio - 0.070567 <= epsilon);
		
		double totalRatio = 1 - (((double)ma.runCache.read_hit+(double)ma.runCache.write_hit)/(((double)ma.runCache.write_miss+(double)ma.runCache.write_hit)+((double)ma.runCache.read_miss+(double)ma.runCache.read_hit)));
		Assert.assertTrue(totalRatio - 0.066166 <= epsilon);
	}
	
	@Test
	public void testMemorySizeCalculations() {
		// Test w/ 32Kb cache, 4-Way Associative, LRU Policy, example file.
		Long cacheSize = new Long(32 * 1024);		// 32Kb
		long lineSize = 16;							// 16B line size
		long addressSize = 32;						// 32-bit addresses
		Long associativity = new Long(4);						// 4-Way Associativity

		Assert.assertEquals(2048, cacheSize/lineSize, fpEpsilon);
		MainActivity proc = new MainActivity(cacheSize.toString(), associativity.toString(), "0", "example.file");

		// Initial Greetings.
		Log.info("Cache Size: " + proc.ctx.getCacheSize() + "B");
		Log.info("Associativity: " + proc.ctx.getKWay() + "-Way");
		Log.info("Replacement Policy: " + (proc.ctx.getReplacementPolicy() == CacheReplacementPolicy.LRU ? "LRU" : "FIFO"));
		Log.info("Trace File: " + proc.ctx.getTraceFile().getName());
		
		// Build the cache.
		if(proc.ctx.getReplacementPolicy() == CacheReplacementPolicy.LRU){
			proc.runCache = new Cache(proc.ctx.getCacheSize(), lineSize, proc.ctx.getKWay(), addressSize, CacheReplacementPolicy.FIFO);
		}
		
		Assert.assertEquals(9, proc.runCache.getNumIndexBits(), fpEpsilon);
		Assert.assertEquals(4, proc.runCache.getNumOffsetBits(), fpEpsilon);
		Assert.assertEquals(19, proc.runCache.getNumTagBits(proc.runCache.getNumIndexBits(), proc.runCache.getNumOffsetBits(), addressSize), fpEpsilon);
	}

	
}
