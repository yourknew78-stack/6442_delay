/* 简单读法
 * 每一行参数都是一个测试用例：new Pricing(...) 是输入，后面的整数是预期结果。
 * 这里保留原资料已确定的6组整体分支方案；用普通数组行列出，不在 expected 中调用被测方法。
 */
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;


@RunWith(Parameterized.class)
public class CalculatePriceTest {

    private final Pricing parameters;
    private final Integer expected;

    public CalculatePriceTest(Pricing parameters, Integer expected) {
        this.parameters = parameters;
        this.expected = expected;
    }


    /**
     * Provides test data for the .isValid() method for Question instances.
     *
     * Each element in the returned collection is an array of two elements:
     * -  a Question instance with specific values.
     * -  a boolean representing the expected result of isValid().
     *
     * This data is used to generate multiple test cases that validate the behavior
     * of the isValid() across a variety of input conditions. The goal is to achieve
     * complete branch coverage using the minimal set of test cases.
     *
     * Example:
     *         return Arrays.asList(new Object[][]{
     *                  {new Pricing(3, 1, "id1"), 10},
     *                  ...
     *         }
     *
     * @return a collection of test cases to be used in the parameterized test.
     */
    @Parameterized.Parameters(name = "{index}: isValid({0}) = {1}")
    public static Collection<Object[]> data() {
        // TODO - Start your code here
        // 按老师要求，只覆盖整条 if 判断的真假，不拆括号内条件：6 行；expected 由 ExpectedCalculator 独立运行核对。
        // 正式断言中不调用被测方法来生成 expected。
        return Arrays.asList(new Object[][]{
            {new Pricing(1, null, null), 4},    // 会员为空的整体判断为 true
            {new Pricing(1, null, "a"), 4},     // 会员为空的整体判断为 false
            {new Pricing(4, 6, "id1"), 10},     // 会员且单价达标的整体判断为 true，返回 10
            {new Pricing(1, 6, "a"), 5},       // 优惠判断为 false；6/6>=1 为 true
            {new Pricing(1, 5, "id1"), 5},     // 优惠判断为 false；5/6>=1 为 false
            {new Pricing(1, 4, "a"), 4}        // quantity>=5 false
        });

        // TODO - End your code here
    }

    public Integer callCalculatePrice(Pricing pricing) {
        return Pricing.calculatePrice(pricing.unitPrice, pricing.quantity, pricing.memberId);
    }

    @Test(timeout = 1000)
    public void testIsValid() {
        assertEquals(expected, callCalculatePrice(parameters));
    }
}
