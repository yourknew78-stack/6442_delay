/* 简单读法
 * 目标是让 findSomething 中的分支走到真假两侧。
 * 原题按被测方法调用次数计数，所以不能靠循环多次调用伪装成一个用例。
 * assertEquals 的第一个参数是预先算好的答案，第二个才是实际调用结果。
 */
import org.junit.Test;

public class BranchCompleteTest {

	@Test(timeout = 1000)
	public void test() {

		// TODO
        // START YOUR CODE
        org.junit.Assert.assertTrue(BranchComplete.findSomething(-2,-1,0)); // 返回 ||：左T
        org.junit.Assert.assertTrue(BranchComplete.findSomething(1,-1,0));  // 返回 ||：左F右T
        org.junit.Assert.assertFalse(BranchComplete.findSomething(-1,-2,0));// 返回 ||：左F右F
        org.junit.Assert.assertFalse(BranchComplete.findSomething(2,1,0));  // 返回 &&：左F
        org.junit.Assert.assertTrue(BranchComplete.findSomething(1,2,0));   // 返回 &&：左T右T
        org.junit.Assert.assertFalse(BranchComplete.findSomething(1,2,1));  // 返回 &&：左T右F

        // END YOUR CODE
	}
}
