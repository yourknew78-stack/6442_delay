import java.util.ArrayList;
import java.util.List;


public class Parser {
    Tokeniser tokeniser;

    public Parser() {
    }

    public Parser(Tokeniser tokeniser) {
        this.tokeniser = tokeniser;
    }


    /**
     * Task (B)
     * Parse the tokens and create git commands.
     */
    public List<GitCommand> parse() {
        List<GitCommand> commands = new ArrayList<GitCommand>();
        while (tokeniser.hasNext()) {
            // 一条命令的顺序必须是 git -> 操作 -> 可选路径 -> 分号。
            if (tokeniser.getCurrentToken().getType() != GitTokenType.GIT) return null;
            tokeniser.next();
            if (!tokeniser.hasNext()) return null;
            GitTokenType operation = tokeniser.getCurrentToken().getType();
            tokeniser.next();
            GitCommand command;
            if (operation == GitTokenType.ADD) {
                if (!tokeniser.hasNext() || tokeniser.getCurrentToken().getType() != GitTokenType.PATH) return null;
                command = new GitCommand(GitCommand.GitCommandType.GIT_ADD, tokeniser.getCurrentToken().getValue());
                tokeniser.next();
            } else if (operation == GitTokenType.COMMIT) {
                command = new GitCommand(GitCommand.GitCommandType.GIT_COMMIT);
            } else if (operation == GitTokenType.PUSH) {
                command = new GitCommand(GitCommand.GitCommandType.GIT_PUSH);
            } else return null;
            if (!tokeniser.hasNext() || tokeniser.getCurrentToken().getType() != GitTokenType.SEPARATOR) return null;
            commands.add(command);
            tokeniser.next();
        }
        // current 为 null 也可能是遇到非法字符，而不是正常结束。
        if (!tokeniser.isEmpty() || commands.isEmpty()) return null;
        return commands;
    }



}
