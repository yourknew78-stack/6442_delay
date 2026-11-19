/*
 * 简单语法解答与读代码顺序
 * 处理数据：内存命令，例如 save 10 to&^ key; SUM k* TO total;。
 * 先跳过特殊分隔符，再读取字母、数字和星号；分号单独读取。
 * 关键字比较忽略大小写，参数 value 保留原始大小写。
 * 例如 k* 作为一个 PARAMETER 保存；星号在这里不会被展开或匹配。
 * 本文件只负责分词，通配符匹配属于执行命令的部分。
 */
public class Tokeniser {

	private String buffer;
	private Token currentToken;

	public Tokeniser(String text) {
		buffer = text;
		next();
	}

	public void next() {

		// ########## YOUR CODE STARTS HERE ##########
        currentToken = null;
        // 跳过空格和特殊分隔符；星号和分号有意义，不能跳过。
        int start = 0;
        while (start < buffer.length()) {
            char c = buffer.charAt(start);
            if (Character.isLetterOrDigit(c) || c == '*' || c == ';') {
                break;
            }
            start++;
        }
        buffer = buffer.substring(start);
        if (buffer.isEmpty()) {
            return;
        }
        int end = 1;
        if (buffer.charAt(0) != ';') {
            while (end < buffer.length()) {
                char c = buffer.charAt(end);
                if (!Character.isLetterOrDigit(c) && c != '*') {
                    break;
                }
                end++;
            }
        }
        String word = buffer.substring(0, end);
        Token.Type type = Token.Type.PARAMETER;
        if (word.equalsIgnoreCase("LOAD")) {
            type = Token.Type.LOAD;
        } else if (word.equalsIgnoreCase("SAVE")) {
            type = Token.Type.SAVE;
        } else if (word.equalsIgnoreCase("SUM")) {
            type = Token.Type.SUM;
        } else if (word.equalsIgnoreCase("TO")) {
            type = Token.Type.TO;
        } else if (word.equals(";")) {
            type = Token.Type.TERMINATOR;
        }
        currentToken = new Token(type, word);
        buffer = buffer.substring(end); // 留下还没处理的文本。
        // ########## YOUR CODE ENDS HERE ##########
	}

	public Token current() {
		return currentToken;
	}

	public boolean hasNext() {
		return currentToken != null;
	}

}