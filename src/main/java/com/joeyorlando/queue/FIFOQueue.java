package com.joeyorlando.queue;

public class FIFOQueue<T> extends Queue<T> {
	
	public FIFOQueue(int fixed_size){
		super(fixed_size);
	}
	
	public T get(T obj){
		T found = null;
		for(T t : queue){
			if(t.equals(obj)) found = t;
		}
		
		// Verify queue is within fixed size boundary.
		// Then return findings.
		trimQueue();
		return found;
	}
	
	public boolean set(T obj){
		T found = null;
		for(T t : queue){
			if(t.equals(obj)) found = t;
		}
		
		if(found == null){
			// Lookup obj doesn't exist. Add to cache. 
			// Don't update LRU count.
			queue.add(0, obj);
		}
		
		// Verify queue is within fixed size boundary.
		// Then return hit/miss.
		trimQueue();
		return (found != null);
	}
	
}
