import java.util.LinkedList;
import java.util.List;

public class Parser {

	private final Tokeniser tokeniser;

	public Parser(Tokeniser tokeniser) {
		this.tokeniser = tokeniser;
	}

	public List<Command> parseCmds() {
        List<Command> commands = new LinkedList<Command>();
        // 原题保证命令合法，因此按固定位置读取即可。
        while (tokeniser.hasNext()) {
            Token.Type type = tokeniser.current().getType();
            tokeniser.next();
            String first = tokeniser.current().getValue();
            tokeniser.next();
            if (type == Token.Type.LOAD) {
                commands.add(new LoadCommand(first));
            } else {
                tokeniser.next(); // 跳过 TO。
                String key = tokeniser.current().getValue();
                tokeniser.next();
                if (type == Token.Type.SAVE) commands.add(new SaveCommand(key, Integer.parseInt(first)));
                else commands.add(new SumCommand(first, key));
            }
            tokeniser.next(); // 跳过分号，进入下一条命令。
        }
        return commands;
    }
}
