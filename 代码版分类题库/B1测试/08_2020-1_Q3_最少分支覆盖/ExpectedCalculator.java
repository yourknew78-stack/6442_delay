public class ExpectedCalculator {
    public static void main(String[] args) {
        // 参数顺序：n1、n2、n3、n4。用正整数，避免原代码循环不结束。
        System.out.println("第1组 expected = " + Something.someMethod(36, 36, 30, 45));
        System.out.println("第2组 expected = " + Something.someMethod(36, 36, 10, 21));
        System.out.println("第3组 expected = " + Something.someMethod(2, 3, 4, 6));
    }
}
