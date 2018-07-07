package com.joeyorlando.ucf.eel4768.project1;

import org.junit.Assert;
import org.junit.Test;

import com.joeyorlando.ucf.eel4768.CacheReplacementPolicy;
import com.joeyorlando.ucf.eel4768.project1.Context;

public class ContextTest {

	@Test
	public void testFIFO() {
		Context tstCtx = new Context(1024L, 2, 1, "example.file");
		Assert.assertTrue(tstCtx.getReplacementPolicy() == CacheReplacementPolicy.FIFO);
	}

	@Test
	public void testLRU() {
		Context tstCtx = new Context(1024L, 2, 0, "example.file");
		Assert.assertTrue(tstCtx.getReplacementPolicy() == CacheReplacementPolicy.LRU);
	}
	
}
