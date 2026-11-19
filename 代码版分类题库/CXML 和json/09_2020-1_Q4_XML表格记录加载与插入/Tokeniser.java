/*
 * 简单语法解答与读代码顺序
 * 处理数据：SQL 插入语句，例如 INSERT INTO Customers (ID) VALUES (2);。
 * 本题不是每个单词一个 Token：INSERT INTO Customers (ID) 是一整个 Token。
 * VALUES (2) 是第二个 Token。value 去掉关键字，originalTokenStr 保留原文。
 * next() 找完整括号并返回 Token；takeNext() 用 originalTokenStr 的长度删除前缀。
 * 缺括号时返回 null，不能借用 VALUES 那一段的括号完成 INSERT 部分。
 */
/**
 * IMPORTANT: This class is incomplete. Please look for "TODO" comments.
 * 
 * Implement next() method in Tokeniser.java to extract the SQL commands as Tokens as follows:
 * 
 * Token 1:
 * originalTokenStr: INSERT INTO table_name (column1, column2, column3, ...)
 * type: INSERT_INTO
 * value: table_name (column1, column2, column3, ...)
 * 
 * Token 2:
 * originalTokenStr: VALUES (value1, value2, value3, ...)
 * type: VALUES
 * value: (value1, value2, value3, ...)
 * 
 * Some brackets in the SQL commands may be missing. Please return null if some brackets are missing
 *
 * Please see test cases in TokeniserTest.java.
 */
public class Tokeniser {
	private String _buffer;

	public Tokeniser(String buffer) {
		this._buffer = buffer;
		int idx = this._buffer.indexOf(";");
		if (idx > 0) {
			this._buffer = this._buffer.substring(0, idx);
		}
	}

	/**
	 * Return the next token without changing the buffer
	 * 
	 * @return the next token OR null when there is no more token or the next token
	 *         is invalid.
	 */
	public Token next() {
		if (_buffer.isEmpty())
			return null;

		// TODO: Complete this method
		// START YOUR CODE
        // 本题的一个 Token 是整段 INSERT INTO ...(...) 或 VALUES (...)。
        String prefix;
        Token.Type type;
        if (_buffer.startsWith("INSERT INTO ")) {
            prefix = "INSERT INTO ";
            type = Token.Type.INSERT_INTO;
        } else if (_buffer.startsWith("VALUES ")) {
            prefix = "VALUES ";
            type = Token.Type.VALUES;
        } else {
            return null;
        }
        int left = _buffer.indexOf('(');
        int right = _buffer.indexOf(')');
        if (left < prefix.length() || right < left) {
            return null; // 找不到完整的一对括号。
        }
        // INSERT 的左括号必须出现在 VALUES 之前，不能借用后一段的括号。
        int values = _buffer.indexOf("VALUES ");
        if (type == Token.Type.INSERT_INTO && values >= 0 && right > values) {
            return null;
        }
        String original = _buffer.substring(0, right + 1);
        String value = original.substring(prefix.length()).trim();
        return new Token(type, value, original);
        // 不删除 buffer；takeNext() 已负责删除 original 的长度。
        // END YOUR CODE
	}

	/**
	 * Return the next token and remove it from the buffer
	 * 
	 * @return the next token OR null when there is no more token or the next token
	 *         is invalid.
	 */
	public Token takeNext() {
		Token nextToken = next();
		if (nextToken == null)
			return null;

		if (nextToken.originalTokenStr.length() < _buffer.length()) {
			_buffer = _buffer.substring(nextToken.originalTokenStr.length()).trim();
		} else {
			_buffer = "";
		}

		return nextToken;
	}

	/**
	 * @return whether there is another token to parse in the buffer
	 */
	public boolean hasNext() {
		return next() != null;
	}
}