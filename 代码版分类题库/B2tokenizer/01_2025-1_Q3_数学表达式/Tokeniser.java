/*
 * 简单语法解答与读代码顺序
 * 处理数据：数学表达式，例如 x + 23;。
 * 第一步：next() 看第一个字符，x 是小写字母，生成 VARIABLE。
 * 第二步：takeNext() 删除 x，trim() 去掉两边空白，剩下 + 23;。
 * 第三步：下一次取 +，再取连续数字 23，最后取分号。
 * 注意：原题 next() 注释与 takeNext() 实现冲突。本答案按调用代码和测试，
 * 让 next() 只查看，takeNext() 负责删除，并补上非法 Token 的空值检查。
 * 因此这是可运行的学习版；若考试限制只能改 next()，需先向老师确认骨架矛盾。
 */
public class Tokeniser {
    private String buffer;
    private Token currentToken;

    public Tokeniser(String input) {
        this.buffer = input.trim();
    }


    /**
     * Task (A)
     * Tokenise the remaining buffer to get the next token.
     * <p>
     * Assign the next token to this.currentToken after it is found, and
     * update the buffer to exclude the tokenised part. Return the
     * next token (i.e., the same as this.currentToken).
     *
     * If the upcoming buffer does not represent a valid next token,
     * assign null to `this.currentToken` and the buffer should remain unchanged.
     * <p>
     * Notes: The tokenisation process should be case-sensitive on the token strings.
     */
    public Token next() {
        // TODO - Start your code
        // 原题的 takeNext() 已负责删除字符，因此这里仅识别，不重复删除。
        // 若两处都删除，例如 "x + 2" 就会跳过 +，甚至发生越界。
        currentToken = null;
        if (buffer.isEmpty()) {
            return null;
        }
        char c = buffer.charAt(0); // 看第一个字符，决定 Token 类型。
        if (c >= '0' && c <= '9') {
            int end = 0;
            while (end < buffer.length() && buffer.charAt(end) >= '0' && buffer.charAt(end) <= '9') {
                end++;
            }
            currentToken = new Token(TokenType.INT, buffer.substring(0, end));
        } else if (c >= 'a' && c <= 'z') {
            currentToken = new Token(TokenType.VARIABLE, buffer.substring(0, 1));
        } else if (c == '+' || c == '-' || c == '*') {
            currentToken = new Token(TokenType.OPERATOR, buffer.substring(0, 1));
        } else if (c == ';') {
            currentToken = new Token(TokenType.EOL, ";");
        }
        return currentToken; // 其他字符非法，返回 null。
        // TODO - End your code
    }

    public Token takeNext() {
        if (!this.hasNext()) {
            this.currentToken = null;
            return null;
        }

        Token nextToken = this.next();
        // 原骨架缺少这个检查：非法字符会让 next() 返回 null。
        if (nextToken == null) {
            return null;
        }

        this.buffer = this.buffer.substring(nextToken.getValue().length());
        this.buffer = this.buffer.trim();
        this.currentToken = nextToken;
        return nextToken;
    }

    public Token current() {
        return this.currentToken;
    }


    public boolean hasNext() {
        return (!this.buffer.isEmpty());
    }

}
