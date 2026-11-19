/*
 * 简单语法解答与读代码顺序
 * 处理数据：Markdown 列表，例如 - A，下一行有四个空格再写 2. B。
 * 一次读一行，空行跳过；数行首空格，计算 level = 空格数 / 4 + 1。
 * - 开头生成 UNORDERED_ITEM；数字加点开头生成 ORDERED_ITEM。
 * 例如四个空格后的 2. B：level 为 2，number 为 2，content 为 B。
 * 本题保证列表缩进合法，因此不额外编写复杂的缩进纠错。
 */
package Q2;

public class MDTokenizer {
    String buffer;
    MDToken current = null;

    public MDTokenizer(String mdContent) {
        this.buffer = mdContent;
    }

    public boolean hasNext() {
        return this.buffer.trim().length()>0; // You may make changes to this line if needed
    }


    public MDToken next() {
        // TODO - Your Code Starts Here
        current = null;
        while (!buffer.isEmpty()) {
            // 每次取一行；substring 后的 buffer 留给下一轮处理。
            int end = buffer.indexOf('\n');
            String line;
            if (end == -1) {
                line = buffer;
                buffer = "";
            } else {
                line = buffer.substring(0, end);
                buffer = buffer.substring(end + 1);
            }
            if (line.trim().isEmpty()) {
                continue; // 空行不生成 Token。
            }
            int spaces = 0;
            while (spaces < line.length() && line.charAt(spaces) == ' ') {
                spaces++;
            }
            int level = spaces / 4 + 1; // 0 个空格是第 1 层，4 个是第 2 层。
            String item = line.trim();
            if (item.startsWith("- ")) {
                String content = item.substring(2);
                current = new MDToken(MDTokenType.UNORDERED_ITEM, content, level);
            } else {
                int dot = item.indexOf('.');
                int number = Integer.parseInt(item.substring(0, dot));
                String content = item.substring(dot + 2); // 跳过点和后面的空格。
                current = new MDToken(MDTokenType.ORDERED_ITEM, content, number, level);
            }
            return current;
        }
        return null;
        // TODO - Your Code Ends Here
    }


    public MDToken getCurrent() {
        return this.current;
    }



}
