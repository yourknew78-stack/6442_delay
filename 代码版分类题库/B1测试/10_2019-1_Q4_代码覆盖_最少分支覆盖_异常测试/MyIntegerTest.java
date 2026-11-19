/* 简单读法
 * assertEquals 检查正常数字结果；assertThrows 检查非法字符是否抛出异常。
 * () -> MyInteger.parseInt("A") 是 JUnit 要求的“待执行动作”，不是立即执行的结果。
 */
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MyIntegerTest {

	@Test
	void test() {
		// TODO: Write a minimum set of test cases that are code complete 
		// for MyInteger.parseInt method.
		// Use assertEquals and assertThrows to test whether the method works as expected. 
		// 
		// All test cases should be included in this method. Do not create another method. 
		// Auto grader will not check the other methods created individually.
		
        assertEquals(123,MyInteger.parseInt("123")); // 正常返回与累加语句
        assertThrows(NumberFormatException.class,()->MyInteger.parseInt("A")); // >57 的 throw
        assertThrows(NumberFormatException.class,()->MyInteger.parseInt("/")); // <48 的 throw

	}

}
