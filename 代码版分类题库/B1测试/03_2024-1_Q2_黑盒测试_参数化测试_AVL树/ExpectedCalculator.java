// 只改下面的插入顺序。参考树负责旋转，main只负责插入和打印。
public class ExpectedCalculator {
    public static void main(String[] args) {
        int[] numbers = {1, 7, 2, 3, 4, 6, 5};

        // 先用第一个数字创建根节点。
        // 这是独立的正确参考树，不是题目给的错误SampleAVLTree。
        AVLTree tree = new AVLVerification.ReferenceAVLTree(numbers[0]);
        System.out.println("插入 " + numbers[0] + " 后：" + tree.preOrder());

        // 从第二个数字开始，一个一个插入。
        for (int i = 1; i < numbers.length; i++) {
            tree = tree.insert(numbers[i]); // 旋转可能换根，所以接住返回的新树。
            System.out.println("插入 " + numbers[i] + " 后：" + tree.preOrder());
        }

        System.out.println("expected = " + tree.preOrder());
    }
}
