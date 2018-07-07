package com.joeyorlando.ucf.eel4768.project1;

import java.io.File;

import com.joeyorlando.ucf.eel4768.CacheReplacementPolicy;

public class Context {
	
	private int kWay;
	private long cacheSizeBytes;
	private CacheReplacementPolicy replacementPolicy;
	private File traceFile;
	
	public Context(long cacheSizeBytes, int kWay, int replacementPolicy, String traceFile){
		this.cacheSizeBytes = cacheSizeBytes;
		this.kWay = kWay;
		if(replacementPolicy > 0){
			this.replacementPolicy = CacheReplacementPolicy.FIFO;
		}else this.replacementPolicy = CacheReplacementPolicy.LRU;
		this.traceFile = new File(traceFile);
	}
	
	public long getCacheSize(){
		return cacheSizeBytes;
	}
	
	public int getKWay(){
		return kWay;
	}
	
	public CacheReplacementPolicy getReplacementPolicy(){
		return replacementPolicy;
	}
	
	public File getTraceFile(){
		return traceFile;
	}
}