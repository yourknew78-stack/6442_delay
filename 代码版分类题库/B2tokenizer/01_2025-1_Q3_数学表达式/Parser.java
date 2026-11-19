public class Parser {
    Tokeniser tokeniser;

    public Parser(Tokeniser tokeniser) {
        this.tokeniser = tokeniser;
    }


    /**
     * Task (B)
     * Parse the tokens and return the corresponding Exp, or return
     * null if it does not represent not a valid Exp.
     */
    public Exp parse() {
        // 按分号切出一组表达式；任何一处错误都使整体无效。
        java.util.List<Exp> expressions = new java.util.ArrayList<Exp>();
        while (tokeniser.hasNext()) {
            Exp expression = parseSingle();
            if (expression == null) return null;
            expressions.add(expression);
            if (tokeniser.hasNext()) {
                Token separator = tokeniser.takeNext();
                if (separator == null || separator.getType() != TokenType.EOL) return null;
            }
        }
        if (expressions.isEmpty()) return null;
        return new SeqExp(expressions);
    }


    // 文法右边还是 SingleExp，所以递归解析右边；不是通常的乘法优先。
    // x + y * 2 变成 x + (y * 2)，x * y + 2 则是 x * (y + 2)。
    private Exp parseSingle() {
        Token value = tokeniser.takeNext();
        if (value == null) return null;
        Exp left;
        if (value.getType() == TokenType.INT) {
            try {
                left = new IntExp(Integer.parseInt(value.getValue()));
            } catch (NumberFormatException e) {
                return null;
            }
        } else if (value.getType() == TokenType.VARIABLE) {
            left = new VarExp(value.getValue());
        } else {
            return null;
        }
        if (!tokeniser.hasNext()) return left;
        Token next = tokeniser.next(); // 只查看；此版本 next() 不删除文本。
        if (next == null) return null;
        if (next.getType() == TokenType.EOL) return left;
        if (next.getType() != TokenType.OPERATOR) return null;
        tokeniser.takeNext();
        OpExp.OpType operator;
        if (next.getValue().equals("+")) operator = OpExp.OpType.ADD;
        else if (next.getValue().equals("-")) operator = OpExp.OpType.SUBTRACT;
        else operator = OpExp.OpType.MULTIPLY;
        Exp right = parseSingle();
        if (right == null) return null;
        return new OpExp(left, operator, right);
    }

}
