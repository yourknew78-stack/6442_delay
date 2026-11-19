import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StringUtilTest {

	@Test
	void test() {
		//TODO: Write a minimum set of test cases that are branch complete 
		// to test StringUtil.collapseNewlines method via assertEquals for test cases.
		// 
		// All test cases should be included in this method. Do not create another method. 
		// Auto grader will not check the other methods created individually
		

        assertEquals("x",StringUtil.collapseNewlines("x")); // 长度<2 提前返回
        assertEquals("a\nb",StringUtil.collapseNewlines("a\n\nb")); // 普通字符、首个换行、重复换行

	}

}
