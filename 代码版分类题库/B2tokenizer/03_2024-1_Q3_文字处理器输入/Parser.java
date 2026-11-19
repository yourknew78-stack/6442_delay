import java.util.List;

public class Parser {
    Tokeniser tokeniser;

    public Parser(Tokeniser tokeniser) {
        this.tokeniser = tokeniser;
    }


    /**
     * Task (B)
     * Get all tokens by tokenising through the buffer string
     *
     * @return a list of all tokenised InputToken from the buffer if it's valid, or
     * return null if there is any invalid token strings within the buffer.
     *
     * Example
     *   Given a tokeniser with buffer `abc3[CTRL][?]`, getAllTokens should return null.
     *   Despite some valid tokens could be tokenised at the beginning,
     *   the invalid token string by the end (`[CTRL][?]`) invalidates the entire list
     *   of tokens.
     *
     * <p>
     * Hint: next() is already called once when the tokeniser was instantiated,
     * so you may check `this.currentToken` to determine your next action.
     */
    public List<InputToken> getAllTokens() {
        List<InputToken> tokens = new java.util.ArrayList<InputToken>();
        while (tokeniser.getCurrentToken() != null) {
            tokens.add(tokeniser.getCurrentToken());
            tokeniser.next(); // 构造函数已读取第一个，这里再向后读取。
        }
        if (!tokeniser.isEmpty()) return null; // 还剩文本说明发生了分词错误。
        return tokens;
    }

}
