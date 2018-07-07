package com.joeyorlando.queue;

import java.util.ArrayList;
import java.util.List;

public abstract class Queue<T> {
	
	protected int fixed_size;
	
	protected List<T> queue = new ArrayList<>();
	
	public Queue(int fixed_size){
		this.fixed_size = fixed_size;
	}
	
	protected void trimQueue(){
		while(queue.size() > fixed_size) queue.remove(queue.size()-1);
	}
	
	public abstract T get(T obj);
	
	public abstract boolean set(T obj);
	
}
