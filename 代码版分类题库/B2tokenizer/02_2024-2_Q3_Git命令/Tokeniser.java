/*
 * 简单语法解答与读代码顺序
 * 处理数据：Git 命令，例如 git add ./src;。
 * 按空格和分号切出 git、add、./src、;，分别生成四个 Token。
 * 构造函数已经调用 next()，因此 getCurrentToken() 一开始就是 git。
 * next() 既识别又删除；./src 的实际文字保存在 PATH 的 value 中。
 * 使用普通 if/else、while 和 substring，不需要正则表达式。
 */
public class Tokeniser {
    private String buffer;
    private GitToken currentToken;

    public Tokeniser(String input) {
        this.buffer = input;
        this.next();
    }

    public GitToken getCurrentToken() {
        return this.currentToken;
    }


    /**
     * Task (A)
     * Tokenise the remaining buffer to get the next token.
     * <p>
     * Assign the next token to this.currentToken after it is found, and
     * update the buffer to exclude the tokenised part.
     * If the upcoming buffer does not represent a valid next token,
     * assign null to `this.currentToken` and the buffer should remain unchanged.
     * <p>
     * Notes: The tokenisation process should be case-sensitive on the token strings.
     */
    public GitToken next() {
        // Please feel free to change or remove any code provided within your coding area.
        // TODO - Your code starts here
        // s 是尚未处理的文本；先用局部变量，识别失败时不修改原 buffer。
        String s = buffer.trim();
        currentToken = null;
        if (s.isEmpty()) {
            buffer = s;
            return null;
        }

        // 分号自己就是一个 Token；其他内容一直读到空格或分号。
        int end = 1;
        if (s.charAt(0) != ';') {
            while (end < s.length() && s.charAt(end) != ' ' && s.charAt(end) != ';') {
                end++;
            }
        }
        String word = s.substring(0, end);
        if (word.equals("git")) {
            currentToken = new GitToken(GitTokenType.GIT);
        } else if (word.equals("add")) {
            currentToken = new GitToken(GitTokenType.ADD);
        } else if (word.equals("commit")) {
            currentToken = new GitToken(GitTokenType.COMMIT);
        } else if (word.equals("push")) {
            currentToken = new GitToken(GitTokenType.PUSH);
        } else if (word.equals(";")) {
            currentToken = new GitToken(GitTokenType.SEPARATOR);
        } else if (word.startsWith(".")) {
            // 只有路径 Token 需要保存实际文字，例如 ./src。
            currentToken = new GitToken(GitTokenType.PATH, word);
        } else {
            return null; // 不认识的单词：保留 buffer，表示输入有错误。
        }
        buffer = s.substring(end).trim(); // 删掉刚刚识别的部分。
        return currentToken;
        // TODO - Your code ends here
    }

    public boolean hasNext() {
        return (this.currentToken != null);
    }

    public boolean isEmpty() {
        return this.buffer.isEmpty();
    }

}
