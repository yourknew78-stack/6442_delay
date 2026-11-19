import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.*;


public class TripleReader {

    /**
     * Question 1a) Read and create the triple objects from the XML File,
     * (You may assume the XML and the triples, entities so represented is in a VALID format)
     * <p>
     * Note: you may use any `java.io` packages
     * <p>
     * Return the set of triples corresponding to the XML file, or
     * null in the event of an exception. Not that there will NOT be cases of invalid input
     * (it is just to specify that this method must NOT throw an exception).
     */
    public static Set<Triple> read(String filePath) {
        Set<Triple> result = new HashSet<Triple>();
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new java.io.File(filePath));
            NodeList rows = doc.getElementsByTagName("triple");
            for (int i = 0; i < rows.getLength(); i++) {
                Element row = (Element) rows.item(i);
                // 每个 triple 中依次提取主语、谓语、宾语的文本。
                TripleComponent subject = new TripleComponent(TripleComponent.TripleComponentType.SUBJECT, row.getElementsByTagName("subject").item(0).getTextContent());
                TripleComponent predicate = new TripleComponent(TripleComponent.TripleComponentType.PREDICATE, row.getElementsByTagName("predicate").item(0).getTextContent());
                TripleComponent object = new TripleComponent(TripleComponent.TripleComponentType.OBJECT, row.getElementsByTagName("object").item(0).getTextContent());
                result.add(new Triple(subject, predicate, object));
            }
            return result;
        } catch (Exception e) {
            return null; // 原题明确要求异常时返回 null。
        }
    }


}