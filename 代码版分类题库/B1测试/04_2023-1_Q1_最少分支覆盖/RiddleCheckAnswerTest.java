/* 简单读法
 * assertTrue 检查答案应正确，assertFalse 检查答案应错误。
 * 每次新建谜题，分别让文本、数字、字母重排各分支被执行，避免上一题状态影响下一题。
 */
package Q1;

import org.junit.Test;
import static org.junit.Assert.*;

public class RiddleCheckAnswerTest {

    @Test
    public void tests() {
        // TODO: Implement test cases for the Riddle.checkAnswer() method
        // TODO - Your Code Starts here
        // 每个断言只调用一次 checkAnswer；各用新对象避免 solved 状态影响。
        assertTrue(new Riddle("?","Echo",Riddle.RiddleType.TEXT_RIDDLE).checkAnswer("eCHO"));
        assertFalse(new Riddle("?","Echo",Riddle.RiddleType.TEXT_RIDDLE).checkAnswer("wrong"));
        assertFalse(new Riddle("?","42",Riddle.RiddleType.NUMERIC_RIDDLE).checkAnswer("abc"));
        assertTrue(new Riddle("?","42",Riddle.RiddleType.NUMERIC_RIDDLE).checkAnswer("42"));
        assertFalse(new Riddle("?","42",Riddle.RiddleType.NUMERIC_RIDDLE).checkAnswer("41"));
        assertTrue(new Riddle("?","Listen",Riddle.RiddleType.ANAGRAM_RIDDLE).checkAnswer("Silent"));
        assertFalse(new Riddle("?","Listen",Riddle.RiddleType.ANAGRAM_RIDDLE).checkAnswer("wrong"));
        // null 类型不抛异常：三个类型判断都为 false，覆盖最后 else-if 的 false。
        assertFalse(new Riddle("?","x",null).checkAnswer("x"));

        // TODO - Your Code Ends here
    }

}