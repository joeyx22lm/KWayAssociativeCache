package com.joeyorlando.ucf.eel4768.project1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;

import com.joeyorlando.Log;
import com.joeyorlando.ucf.eel4768.Cache;
import com.joeyorlando.ucf.eel4768.CacheReplacementPolicy;

public class MainActivity {
	
	public static final long DEFAULT_ADDRESS_SIZE = 64;
	public static final int blockSizeBytes = 64;
	
	public Context ctx;
	
	public static Cache runCache = null;
	
	public MainActivity(String cacheSizeBytes, String associativityPolicy, String replacementPolicy, String traceFile){
		this.ctx = new Context(Long.parseLong(cacheSizeBytes), Integer.parseInt(associativityPolicy), Integer.parseInt(replacementPolicy), traceFile);
	}
	
	public void init(){
		// Initial Greetings.
		Log.info("Cache Size: " + ctx.getCacheSize() + "B");
		Log.info("Associativity: " + ctx.getKWay() + "-Way");
		Log.info("Replacement Policy: " + (ctx.getReplacementPolicy() == CacheReplacementPolicy.LRU ? "LRU" : "FIFO"));
		Log.info("Trace File: " + ctx.getTraceFile().getName());
		
		// Build the cache.
		runCache = new Cache(ctx.getCacheSize(), blockSizeBytes, ctx.getKWay(), DEFAULT_ADDRESS_SIZE, ctx.getReplacementPolicy());
		
		// Read trace from file.
		Exception fault = null;
		try(FileReader fr = new FileReader(ctx.getTraceFile())){
			try(BufferedReader buffer = new BufferedReader(fr)){
				String line = null;
				while ((line = buffer.readLine()) != null) {
					BigInteger addr = new BigInteger(line.substring(4), 16);
					if("R".equalsIgnoreCase(line.substring(0, 1))){
						String debug = "Miss";
						if(runCache.read(addr.longValue())){
							debug = "Hit";
						}
						Log.debug("Read " + debug + ": " + addr.longValue());
					}else{
						String debug = "Miss";
						if(runCache.write(addr.longValue())){
							debug = "Hit";
						}
						Log.debug("Write " + debug + ": " + addr.longValue());
					}
				}
			}catch(IOException e){
				fault = e;
			}
		}catch(IOException e){
			fault = e;
		}
		
		if(fault == null){
			double writeTotal = (runCache.write_miss+runCache.write_hit);
			double readTotal = (runCache.read_miss+runCache.read_hit);
			Log.info("Write Misses: " + (1-(runCache.write_hit / writeTotal)));
			Log.info("Read Misses: " + (1-(runCache.read_hit / readTotal)));		
			Log.info("Total Miss Ratio: " + (1-((runCache.write_hit + runCache.read_hit) / (readTotal+writeTotal))));
		}else Log.error("An unexpected error occurred: " + fault.getMessage() +"\nTrace Path: " + ctx.getTraceFile().getAbsolutePath(), 1);
	}
	
	public static void main(String[] args){
		
		if(args == null || args.length != 4){
			Log.error("Invalid input arguments given.");
			Log.error("Syntax: ./SIM <CACHE_SIZE> <ASSOC> <REPLACEMENT> <TRACE_FILE>", 1);
		}
		
		new MainActivity(args[0], args[1], args[2], args[3]).init();
	}
	
}