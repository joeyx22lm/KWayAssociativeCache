package com.joeyorlando;

public class RestrictedBitOperation {
	
	/**
	 * Replicate the overflow normally incurred by a small register. Sometimes it's nice
	 * working with small-sized numbers, as we can use overflow to our advantage when
	 * breaking apart binary numbers. Since long's are 64-bit numbers, it's hard to 
	 * overflow them. This operator replicates the overflow normally incurred by
	 * a register of <i>fixed_size</i> length.
	 * @param val binary number of type long
	 * @param fixed_size fixed register size
	 * @param shifts number of left shifts
	 * @return left-shifted (x shifts) binary long
	 */
	public static long restrictedLeftShift(long val, long fixed_size, long shifts){
		return (val << shifts) & getIdentityString(fixed_size);
	}
	
	/**
	 * Unsigned right shift.
	 * @param val binary number of type long
	 * @param shifts number of right shifts
	 * @return right-shifted (x shifts) binary long
	 */
	public static long unsignedRightShift(long address, long shifts){
		return (address >>> shifts);
	}
	
	/**
	 * f(4) => 1111
	 * f(7) => 1111111
	 * @param bits length
	 * @return binary string of all 1's, of length <i>bits</i>
	 */
	public static long getIdentityString(long bits){
		long out = 1;
		for(long i = 0;i<bits-1;i++){
			out = (out * 2) + 1;
		}
		return out;
	}
	
}
