/*
 * 简单语法解答与读代码顺序
 * 处理数据：知识图谱查询，例如 SELECT ?subject WHERE { I ?predicate ?object }。
 * 先读取 SELECT 和查询目标，再剥掉 WHERE 的外层花括号。
 * 最后 next() 每次读取一个条件：I 是 WHERE_MATCH，?predicate 是 WHERE_ANY。
 * 分词器只检查单个词；例如连续出现两个 ?subject，仍要分别生成 Token。
 * 单词组合是否符合完整语法，属于 Parser 的工作。
 */
public class QueryTokenizer {
    String buffer;

    public QueryTokenizer(String buffer) {
        this.buffer = buffer;
    }

    public boolean hasNext() {
        return this.buffer.trim().length() > 0;
    }


    /**
     *  Question 1(b). Returns the SELECT Token if a valid SELECT statement was found, or null otherwise.
     *  <p>
     *  You may also update the buffer to get prepared for the subsequent tokenization tasks.
     *      The updates do not need to be precise as long as it does not wrongly remove information
     *      (i.e., you could choose to not update the buffer here too)
     */
    public QToken getSelectTokenIfValid() {
        // TODO - Your Code Starts Here (Q1b)
        String s = buffer.trim();
        int end = 0;
        while (end < s.length() && !Character.isWhitespace(s.charAt(end))) {
            end++;
        }
        if (!s.substring(0, end).equals("SELECT")) {
            return null;
        }
        s = s.substring(end).trim();
        end = 0;
        while (end < s.length() && !Character.isWhitespace(s.charAt(end))) {
            end++;
        }
        String target = s.substring(0, end); // SELECT 后的查询目标。
        TripleComponent.TripleComponentType type;
        if (target.equals("?subject")) {
            type = TripleComponent.TripleComponentType.SUBJECT;
        } else if (target.equals("?predicate")) {
            type = TripleComponent.TripleComponentType.PREDICATE;
        } else if (target.equals("?object")) {
            type = TripleComponent.TripleComponentType.OBJECT;
        } else if (target.equals("triple")) {
            type = TripleComponent.TripleComponentType.TRIPLE;
        } else {
            return null;
        }
        buffer = s.substring(end).trim(); // 留下 WHERE 部分。
        return new QToken(QTokenType.SELECT, type);
        // TODO - Your Code END Here
    }


    /**
     *  Question 1(c). Determine whether the WHERE statement for search is valid.
     *  <p>
     *  You may also update the buffer to get prepared for the subsequent tokenization tasks.
     *      The updates do not need to be precise as long as it does not wrongly remove information
     *      (i.e., you could choose to not update the buffer here too)
     */
    public boolean whereIsValid() {
        // TODO - Your Code Starts Here (Q1c)
        String s = buffer.trim();
        if (!s.startsWith("WHERE") || s.length() <= 5 || !Character.isWhitespace(s.charAt(5))) {
            return false;
        }
        s = s.substring(5).trim();
        if (!s.startsWith("{") || !s.endsWith("}")) {
            return false;
        }
        // 去掉外层花括号，后续 next() 只读取条件中的单词。
        String conditions = s.substring(1, s.length() - 1).trim();
        if (conditions.isEmpty() || conditions.indexOf('{') >= 0 || conditions.indexOf('}') >= 0) {
            return false;
        }
        buffer = conditions;
        return true;
        // TODO - Your Code END Here
    }


    /**
     *  Question 1(d). Return the next (WHERE_*) QToken for search.
     */
    public QToken next() {
        buffer = buffer.trim();
        if (buffer.isEmpty()) {
            return null;
        }
        int end = 0;
        while (end < buffer.length() && !Character.isWhitespace(buffer.charAt(end))) {
            end++;
        }
        String word = buffer.substring(0, end);
        buffer = buffer.substring(end).trim();
        if (word.equals("?subject")) {
            return new QToken(QTokenType.WHERE_ANY, TripleComponent.TripleComponentType.SUBJECT);
        }
        if (word.equals("?predicate")) {
            return new QToken(QTokenType.WHERE_ANY, TripleComponent.TripleComponentType.PREDICATE);
        }
        if (word.equals("?object")) {
            return new QToken(QTokenType.WHERE_ANY, TripleComponent.TripleComponentType.OBJECT);
        }
        // 普通匹配值只允许英文字母、数字及题目列出的四种符号。
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            boolean letter = (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
            boolean digit = c >= '0' && c <= '9';
            if (!letter && !digit && c != ':' && c != '/' && c != '_' && c != '.') {
                return null;
            }
        }
        // 这里只判断单个词是否合法；词的先后顺序由 Parser 检查。
        return new QToken(word);
        // TODO - Your Code END Here
    }





}
