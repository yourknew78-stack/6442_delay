package Q2;


public class MDParser {
    MDTokenizer tokenizer;

    public MDParser(MDTokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public ListElementsTree parse() {
        ListElementsTree tree = new ListElementsTree();
        // parents 的下标就是层级；0 是虚拟根，1 是最近的一级列表项。
        java.util.List<ListElementsTree.Node> parents = new java.util.ArrayList<ListElementsTree.Node>();
        parents.add(tree.root);
        while (tokenizer.hasNext()) {
            MDToken token = tokenizer.next();
            int level = token.getLevel();
            while (parents.size() > level) parents.remove(parents.size() - 1);
            ListElementsTree.Node parent = parents.get(level - 1);
            ListElementsTree.Node child = parent.insert(token.getContent());
            parents.add(child);
        }
        return tree;
    }


}
