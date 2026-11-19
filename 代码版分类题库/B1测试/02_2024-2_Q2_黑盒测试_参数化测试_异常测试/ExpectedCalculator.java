// 黑盒题：按照题目公式计算，不能调用待测HashingBox来当标准答案。
public class ExpectedCalculator {
    public static void main(String[] args) {
        // 想算哪一组，就改这4个变量。
        String name = "Comp2100";
        int size = 3;
        Material material = Material.SILVER;
        Shape shape = Shape.SQUARE;

        // 看每个字符的编码。英文字母的编码就是它的ASCII值。
        for (int i = 0; i < name.length(); i++) {
            char letter = name.charAt(i);
            int code = letter;
            System.out.println("字符 " + letter + " 的编码 = " + code);
        }

        int answer = expected(material, size, shape, name);
        System.out.println("expected = " + answer);
    }

    // 参数顺序：材质、大小、形状、名字。
    public static int expected(Material material, int size, Shape shape, String name) {
        if (size < 1 || size > 31) {
            throw new IllegalArgumentException("size必须在1到31之间");
        }

        // 第一步：用题目范围内的质数表，找不超过size的最大质数。
        // size=1是例外，题目规定sizeHash=2，所以先设成2。
        int sizeHash = 2;
        int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31};
        for (int i = 0; i < primes.length; i++) {
            if (primes[i] <= size) {
                sizeHash = primes[i];
            }
        }

        // 第二步：只看名字前6个位置，只累加其中英文字母的ASCII。
        int asciiSum = 0;
        for (int i = 0; i < name.length() && i < 6; i++) {
            char letter = name.charAt(i);
            if ((letter >= 'A' && letter <= 'Z') || (letter >= 'a' && letter <= 'z')) {
                asciiSum = asciiSum + letter;
            }
        }
        int nameHash = asciiSum * sizeHash + name.length();

        // 第三步：材质值乘形状值，最后与nameHash相加。
        int materialShapeHash = material.getValue() * shape.getValue();
        return nameHash + materialShapeHash;
    }
}
