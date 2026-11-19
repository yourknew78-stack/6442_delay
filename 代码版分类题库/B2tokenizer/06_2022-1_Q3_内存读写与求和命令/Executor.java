import java.util.LinkedList;
import java.util.List;

public class Executor {

	private final List<Command> commands;

	private final BST memory = new BST();

	public Executor(List<Command> commands) {
		this.commands = commands;
	}

	public List<NodeCopy> execute() {
		List<NodeCopy> results = new LinkedList<>();
		for (Command c : this.commands) {
			if (c instanceof LoadCommand) {
				results.add(load((LoadCommand) c));
			} else if (c instanceof SaveCommand) {
				results.add(save((SaveCommand) c));
			} else if (c instanceof SumCommand) {
				results.add(sum((SumCommand) c));
			}
		}

		return results;
	}

	private NodeCopy load(LoadCommand c) {
        Node node = memory.find(c.getKey());
        if (node == null) return null;
        return new NodeCopy(node); // 返回当时的快照，后续修改不会改变旧结果。
    }

	/**
	 * return the nodecopy of the save command key to be saved into memory
	 * 
	 * @param c
	 * @return
	 */
	private NodeCopy save(SaveCommand c) {
        Node node = memory.find(c.getKey());
        if (node == null) {
            node = new Node(c.getKey(), c.getValue());
            memory.insert(node);
        } else {
            node.value = c.getValue(); // 已存在的 key 直接更新，不重复插入。
        }
        return new NodeCopy(node);
    }

	/**
	 * return the nodecopy of the sum command key to be saved into memory
	 * 
	 * @param c
	 * @return
	 */
	private NodeCopy sum(SumCommand c) {
        String pattern = c.getPattern();
        int star = pattern.indexOf('*');
        int total = 0;
        for (Node node : memory.invertedPreOrder()) {
            boolean matches;
            if (star == -1) matches = node.key.equals(pattern);
            else {
                String before = pattern.substring(0, star);
                String after = pattern.substring(star + 1);
                // 星号可以匹配空串，但前缀和后缀不能互相重叠。
                matches = node.key.length() >= before.length() + after.length()
                    && node.key.startsWith(before) && node.key.endsWith(after);
            }
            if (matches) total += node.value;
        }
        return save(new SaveCommand(c.getKey(), total));
    }

	public List<Command> getCommands() {
		return commands;
	}
}
