package org.bravo.gaia.commons.money;

import java.math.BigDecimal;

/**
 * 检查数学运算中的溢出
 *
 * @author lijian  from google guava-libraries
 * @version 2021/07/13
 */

public class CheckOverflow {
	
	static final BigDecimal MAX_LONG = new BigDecimal(Long.MAX_VALUE);
	static final BigDecimal MIN_LONG = new BigDecimal(Long.MIN_VALUE);
	
	/**
	 * 溢出异常抛出
	 * @param b
	 */
	private static void checkNoOverflow(boolean b) {
		if (!b) {
			throw new ArithmeticException("overflow");
		}
	}
	
	/**
	 * 检查decimal是否溢出 long
	 * @param a
	 * @return long值
	 */
	public static long bigDecimalChecked(BigDecimal a) {
		if (a.compareTo(MAX_LONG) > 0 || a.compareTo(MIN_LONG) < 0){
			checkNoOverflow(false);
		}
		long result = a.longValue();
		return result;
	}

	
	
	/**
	 * 带溢出检查的double乘法 , 注意此方法最后检查是否超过Long
	 * @param a
	 * @param cent
	 * @return
	 */
	public static double doubleCheckedMultiply(double a, long cent) {
		double result = a * cent;
		if (result > Long.MAX_VALUE || result < Long.MIN_VALUE) {
			checkNoOverflow(false);
		}
		
		return result;
	}


	
	
	/**
	 * 带溢出检查的int加法 
	 * @param a
	 * @param b
	 * @return
	 */
	public static int intCheckedAdd(int a, int b) {
	    long result = (long) a + b;
	    checkNoOverflow(result == (int) result);
	    return (int) result;
	  }

	
	/**
	 * 带溢出检查的int减法 
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static int intCheckedSubtract(int a, int b) {
		long result = (long) a - b;
		checkNoOverflow(result == (int) result);
		return (int)result;
		
	}

	/**
	 * 带溢出检查的int乘法 
	 * @param a
	 * @param b
	 * @return
	 */
	public static int intCheckedMultiply(int a, int b) {
	    long result = (long) a * b;
	    checkNoOverflow(result == (int) result);
	    return (int) result;
	  }
	
	/**
	 * 带溢出检查的long加法 
	 * @param a
	 * @param b
	 * @return
	 */
	public static long longCheckedAdd(long a, long b) {
	    long result = a + b;
	    checkNoOverflow((a ^ b) < 0 | (a ^ result) >= 0);
	    return result;
	  }
	
	/**
	 * 带溢出检查的long减法 
	 * @param a
	 * @param b
	 * @return
	 */
	public static long longCheckedSubtract(long a, long b) {
	    long result = a - b;
	    checkNoOverflow((a ^ b) >= 0 | (a ^ result) >= 0);
	    return result;
	  }
	
	/**
	 * 带溢出检查的long乘法 
	 * @param a
	 * @param b
	 * @return
	 */
	public static long longCheckedMultiply(long a, long b) {
	    // Hacker's Delight, Section 2-12
	    int leadingZeros = Long.numberOfLeadingZeros(a) + Long.numberOfLeadingZeros(~a)
	        + Long.numberOfLeadingZeros(b) + Long.numberOfLeadingZeros(~b);
	    /*
	     * If leadingZeros > Long.SIZE + 1 it's definitely fine, if it's < Long.SIZE it's definitely
	     * bad. We do the leadingZeros check to avoid the division below if at all possible.
	     *
	     * Otherwise, if b == Long.MIN_VALUE, then the only allowed values of a are 0 and 1. We take
	     * care of all a < 0 with their own check, because in particular, the case a == -1 will
	     * incorrectly pass the division check below.
	     *
	     * In all other cases, we check that either a is 0 or the result is consistent with division.
	     */
	    if (leadingZeros > Long.SIZE + 1) {
	      return a * b;
	    }
	    checkNoOverflow(leadingZeros >= Long.SIZE);
	    checkNoOverflow(a >= 0 | b != Long.MIN_VALUE);
	    long result = a * b;
	    checkNoOverflow(a == 0 || result / a == b);
	    return result;
	  }
	
	

}
