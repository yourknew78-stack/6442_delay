/*
 * 简单语法解答与读代码顺序
 * 处理数据：XML 加载与保存命令，例如 LOAD persons FROM persons.xml;。
 * 读取连续的非空白字符，但遇到分号必须停止。
 * persons.xml 是一个完整 PARAMETER，点号不能当分隔符。
 * 构造函数已经读出 LOAD；每次 next() 继续读取下一项。
 * 方法结尾的原题代码已经更新 buffer，填空部分不要重复删除。
 */
public class Tokeniser {

	private String buffer; // save text
	private Token currentToken; // save token extracted from next()

	public Tokeniser(String text) {
		buffer = text; // save input text (string)
		next(); // extracts the first token.
	}

	/**
	 * This function will find and extract a next token from {@code buffer} and save
	 * the token to {@code currentToken}.
	 */
	public void next() {

		buffer = buffer.trim(); // remove whitespace
		if (buffer.isEmpty()) {
			currentToken = null; // if there's no string left, set currentToken null and return
			return;
		}

		// ########## YOUR CODE STARTS HERE ##########
        // 分号独立成 Token；普通单词读到空白或分号就停。
        int end = 1;
        if (buffer.charAt(0) != ';') {
            while (end < buffer.length() && !Character.isWhitespace(buffer.charAt(end)) && buffer.charAt(end) != ';') {
                end++;
            }
        }
        String word = buffer.substring(0, end);
        Token.Type type = Token.Type.PARAMETER; // 默认是文件名或对象名。
        if (word.equalsIgnoreCase("LOAD")) {
            type = Token.Type.LOAD;
        } else if (word.equalsIgnoreCase("FROM")) {
            type = Token.Type.FROM;
        } else if (word.equalsIgnoreCase("SAVE")) {
            type = Token.Type.SAVE;
        } else if (word.equalsIgnoreCase("TO")) {
            type = Token.Type.TO;
        } else if (word.equals(";")) {
            type = Token.Type.TERMINATOR;
        }
        // 保留原始大小写：文件名和对象名区分大小写。
        currentToken = new Token(type, word);
        // 下面原题代码已经删除 Token，这里不要再删一次。
        // ########## YOUR CODE ENDS HERE ##########

		// Remove the extracted token from buffer
		int tokenLen = currentToken.getValue().length();
		buffer = buffer.substring(tokenLen);
	}

	/**
	 * returned the current token extracted by {@code next()} **** please do not
	 * modify this part ****
	 * 
	 * @return type: Token
	 */
	public Token current() {
		return currentToken;
	}

	/**
	 * check whether there still exists another tokens in the buffer or not ****
	 * please do not modify this part ****
	 * 
	 * @return type: boolean
	 */
	public boolean hasNext() {
		return currentToken != null;
	}

}