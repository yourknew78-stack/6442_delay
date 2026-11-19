package Q2;


public class MDFileReader {
    public static String read(String filePath)  {
        // 逐个字符读取，保留原文件的换行和缩进，供 Tokenizer 判断层级。
        StringBuilder text = new StringBuilder();
        try (java.io.FileReader reader = new java.io.FileReader(filePath)) {
            int c;
            while ((c = reader.read()) != -1) text.append((char) c);
        } catch (java.io.IOException e) {
            throw new IllegalArgumentException("无法读取文件：" + filePath, e);
        }
        return text.toString();
    }




}
