package com.joeyorlando.ucf.eel4768;

import com.joeyorlando.RestrictedBitOperation;

public class MemoryAddress {
	
	// Given attributes.
	public long addressSize;
	public long offsetSize;
	public long indexSize;
	public long tagSize;
	public long address;
	
	// Calculated attributes.
	public Long offset;
	public Long set;
	public Long tag;

	public MemoryAddress(long addressSize, long offsetSize, long indexSize, long address){
		this.addressSize = addressSize;
		this.offsetSize = offsetSize;
		this.indexSize = indexSize;
		this.tagSize = (addressSize-offsetSize-indexSize);
		this.address = address;
	}

	public MemoryAddress(long addressSize, Double offsetSize, Double indexSize, long address){
		this(addressSize, offsetSize.longValue(), indexSize.longValue(), address);
	}
	
	public Long getOffset(){
		if(offset == null){
			offset = RestrictedBitOperation.unsignedRightShift(RestrictedBitOperation.restrictedLeftShift(address, addressSize, (addressSize-offsetSize)), (addressSize-offsetSize));
		}
		return offset;
	}
	
	public Long getSet(){
		if(set == null){
			set = RestrictedBitOperation.unsignedRightShift(RestrictedBitOperation.restrictedLeftShift(address, addressSize, (addressSize-offsetSize-indexSize)), (addressSize-indexSize));
		}
		return set;
	}
	
	public Long getTag(){
		if(tag == null){
			tag = RestrictedBitOperation.unsignedRightShift(address, offsetSize+indexSize);
		}
		return tag;
	}
	
	@Override
	public String toString(){
		return("("+
			"offset["+offsetSize+"]: " + Long.toBinaryString(getOffset())+", "+
			"index["+indexSize+"]: " + Long.toBinaryString(getSet())+", "+
			"tag["+tagSize+"]: " + Long.toBinaryString(getTag())+")"
		);
	}
	
	@Override
	public boolean equals(Object o){
		if(o == this) return true;
		if(o == null) return false;
		MemoryAddress mo = (MemoryAddress)o;
		return(
			this.getSet().equals(mo.getSet()) &&
			this.getTag().equals(mo.getTag())
		);
	}

}
