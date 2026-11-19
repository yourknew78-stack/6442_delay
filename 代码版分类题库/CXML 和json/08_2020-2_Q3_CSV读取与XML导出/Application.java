
import java.util.LinkedList;
import java.util.List;


public class Application {

	public RBTree<Integer, Person> createTree(List<Person> personList) {
        RBTree<Integer, Person> tree = new RBTree<Integer, Person>();
        for (Person person : personList) tree.insert(person.getId(), person);
        return tree;
    }

	public List<Person> readCsv(String fileName) {
        List<Person> people = new LinkedList<Person>();
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(fileName))) {
            reader.readLine(); // 第一行是 id;name;age;occupation，不是一个人。
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] fields = line.split(";", -1); // 本题使用分号分隔，保留空字段。
                people.add(new Person(Integer.parseInt(fields[0]), fields[1], Integer.parseInt(fields[2]), fields[3]));
            }
        } catch (java.io.IOException e) {
            throw new IllegalArgumentException("读取 CSV 失败", e);
        }
        return people;
    }

	public void saveXML(RBTree<Integer, Person> tree) {
        if (tree == null) return;
        try {
            org.w3c.dom.Document doc = javax.xml.parsers.DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            org.w3c.dom.Element root = doc.createElement("people");
            doc.appendChild(root);
            for (Node node : tree.levelTraversal()) {
                Person person = (Person) node.getData();
                org.w3c.dom.Element row = doc.createElement("person");
                root.appendChild(row);
                String[] tags = {"id", "name", "age", "occupation"};
                String[] values = {person.getId().toString(), person.getName(), person.getAge().toString(), person.getOccupation()};
                for (int i = 0; i < tags.length; i++) {
                    org.w3c.dom.Element child = doc.createElement(tags[i]);
                    child.setTextContent(values[i]);
                    row.appendChild(child);
                }
            }
            // 内存中的 DOM 树还不是文件，需要 Transformer 写到磁盘。
            javax.xml.transform.Transformer writer = javax.xml.transform.TransformerFactory.newInstance().newTransformer();
            writer.setOutputProperty(javax.xml.transform.OutputKeys.ENCODING, "utf-8");
            writer.transform(new javax.xml.transform.dom.DOMSource(doc), new javax.xml.transform.stream.StreamResult(new java.io.File("people.xml")));
        } catch (Exception e) {
            throw new IllegalArgumentException("写入 XML 失败", e);
        }
    }

}
