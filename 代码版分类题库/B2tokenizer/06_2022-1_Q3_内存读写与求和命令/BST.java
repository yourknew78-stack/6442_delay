import java.util.LinkedList;
import java.util.List;

public class BST {

	private Node root;

	public BST() {
		this.root = null;
	}

	public void insert(Node node) {
		if (this.root == null) {
			this.root = node;
		} else {
			Node parent = null;
			Node current = this.root;
			while (current != null) {
				if (current.key.compareTo(node.key) < 0) {
					parent = current;
					current = current.right;
				} else if (current.key.compareTo(node.key) > 0) {
					parent = current;
					current = current.left;
				} else {
					break;
				}
			}

			if (parent.key.compareTo(node.key) < 0) {
				parent.right = node;
			} else if (parent.key.compareTo(node.key) > 0) {
				parent.left = node;
			}
		}
	}

	public Node find(String key) {
        Node current = root;
        while (current != null) {
            int comparison = key.compareTo(current.key);
            if (comparison == 0) return current;
            if (comparison < 0) current = current.left; // 小的去左边找。
            else current = current.right;
        }
        return null; // 走到空位置，说明没有这个 key。
    }

	public List<Node> invertedPreOrder() {
        List<Node> list = new LinkedList<Node>();
        visit(root, list);
        return list;
    }


    private void visit(Node node, List<Node> list) {
        if (node == null) return;
        list.add(node); // 反向前序：根、右、左。
        visit(node.right, list);
        visit(node.left, list);
    }

}