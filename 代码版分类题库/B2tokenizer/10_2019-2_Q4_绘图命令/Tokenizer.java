/*
 * 简单语法解答与读代码顺序
 * 处理数据：绘图命令，例如 FORWARD(50); BACK_TO_END;。
 * 先用分号取出一整条命令，再用 if/else 判断命令名称。
 * 带括号的命令把括号里的数字转为步数；TO_END 命令不带步数。
 * 本年度构造函数先调用 next()，current() 读取当前 Token。
 * next() 会删除当前命令及分号，再把识别结果保存在 current 中。
 */
package parser;
/**
 * IMPORTANT: This class is incomplete. Please look for "TODO" comments.
 * @author huy.pham
 * @author dongwoo
 */
public class Tokenizer {
    private String _buffer;
    private final char COMMAND_SEPARATOR = ';'; 
    private Token current;
    
    public Tokenizer(String buffer) {
        this._buffer = buffer;
        this.next();
    }
    
    /**
     *  Extract the next token from {@code _buffer}
     */
    public void next() {
        _buffer = _buffer.trim();
        if (_buffer.isEmpty()) {
            current = null;
            return;
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
        } else if (command.equals("FORWARD_TO_END")) {
            token = new Token(Token.Type.FORWARD_TO_END);
        } else if (command.equals("BACK_TO_END")) {
            token = new Token(Token.Type.BACK_TO_END);
        } else {
            // FORWARD(50)：括号之间的 "50" 是步数。
            int left = command.indexOf('(');
            int right = command.indexOf(')');
            if (left < 0 || right <= left) {
                current = null;
            return;
            }
            int steps = Integer.parseInt(command.substring(left + 1, right).trim());
            String name = command.substring(0, left).trim();
            if (name.equals("FORWARD")) {
                token = new Token(Token.Type.FORWARD, command, steps);
            } else if (name.equals("BACK")) {
                token = new Token(Token.Type.BACK, command, steps);
            } else {
                current = null;
            return;
            }
        }
        current = token;
        // 本年度 next() 自己前进；删除整条命令及其分号。
        if (separator == -1) {
            _buffer = "";
        } else {
            _buffer = _buffer.substring(separator + 1);
        }
    }
    

    /**
     * Return the token in {@code _current}
     * @return the next token
     */
    public Token current() {
        return current;
    }

    /**
     * @return whether there is another token in {@code _current}
     */
    public boolean hasNext() {
        if(current == null){
            return false;
        }
        return true;
    }

}