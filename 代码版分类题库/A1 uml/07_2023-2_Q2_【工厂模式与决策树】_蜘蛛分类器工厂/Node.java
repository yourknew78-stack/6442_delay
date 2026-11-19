public abstract class Node {
    public abstract int maximumDecisions();

    public String classify(Spider spider) {
        // 真正的规则分别由 DecisionNode 和 TerminalNode 实现。
        throw new UnsupportedOperationException("请使用具体节点的 classify 方法");
    }

}
