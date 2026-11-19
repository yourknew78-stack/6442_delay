/**
 * IMPORTANT: This class is incomplete. Please look for "TODO" comments.
 * 
 * @author huy.pham
 */
public class Parser {
	private Tokenizer _tokenizer;
	private Screen _screen;

	public Parser(Tokenizer tokenizer, Screen screen) {
		_tokenizer = tokenizer;
		_screen = screen;
	}

    /**
     * This should parse all the tokens, turn the pointer and mark its visited positions for
     * every move.
     *
     * If there is no more token, this should return the current screen
     *
     * @return a screen object containing pointer's trace information.
     * @throws Exception
     */
    public Screen parse() throws OutOfScreenException {
        Pointer pointer = _screen.pointer;
        while (_tokenizer.hasNext()) {
            Token token = _tokenizer.takeNext();
            if (token.type == Token.Type.LEFT) pointer.turnLeft();
            else if (token.type == Token.Type.RIGHT) pointer.turnRight();
            else if (token.type == Token.Type.PENUP) pointer.isPenDown = false;
            else if (token.type == Token.Type.PENDOWN) {
                pointer.isPenDown = true;
                _screen.markVisistedPos(pointer.position);
            } else {
                int dx = 0;
                int dy = 0;
                if (pointer.direction == Direction.Upward) dx = -1;
                else if (pointer.direction == Direction.Downward) dx = 1;
                else if (pointer.direction == Direction.Left) dy = -1;
                else dy = 1;
                boolean toEnd = false;
                boolean back = token.type == Token.Type.BACK;
                if (back) { dx = -dx; dy = -dy; }
                int steps = 0;
                while (toEnd || steps < token.value) {
                    int x = pointer.position.x + dx;
                    int y = pointer.position.y + dy;
                    if (x < 0 || x >= _screen.noOfRows || y < 0 || y >= _screen.noOfColumns) {
                        if (toEnd) break; // TO_END 到边界正常停下，普通移动越界抛异常。
                        throw new OutOfScreenException();
                    }
                    pointer.position.x = x;
                    pointer.position.y = y;
                    if (pointer.isPenDown) _screen.markVisistedPos(pointer.position);
                    steps++;
                }
            }
        }
        return _screen;
    }
}
