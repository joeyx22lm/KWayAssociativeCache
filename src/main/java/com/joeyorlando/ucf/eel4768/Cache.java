package com.joeyorlando.ucf.eel4768;

import java.util.HashMap;
import java.util.Map;

import com.joeyorlando.queue.FIFOQueue;
import com.joeyorlando.queue.LRUQueue;
import com.joeyorlando.queue.Queue;

public class Cache {
	
	private Map<Long, Queue<MemoryAddress>> cache = new HashMap<>();
	
	// Runtime variables.
	public int associativity;
	public long addressSize;
	public long cacheSizeBytes;
	public long blockSizeBytes;
	private CacheReplacementPolicy crp;
	
	// Calculated metrics.
	public int write_hit = 0;
	public int write_miss = 0;
	public int read_hit = 0;
	public int read_miss = 0;
	
	public Cache(long cacheSizeBytes, long blockSizeBytes, int associativity, long addressSize, CacheReplacementPolicy crp){
		this.cacheSizeBytes = cacheSizeBytes;
		this.blockSizeBytes = blockSizeBytes;
		this.associativity = associativity;
		this.addressSize = addressSize;
		this.crp = crp;
	}
	
	public static double logBaseTwo(double val){
		return Math.log(val) / Math.log(2);
	}
	
	public double getNumIndexBits(){
		return getNumIndexBits(getSetLength());
	}
	
	public static double getNumIndexBits(double setLength){
		return logBaseTwo(setLength);
	}

	public double getNumOffsetBits(){
		return getNumOffsetBits(this.blockSizeBytes);
	}
	
	public static double getNumOffsetBits(double blockSizeBytes){
		return logBaseTwo(blockSizeBytes);
	}
	
	public double getNumTagBits(){
		return getNumTagBits(getNumOffsetBits(), getNumIndexBits(), addressSize);
	}
	
	public static double getNumTagBits(double numOffsetBits, double numIndexBits, double addressSize){
		return addressSize-numIndexBits-numOffsetBits;
	}
	
	public Long getSetLength(){
		return (cacheSizeBytes / (blockSizeBytes * associativity));
	}
	
	public boolean read(Long addressIn){
		MemoryAddress addr = new MemoryAddress(addressSize, new Double(getNumOffsetBits()).longValue(), new Double(getNumIndexBits()).longValue(), addressIn);
		if(addr != null){
			long set = addr.getSet();
			if(cache.get(set) == null){
				// Requested set does not yet exist. Must be
				// the first access. Initialize new set.
				if(crp == CacheReplacementPolicy.FIFO){
					cache.put(set, new FIFOQueue(associativity));
				}else{
					cache.put(set, new LRUQueue(associativity));
				}
			}
			
			// Check to see if value already exists in cache.
			if(cache.get(set).get(addr) != null){
				// Already exists in cache.
				++read_hit;
			}else{
				// Doesn't already exist in cache.
				cache.get(set).set(addr);
				++read_miss;
			}
		}
		return false;
	}
	
	public boolean write(Long addressIn){
		MemoryAddress addr = new MemoryAddress(addressSize, new Double(getNumOffsetBits()).longValue(), new Double(getNumIndexBits()).longValue(), addressIn);
		if(addr != null){
			long set = addr.getSet();
			if(cache.get(set) == null){
				// Requested set does not yet exist. Must be
				// the first access. Initialize new set.
				if(crp == CacheReplacementPolicy.FIFO){
					cache.put(set, new FIFOQueue(associativity));
				}else{
					cache.put(set, new LRUQueue(associativity));
				}
			}
			
			// On write, verify value exists in cache.
			// Do not update LRU counter.
			if(cache.get(set).set(addr)){
				++write_hit;
			}else{
				++write_miss;
			}
		}
		return false;
	}
}