import static org.junit.Assert.*;

import org.junit.Test;


public class testStringToLong {
	public static final long SOLU1 = 123;
	public static final long SOLU2 = -123;
	public static final long SOLU3 = 0;
	
	@Test
	public void TestEmptyInput() {
		assertEquals(SOLU3, StringToLong.stringToLong(""));
		assertEquals(SOLU3, StringToLong.stringToLong("    "));
	}
	
	@Test
	public void TestRegularInput() {
		assertEquals(SOLU1, StringToLong.stringToLong("123"));
	}
	
	@Test
	public void TestRegularInputWithSpace() {
		assertEquals(SOLU1, StringToLong.stringToLong("   123"));
	}

	@Test
	public void TestSigns() {
		assertEquals(SOLU1, StringToLong.stringToLong("+123"));
		assertEquals(SOLU2, StringToLong.stringToLong("-123"));
	}
	
	@Test
	public void TestZeroWithSigns() {
		assertEquals(SOLU3, StringToLong.stringToLong("+0"));
		assertEquals(SOLU3, StringToLong.stringToLong("-0"));
	}
	
	@Test
	public void testAroundMax() {
		long l = new Long("9223372036854775806");
		assertEquals(Long.MAX_VALUE, StringToLong.stringToLong("9223372036854775808"));
		assertEquals(Long.MAX_VALUE, StringToLong.stringToLong("9223372036854775807"));
		assertEquals(Long.MAX_VALUE, StringToLong.stringToLong("92233720368547758080"));
		assertEquals(l, StringToLong.stringToLong("9223372036854775806"));
	}
	
	@Test
	public void testAroundMin() {
		long l = new Long("-9223372036854775807");
		assertEquals(Long.MIN_VALUE, StringToLong.stringToLong("-9223372036854775808"));
		assertEquals(Long.MIN_VALUE, StringToLong.stringToLong("-9223372036854775809"));
		assertEquals(Long.MIN_VALUE, StringToLong.stringToLong("-92233720368547758080"));
		assertEquals(l, StringToLong.stringToLong("-9223372036854775807"));
	}
	
	@Test(expected=NumberFormatException.class)
	public void TestSimpleBadInput() {
		StringToLong.stringToLong("abc123");
	}
	
	@Test(expected=NumberFormatException.class)
	public void TestMixedBadImput() {
		StringToLong.stringToLong("   +1ads23fas+++");
	}
	
	@Test(expected=NumberFormatException.class)
	public void TestMixedBadInput2() {
		StringToLong.stringToLong("3924x");
	}
}
