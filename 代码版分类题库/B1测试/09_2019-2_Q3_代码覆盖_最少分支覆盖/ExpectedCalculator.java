package utils;

public class ExpectedCalculator {
    public static void main(String[] args) {
        // parseDouble：修改括号里的字符串，查看提取出的数字。
        System.out.println("第1组 expected = " + MyUtil.parseDouble(null));
        System.out.println("第2组 expected = " + MyUtil.parseDouble("abc"));
        System.out.println("第3组 expected = " + MyUtil.parseDouble("abc12.34xyz"));

        // isMixedCase：查看是否同时含有大写和小写字母。
        System.out.println("第1组 expected = " + MyStringUtil.isMixedCase(null));
        System.out.println("第2组 expected = " + MyStringUtil.isMixedCase(""));
        System.out.println("第3组 expected = " + MyStringUtil.isMixedCase("a"));
        System.out.println("第4组 expected = " + MyStringUtil.isMixedCase("A"));
        System.out.println("第5组 expected = " + MyStringUtil.isMixedCase("aA"));
        System.out.println("第6组 expected = " + MyStringUtil.isMixedCase("Aa0"));
    }
}
