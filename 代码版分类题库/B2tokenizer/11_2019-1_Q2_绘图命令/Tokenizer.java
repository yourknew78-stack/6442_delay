/*
 * 简单语法解答与读代码顺序
 * 处理数据：绘图命令，例如 LEFT; FORWARD(50); PENUP;。
 * 先找分号获取完整命令；FORWARD(50) 的步数是括号里的 50。
 * 本年度 next() 仅查看下一条，takeNext() 才删除命令与分号。
 * hasNext() 使用 next() 查看，不会吃掉命令。
 * 因此可以反复调用 hasNext()，随后 takeNext() 仍然返回同一条命令。
 */
/**
 * IMPORTANT: This class is incomplete. Please look for "TODO" comments.
 * @author huy.pham
 */
public class Tokenizer {
    private String _buffer;
    private final char COMMAND_SEPARATOR = ';'; 
    
    public Tokenizer(String buffer) {
        this._buffer = buffer;
    }
    
    /**
     * @return the next token
     */
    private Token next() {
        _buffer = _buffer.trim();
        if (_buffer.isEmpty()) {
            return null;
        }
        // 每条绘图命令以分号分隔，先取出完整的一条。
        int separator = _buffer.indexOf(COMMAND_SEPARATOR);
        String command;
        if (separator == -1) {
            command = _buffer;
        } else {
            command = _buffer.substring(0, separator).trim();
        }
        Token token;
        if (command.equals("LEFT")) {
            token = new Token(Token.Type.LEFT);
        } else if (command.equals("RIGHT")) {
            token = new Token(Token.Type.RIGHT);
        } else if (command.equals("PENUP")) {
            token = new Token(Token.Type.PENUP);
        } else if (command.equals("PENDOWN")) {
            token = new Token(Token.Type.PENDOWN);
        }  else {
            // FORWARD(50)：括号之间的 "50" 是步数。
            int left = command.indexOf('(');
            int right = command.indexOf(')');
            if (left < 0 || right <= left) {
                return null;
            }
            int steps = Integer.parseInt(command.substring(left + 1, right).trim());
            String name = command.substring(0, left).trim();
            if (name.equals("FORWARD")) {
                token = new Token(Token.Type.FORWARD, command, steps);
            } else if (name.equals("BACK")) {
                token = new Token(Token.Type.BACK, command, steps);
            } else {
                return null;
            }
        }
        return token; // 本年度 next() 只查看，takeNext() 才前进。
    }
    

    /**
     * Return the next token and remove it from the buffer
     * @return the next token
     */
    public Token takeNext() {
        Token token = next();
        if (token == null) {
            return null;
        }
        // next() 已去掉前导空格，现在删掉命令及分号。
        int separator = _buffer.indexOf(COMMAND_SEPARATOR);
        if (separator == -1) {
            _buffer = "";
        } else {
            _buffer = _buffer.substring(separator + 1);
        }
        return token;
    }

    /**
     * @return whether there is another token to parse in the buffer
     */
    public boolean hasNext() {
        return next() != null; // 查看下一条是否存在，不消耗命令。
    }
   
}