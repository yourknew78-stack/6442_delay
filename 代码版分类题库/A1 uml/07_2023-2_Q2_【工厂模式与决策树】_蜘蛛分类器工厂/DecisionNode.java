public class DecisionNode extends Node {
    private final Spider.Property testsFor;
    private final Node yesOutcome, noOutcome;
    public DecisionNode(Spider.Property testsFor, Node yesOutcome, Node noOutcome) {
        this.testsFor = testsFor;
        this.yesOutcome = yesOutcome;
        this.noOutcome = noOutcome;
    }

    @Override
    public int maximumDecisions() {
        return Math.max(yesOutcome.maximumDecisions(), noOutcome.maximumDecisions()) + 1;
    }

    @Override
    public String classify(Spider spider) {
        if (spider.testProperty(testsFor)) return yesOutcome.classify(spider);
        return noOutcome.classify(spider); // 递归沿 Yes 或 No 分支继续判断。
    }


    @Override
    public String toString() {
        return describe(this, "");
    }





    private String describe(Node node, String indent) {
        if (node instanceof TerminalNode) return ((TerminalNode) node).outcome;
        DecisionNode decision = (DecisionNode) node;
        String childIndent = indent + "| ";
        return Spider.propertyToString(decision.testsFor) + "?\n"
            + childIndent + "Yes: " + describe(decision.yesOutcome, childIndent) + "\n"
            + childIndent + "No: " + describe(decision.noOutcome, childIndent);
    }

}