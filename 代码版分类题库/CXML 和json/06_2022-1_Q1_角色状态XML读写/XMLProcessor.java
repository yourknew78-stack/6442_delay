import java.io.File;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public class XMLProcessor {

	private static final String STATE_ROOT_ELEMENT = "States";

	public static List<List<String>> read(String fileName) {
		File f = new File(fileName);
		if (!f.exists()) {
			return Collections.emptyList();
		}

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
			Document doc = db.parse(f);

			doc.getDocumentElement().normalize();

			// TODO
            // ########## YOUR CODE STARTS HERE ##########
            // 两个列表使用相同下标：keys[i] 的按键对应 states[i] 的状态。
            java.util.List<String> keys = new java.util.ArrayList<>();
            java.util.List<String> states = new java.util.ArrayList<>();
            org.w3c.dom.NodeList nodes = doc.getDocumentElement().getChildNodes();
            for (int i=0;i<nodes.getLength();i++) {
                org.w3c.dom.Node node=nodes.item(i);
                if (node.getNodeType()==org.w3c.dom.Node.ELEMENT_NODE) {
                    keys.add(node.getNodeName());
                    states.add(node.getTextContent().trim());
                }
            }
            return java.util.Arrays.asList(keys,states);

            // ########## YOUR CODE ENDS HERE ##########
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Collections.emptyList();
	}

	public static void write(String fileName, List<String> keys, List<String> states) {
		File f = new File(fileName);
		if (f.exists()) {
			f.delete();
		}

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {

			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.newDocument();

			// TODO
            // ########## YOUR CODE STARTS HERE ##########
            // 先创建根，再按列表顺序追加子节点，最后由 Transformer 写文件。
            org.w3c.dom.Element root=doc.createElement(STATE_ROOT_ELEMENT);
            doc.appendChild(root);
            for (int i=0;i<keys.size();i++) {
                org.w3c.dom.Element element=doc.createElement(keys.get(i));
                element.setTextContent(states.get(i));
                root.appendChild(element);
            }

            // ########## YOUR CODE ENDS HERE ##########

			Transformer transformer = TransformerFactory.newInstance().newTransformer();

			transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(f);
			transformer.transform(source, result);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
