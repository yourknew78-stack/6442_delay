/* 简单读法
 * 给 someMethod 传入不同参数，分别触发不同分支。
 * 预期值应从被测源码一步步计算；一个测试方法可以写多个断言，但不是只有一个测试用例。
 */
import org.junit.Test;

/**
 *
 * You are given a java class called Something, which has a method called
 * someMethod. Please implement a minimum number of test cases for testing `someMethod`
 * that are branch complete within `someMethod`. Write your test case(s) in test() method in
 * `SomethingTest.java`. You are not allowed to alter the signatures of the given
 * methods and the package structures of the given classes. Please upload the
 * `SomethingTest.java` file to Wattle for marking.
 *
 *
 * @author nanwang
 *
 */
public class SomethingTest {

	@Test
	public void test() {
		// Implement your test cases
        // START YOUR CODE
        // gcd=36，两个lcm相等；余数=15：三个if都true。
        org.junit.Assert.assertEquals(228,Something.someMethod(36,36,30,45));
        // gcd=36，lcm不相等；余数!=15：内层if和最后if为false。
        org.junit.Assert.assertEquals(72,Something.someMethod(36,36,10,21));
        // gcd!=36：外层if为false，内部条件不执行。
        org.junit.Assert.assertEquals(0,Something.someMethod(2,3,4,6));

        // END YOUR CODE
	}
}
