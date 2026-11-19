package Q2;

import java.util.ArrayList;
import java.util.List;

public class ListElementsTree {
    Node root;

    public ListElementsTree() {
        this.root = new Node(MDTokenType.LIST_ROOT.toString());
    }

    public Node insert(MDToken child) {
        return this.root.insert(child.getContent());
    }

    public List<String> preOrderWalk() {
        List<String> result = new ArrayList<String>();
        // 根是人为添加的 LIST_ROOT，不放进答案，从它的孩子开始。
        for (Node child : root.getChildren()) walk(child, result);
        return result;
    }



    /** Note: this method is created for testing ONLY. It should not be used in your submissions. */
    public ListElementsTree(String rootValue, List<String> childrenValues) {
        this.root = new Node(rootValue);
        for (String childValue : childrenValues) {
            Node child = new Node(childValue);
            this.root.children.add(child);
        }
    }

    /** Note: this method is created for testing ONLY. It should not be used in your submissions. */
    public Node insertTree(ListElementsTree tree) {
        this.root.children.add(tree.root);
        return tree.root;
    }



    /**
     * Node
     */
    public class Node {
        private String value;
        private List<Node> children;

        public Node(String value) {
            this.value = value;
            this.children = new ArrayList<>();
        }


        public Node insert(String value) {
            Node child = new Node(value);
            children.add(child);
            return child;
        }


        public List<Node> getChildren() {
            return children;
        }

        public String getValue() {
            return value;
        }

    }



    private void walk(Node node, List<String> result) {
        result.add(node.getValue()); // 前序：先自己，再按从左到右的顺序访问孩子。
        for (Node child : node.getChildren()) walk(child, result);
    }

}
