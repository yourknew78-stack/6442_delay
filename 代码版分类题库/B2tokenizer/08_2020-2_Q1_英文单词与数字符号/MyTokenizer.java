/*
 * 简单语法解答与读代码顺序
 * 处理数据：英文单词、整数和符号，例如 KING caStle 123 #。
 * 连续数字生成 INTEGER_NUMBER；连续英文字母先组成一个单词。
 * 再遍历单词，记录是否出现大写和小写：全大写、全小写或混合大小写。
 * 混合大小写且长度不超过 3 是 SHORT_CAMEL_CASE_WORD，否则是 LONG_CAMEL_CASE_WORD。
 * 例如 caStle 长度为 6，属于 LONG_CAMEL_CASE_WORD；# 单独生成符号 Token。
 */


public class MyTokenizer implements Tokenizer {

    private String buffer;		//save text
    private Token currentToken;	//save token extracted from next()

    /**
     *  Tokenizer class constructor
     *  The constructor extracts the first token and save it to currentToken
     *  **** please do not modify this part ****
     */
    public MyTokenizer(String text) {
    	buffer = text;		// save input text (string)
    	next();		// extracts the first token.
    }

    /**
     *  This function will find and extract a next token from {@code buffer} and
     *  save the token to {@code currentToken}.
     */
    public void next() {


        buffer = buffer.trim(); // remove whitespace

         if(buffer.isEmpty()) {
            currentToken = null;	// if there's no string left, set currentToken null and return
            return;
         }

        // ########## YOUR CODE STARTS HERE ##########
        char first = buffer.charAt(0);
        int end = 1;
        Token.Type type;
        if (first >= '0' && first <= '9') {
            while (end < buffer.length() && buffer.charAt(end) >= '0' && buffer.charAt(end) <= '9') {
                end++;
            }
            type = Token.Type.INTEGER_NUMBER;
        } else if ((first >= 'A' && first <= 'Z') || (first >= 'a' && first <= 'z')) {
            // 一个单词是连续的英文字母，数字和符号会结束当前单词。
            while (end < buffer.length()) {
                char c = buffer.charAt(end);
                if (!((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))) {
                    break;
                }
                end++;
            }
            boolean hasUpper = false;
            boolean hasLower = false;
            for (int i = 0; i < end; i++) {
                char c = buffer.charAt(i);
                if (c >= 'A' && c <= 'Z') {
                    hasUpper = true;
                } else {
                    hasLower = true;
                }
            }
            if (!hasLower) {
                type = Token.Type.UPPER_CASE_WORD;
            } else if (!hasUpper) {
                type = Token.Type.LOWER_CASE_WORD;
            } else if (end <= 3) {
                type = Token.Type.SHORT_CAMEL_CASE_WORD;
            } else {
                type = Token.Type.LONG_CAMEL_CASE_WORD;
            }
        } else {
            type = Token.Type.NON_ALPHANUMERIC; // 符号一次只取一个。
        }
        currentToken = new Token(buffer.substring(0, end), type);
        // 方法末尾的原题代码负责删除这个 Token。
        // ########## YOUR CODE ENDS HERE ##########
        
        // Remove the extracted token from buffer
        int tokenLen = currentToken.getValue().length();
        buffer = buffer.substring(tokenLen);
    }

    /**
     *  returned the current token extracted by {@code next()}
     *  **** please do not modify this part ****
     *  
     * @return type: Token
     */
    public Token current() {
    	return currentToken;
    }

    /**
     *  check whether there still exists another tokens in the buffer or not
     *  **** please do not modify this part ****
     *  
     * @return type: boolean
     */
    public boolean hasNext() {
    	return currentToken != null;
    }


}