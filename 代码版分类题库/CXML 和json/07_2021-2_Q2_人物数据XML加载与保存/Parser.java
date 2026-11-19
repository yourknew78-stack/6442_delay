import java.util.LinkedList;
import java.util.List;

public class Parser {

	private final Tokeniser tokeniser;

	public Parser(Tokeniser tokeniser) {
		this.tokeniser = tokeniser;
	}

	public List<Command> parseCmds() {
        List<Command> commands = new LinkedList<Command>();
        while (tokeniser.hasNext()) {
            Token.Type type = tokeniser.current().getType();
            tokeniser.next();
            String key = tokeniser.current().getValue();
            tokeniser.next();
            tokeniser.next(); // 跳过 FROM 或 TO。
            String filename = tokeniser.current().getValue();
            tokeniser.next();
            tokeniser.next(); // 跳过分号。
            if (type == Token.Type.LOAD) commands.add(new LoadCommand(key, filename));
            else commands.add(new SaveCommand(key, filename));
        }
        return commands;
    }
}
