package Q1;

public class ExpectedCalculator {
    public static void main(String[] args) {
        // 参数顺序：题目类型、标准答案、输入答案。
        printExpected(Riddle.RiddleType.TEXT_RIDDLE, "Echo", "eCHO");
        printExpected(Riddle.RiddleType.TEXT_RIDDLE, "Echo", "wrong");
        printExpected(Riddle.RiddleType.NUMERIC_RIDDLE, "42", "abc");
        printExpected(Riddle.RiddleType.NUMERIC_RIDDLE, "42", "42");
        printExpected(Riddle.RiddleType.NUMERIC_RIDDLE, "42", "41");
        printExpected(Riddle.RiddleType.ANAGRAM_RIDDLE, "Listen", "Silent");
        printExpected(Riddle.RiddleType.ANAGRAM_RIDDLE, "Listen", "wrong");
        printExpected(null, "x", "x");
    }

    // 每次新建一道题，再调用原方法，最后打印结果。
    public static void printExpected(Riddle.RiddleType type, String answer, String input) {
        Riddle riddle = new Riddle("题目", answer, type);
        boolean expected = riddle.checkAnswer(input);
        System.out.println("类型=" + type + "，答案=" + answer + "，输入=" + input);
        System.out.println("expected = " + expected);
    }
}
