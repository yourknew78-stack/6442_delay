
import java.util.List;

public class TreeBuilder {

    public RBTree<Integer, List<String>> build(String text) {
        RBTree<Integer, List<String>> tree = new RBTree<Integer, List<String>>();
        MyTokenizer tokenizer = new MyTokenizer(text);
        while (tokenizer.hasNext()) {
            Token token = tokenizer.current();
            if (token.getType() == Token.Type.UPPER_CASE_WORD) {
                String word = token.getValue();
                int key = word.length();
                List<String> words = tree.getData(key);
                if (words == null) {
                    words = new java.util.ArrayList<String>();
                    tree.insert(key, words);
                }
                if (!words.contains(word)) words.add(word); // 同长度放同节点，同单词不重复。
            }
            tokenizer.next();
        }
        return tree;
    }




}
