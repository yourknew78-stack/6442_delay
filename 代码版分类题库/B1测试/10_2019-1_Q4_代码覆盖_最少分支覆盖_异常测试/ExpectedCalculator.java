public class ExpectedCalculator {
    public static void main(String[] args) {
        // 想检查其他字符串，就修改这个数组。
        String[] inputs = {"123", "A", "/"};
        for (int i = 0; i < inputs.length; i++) {
            String input = inputs[i];
            try {
                int expected = MyInteger.parseInt(input);
                System.out.println(input + " 的expected = " + expected);
            } catch (NumberFormatException e) {
                System.out.println(input + " 的expected = NumberFormatException");
            }
        }

        String expected1 = StringUtil.collapseNewlines("x");
        String expected2 = StringUtil.collapseNewlines("a\n\nb");
        System.out.println("第1组换行测试 expected = " + expected1);
        // replace只改变打印方式：把真正的换行显示成两个字符 \ 和 n。
        System.out.println("第2组换行测试 expected = " + expected2.replace("\n", "\\n"));

        // 字符赋给int变量，就能看到编码；例如'0'是48。
        int code = '0';
        System.out.println("字符0的ASCII = " + code);
    }
}
