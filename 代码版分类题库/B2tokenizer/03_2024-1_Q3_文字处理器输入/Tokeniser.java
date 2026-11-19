/*
 * 简单语法解答与读代码顺序
 * 处理数据：文字处理器输入，例如 a3[CTRL][Z]。
 * a 生成 TYPE，3 生成 OCCURRENCES，快捷键生成 UNDO。
 * 每次只处理一个 Token，剩下的文本放回 buffer。
 * regionMatches(true, ...) 表示忽略大小写比较指定的一段文字。
 * 换行也是 Token，因此这里只跳过空格，不能直接 trim()。
 */
public class Tokeniser {
    private String buffer;
    private InputToken currentToken;

    public Tokeniser(String input) {
        this.buffer = input;
        this.next();
    }

    public InputToken getCurrentToken() {
        return this.currentToken;
    }


    /**
     * Task (A)
     * Tokenise the remaining buffer to get the next token.
     * <p>
     * Assign the next token to this.currentToken after it is found, and
     * update the buffer to exclude the tokenised part. Otherwise, if there
     * is not a valid next token, this.currentToken should be null and the
     * buffer should remain unchanged.
     * <p>
     * Notes:
     *   -  The tokenisation process should be case-insensitive on the token strings.
     */
    public void next() {
        // TODO - Your code starts here
        // 换行本身是 SEPARATOR，不能用 trim() 把它一起删掉。
        String s = buffer;
        int spaces = 0;
        while (spaces < s.length() && s.charAt(spaces) == ' ') {
            spaces++;
        }
        s = s.substring(spaces);
        currentToken = null;
        if (s.isEmpty()) {
            buffer = s;
            return;
        }
        int length = 1; // 默认只取一个字符。
        char c = s.charAt(0);
        if (s.regionMatches(true, 0, "[CTRL][Z]", 0, 9)) {
            currentToken = new InputToken(TokenType.UNDO);
            length = 9;
        } else if (s.regionMatches(true, 0, "[CTRL][Y]", 0, 9)) {
            currentToken = new InputToken(TokenType.REDO);
            length = 9;
        } else if (s.regionMatches(true, 0, "{CapsLock}", 0, 10)) {
            currentToken = new InputToken(TokenType.CAPITALISE);
            length = 10;
        } else if (c == '\n') {
            currentToken = new InputToken(TokenType.SEPARATOR);
        } else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
            currentToken = new InputToken(TokenType.TYPE);
        } else if (c >= '0' && c <= '9') {
            int value = 0;
            length = 0;
            while (length < s.length() && s.charAt(length) >= '0' && s.charAt(length) <= '9') {
                // 例如原来是 12，再读到 3，就得到 12 * 10 + 3 = 123。
                value = value * 10 + s.charAt(length) - '0';
                if (value >= 1000) {
                    return; // 超出题目范围；原 buffer 不变。
                }
                length++;
            }
            currentToken = new InputToken(TokenType.OCCURRENCES, value);
        } else {
            return; // 非法输入，currentToken 保持 null。
        }
        buffer = s.substring(length); // 成功后才删除识别的字符。
        // TODO - Your code ends here
    }


    public boolean isEmpty() {
        return this.buffer.isEmpty();
    }


}
