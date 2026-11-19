package utils;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * TODO: write a minimum number of JUnit test cases (assertEquals) for
 * {@code MyStringUtil.isMixedCase} that is branch complete.
 */
public class MyStringUtilTest {

	@Test
	public void test() {
        //start your code
        assertEquals(false,MyStringUtil.isMixedCase(null)); // 第一个提前返回
        assertEquals(false,MyStringUtil.isMixedCase(""));   // 第二个提前返回
        assertEquals(false,MyStringUtil.isMixedCase("a"));  // 最后&&：左false
        assertEquals(false,MyStringUtil.isMixedCase("A"));  // 最后&&：左true右false
        assertEquals(true,MyStringUtil.isMixedCase("aA"));  // 最后&&：两边true
        assertEquals(true,MyStringUtil.isMixedCase("Aa0")); // 第三轮循环开头提前return true

        //end your code
	}

}
