import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * IMPORTANT: This class is incomplete. Please look for "TODO" comments.
 * 
 * Save() method is already given. Please implement load() method to load XML
 * files and insert() method to insert a new customer to XML files.
 * 
 * Each customer must have an ID value, but may not have all the following column values:
 * Name, Address, City, Postcode, Country. Please see test cases in XMLTableTest.java.
 * 
 */
public class XMLTable {

	/**
	 * Save all records to the XML file
	 */
	public void save(String tableName, List<Customer> customers) {

		File f = new File(FileUtil.getTableFileName(tableName));

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			// make the xml tree
			// use factory to get the instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.newDocument();
			// Root element <customers>
			Element rootElem = doc.createElement(Customer.KEY_ROOT);
			doc.appendChild(rootElem);

			for (Customer customer : customers) {
				Element customerElem = doc.createElement(Customer.KEY_ELEMENT);
				rootElem.appendChild(customerElem);

				// child element <ID> under <customer>
				Element cid = doc.createElement(Customer.KEY_ID);
				cid.appendChild(doc.createTextNode(Integer.toString(customer.getId())));
				customerElem.appendChild(cid);

				if (customer.getName() != null) {
					Element name = doc.createElement(Customer.KEY_NAME);
					name.appendChild(doc.createTextNode(customer.getName()));
					customerElem.appendChild(name);
				}

				if (customer.getAddress() != null) {
					Element address = doc.createElement(Customer.KEY_ADDRESS);
					address.appendChild(doc.createTextNode(customer.getAddress()));
					customerElem.appendChild(address);
				}

				if (customer.getCity() != null) {
					Element city = doc.createElement(Customer.KEY_CITY);
					city.appendChild(doc.createTextNode(customer.getCity()));
					customerElem.appendChild(city);
				}

				if (customer.getPostCode() != null) {
					Element postCode = doc.createElement(Customer.KEY_POSTCODE);
					postCode.appendChild(doc.createTextNode(customer.getPostCode()));
					customerElem.appendChild(postCode);
				}

				if (customer.getCountry() != null) {
					Element country = doc.createElement(Customer.KEY_COUNTRY);
					country.appendChild(doc.createTextNode(customer.getCountry()));
					customerElem.appendChild(country);
				}
			}
			// save the xml file
			// Transformer is for process XML from a variety of sources and write the
			// transformation
			// output to a variety of sinks
			Transformer transformer = TransformerFactory.newInstance().newTransformer();

			// set xml encoding to utf-8
			transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
			// pretty print
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(f);
			transformer.transform(source, result);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get all records from the XML file
	 */
	public List<Customer> load(String tableName) {
        List<Customer> customers = new ArrayList<Customer>();
        File file = new File(FileUtil.getTableFileName(tableName));
        if (!file.exists()) return customers;
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
            org.w3c.dom.NodeList rows = doc.getElementsByTagName(Customer.KEY_ELEMENT);
            for (int i = 0; i < rows.getLength(); i++) {
                Element row = (Element) rows.item(i);
                Customer customer = new Customer();
                customer.setId(Integer.parseInt(text(row, Customer.KEY_ID)));
                // 可选字段缺失时保留 null，不能直接对 item(0) 调用 getTextContent。
                customer.setName(text(row, Customer.KEY_NAME));
                customer.setAddress(text(row, Customer.KEY_ADDRESS));
                customer.setCity(text(row, Customer.KEY_CITY));
                customer.setPostCode(text(row, Customer.KEY_POSTCODE));
                customer.setCountry(text(row, Customer.KEY_COUNTRY));
                customers.add(customer);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("读取 XML 失败", e);
        }
        return customers;
    }

	/**
	 * Insert a new customer to XML files. 
	 * 
	 *
	 */
	public void insert(String tableName, Customer customer) {
        List<Customer> customers = load(tableName); // 先读旧记录，不能覆盖丢失。
        customers.add(customer);
        save(tableName, customers);
    }

    private String text(Element parent, String tag) {
        org.w3c.dom.Node node = parent.getElementsByTagName(tag).item(0);
        if (node == null) return null;
        return node.getTextContent();
    }

}
