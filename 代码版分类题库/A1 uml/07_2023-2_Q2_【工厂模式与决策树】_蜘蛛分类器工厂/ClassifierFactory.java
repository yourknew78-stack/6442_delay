import java.util.*;

public class ClassifierFactory {


    public static Node createClassifier(Set<Spider> possibilities) {
        return build(possibilities, new HashMap<Set<Spider>, Node>());
    }


    // 要求最少的最坏判断次数，不能只选第一次能分开的特征。
    // 对每个特征分别尝试，递归求两边的最优树，保留高度最小的一棵。
    private static Node build(Set<Spider> spiders, Map<Set<Spider>, Node> saved) {
        if (saved.containsKey(spiders)) return saved.get(spiders);
        if (spiders.size() == 1) return new TerminalNode(spiders.iterator().next().speciesName);
        Node best = null;
        int bestHeight = Integer.MAX_VALUE;
        for (Spider.Property property : Spider.Property.values()) {
            Set<Spider> yes = new HashSet<Spider>();
            Set<Spider> no = new HashSet<Spider>();
            for (Spider spider : spiders) {
                if (spider.testProperty(property)) yes.add(spider);
                else no.add(spider);
            }
            if (yes.isEmpty() || no.isEmpty()) continue; // 没分开，不会使问题变小。
            Node left = build(yes, saved);
            Node right = build(no, saved);
            if (left == null || right == null) continue;
            Node candidate = new DecisionNode(property, left, right);
            int height = candidate.maximumDecisions();
            if (height < bestHeight) {
                best = candidate;
                bestHeight = height;
            }
        }
        // Set 没有再修改，可以作为缓存的 key；同一组蜘蛛只计算一次。
        saved.put(spiders, best);
        return best;
    }

}
