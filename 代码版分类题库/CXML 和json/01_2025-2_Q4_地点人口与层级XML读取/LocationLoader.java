import java.io.File;
import java.util.*;


public class LocationLoader {


    /**
     * Task (A) - Load location data as a map of names to populations.
     */
    public static Map<String, Integer> loadPopulationInfo(File f) {
        try {
            org.w3c.dom.Element root = javax.xml.parsers.DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(f).getDocumentElement();
            if (!root.getTagName().equals("Locations")) return null;
            Map<String, Integer> result = new LinkedHashMap<String, Integer>();
            org.w3c.dom.NodeList rows = root.getChildNodes();
            for (int i = 0; i < rows.getLength(); i++) {
                if (!(rows.item(i) instanceof org.w3c.dom.Element)) continue; // 跳过缩进空白。
                org.w3c.dom.Element row = (org.w3c.dom.Element) rows.item(i);
                if (!row.hasAttribute("name")) return null;
                Integer population = null;
                int count = 0;
                org.w3c.dom.NodeList children = row.getChildNodes();
                for (int j = 0; j < children.getLength(); j++) {
                    if (!(children.item(j) instanceof org.w3c.dom.Element)) continue;
                    org.w3c.dom.Element child = (org.w3c.dom.Element) children.item(j);
                    if (!child.getTagName().equals("Population") || ++count > 1 || child.getElementsByTagName("*").getLength() > 0) return null;
                    String text = child.getTextContent().trim();
                    if (!text.isEmpty()) population = Integer.parseInt(text);
                }
                result.put(row.getAttribute("name"), population);
            }
            return result;
        } catch (Exception e) {
            return null; // 文件、XML 格式和整数解析错误均按题意返回 null。
        }
    }


    /**
     * Task (B) - Load a tree of locations from a hierarchical XML.
     * @return the set of locations represented by the direct children
     *         in a valid format of the root <Locations> .
     */
    public static Set<Location> loadLocations(File f) {
        try {
            org.w3c.dom.Element root = javax.xml.parsers.DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(f).getDocumentElement();
            if (!root.getTagName().equals("Locations")) return null;
            return readChildren(root);
        } catch (Exception e) {
            return null;
        }
    }


    // 只读直接孩子，再递归下去；不能一次抓取所有后代，否则层级会被压平。
    private static Set<Location> readChildren(org.w3c.dom.Element parent) {
        Set<Location> result = new LinkedHashSet<Location>();
        org.w3c.dom.NodeList nodes = parent.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            if (!(nodes.item(i) instanceof org.w3c.dom.Element)) continue;
            org.w3c.dom.Element element = (org.w3c.dom.Element) nodes.item(i);
            if (!element.hasAttribute("name")) continue; // 缺 name 时跳过整棵子树。
            Location location = new Location(element.getTagName(), element.getAttribute("name"));
            String population = element.getAttribute("population").trim();
            if (!population.isEmpty()) location.population = Integer.parseInt(population);
            location.children.addAll(readChildren(element));
            result.add(location);
        }
        return result;
    }

}
