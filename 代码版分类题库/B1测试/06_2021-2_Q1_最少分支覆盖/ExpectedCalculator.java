public class ExpectedCalculator {
    public static void main(String[] args) {
        // 参数顺序是 a、b、c。修改数字后运行，就能重算expected。
        System.out.println("第1组 expected = " + BranchComplete.findSomething(-2, -1, 0));
        System.out.println("第2组 expected = " + BranchComplete.findSomething(1, -1, 0));
        System.out.println("第3组 expected = " + BranchComplete.findSomething(-1, -2, 0));
        System.out.println("第4组 expected = " + BranchComplete.findSomething(2, 1, 0));
        System.out.println("第5组 expected = " + BranchComplete.findSomething(1, 2, 0));
        System.out.println("第6组 expected = " + BranchComplete.findSomething(1, 2, 1));
    }
}
