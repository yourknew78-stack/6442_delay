// 修改下面的参数，运行 main，就能看到 expected。
// 参数顺序：单价、数量、会员编号。正式答案按老师要求保留6组。
public class ExpectedCalculator {
    public static void main(String[] args) {
        System.out.println("第1组 expected = " + Pricing.calculatePrice(1, null, null));
        System.out.println("第2组 expected = " + Pricing.calculatePrice(1, null, "a"));
        System.out.println("第3组 expected = " + Pricing.calculatePrice(4, 6, "id1"));
        System.out.println("第4组 expected = " + Pricing.calculatePrice(1, 6, "a"));
        System.out.println("第5组 expected = " + Pricing.calculatePrice(1, 5, "id1"));
        System.out.println("第6组 expected = " + Pricing.calculatePrice(1, 4, "a"));
    }
}
