package com.joeyorlando;

import org.junit.Assert;
import org.junit.Test;

public class RestrictedBitOperationTest {

	@Test
	public void testIdentityString(){
		Long smallTest = RestrictedBitOperation.getIdentityString(3);
		Long mediumTest = RestrictedBitOperation.getIdentityString(20);
		Long largeTest = RestrictedBitOperation.getIdentityString(54);
		
		// Check small is proper length of 1's.
		Assert.assertEquals(3, Long.toBinaryString(smallTest).chars().filter(c -> c == '1').count());
		Assert.assertEquals(0, Long.toBinaryString(smallTest).chars().filter(c -> c != '1').count());
		
		// Check medium is proper length of 1's.
		Assert.assertEquals(20, Long.toBinaryString(mediumTest).chars().filter(c -> c == '1').count());
		Assert.assertEquals(0, Long.toBinaryString(mediumTest).chars().filter(c -> c != '1').count());
		
		// Check large is proper length of 1's.
		Assert.assertEquals(54, Long.toBinaryString(largeTest).chars().filter(c -> c == '1').count());
		Assert.assertEquals(0, Long.toBinaryString(largeTest).chars().filter(c -> c != '1').count());
	}
	@Test
	public void testRestrictedLeftShift(){
		// 0x070L == 112 == 1110000
		long addr = 0x070L;
		Assert.assertTrue("111000000000".equals(Long.toBinaryString(RestrictedBitOperation.restrictedLeftShift(addr, 12, 5))));
		Assert.assertTrue("11100000000".equals(Long.toBinaryString(RestrictedBitOperation.restrictedLeftShift(addr, 12, 4))));
		Assert.assertTrue("11000000000".equals(Long.toBinaryString(RestrictedBitOperation.restrictedLeftShift(addr, 11, 5))));
	}
	
	@Test
	public void testRestrictedRightShift(){
		// 0x070L == 112 == 1110000
		long addr = 0x070L;
		Assert.assertTrue("1110".equals(Long.toBinaryString(RestrictedBitOperation.unsignedRightShift(addr, 3))));
		Assert.assertTrue("111".equals(Long.toBinaryString(RestrictedBitOperation.unsignedRightShift(addr, 4))));
	}

}
