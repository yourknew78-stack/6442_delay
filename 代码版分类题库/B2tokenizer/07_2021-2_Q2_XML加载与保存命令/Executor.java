import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public class Executor {

	private final List<Command> commands;

	private final DataBase db = DataBase.getInstance();

	public Executor(List<Command> commands) {
		this.commands = commands;
	}

	public void execute() {
		for (Command c : this.commands) {
			if (c instanceof LoadCommand) {
				loadFrom((LoadCommand) c);
			} else if (c instanceof SaveCommand) {
				saveTo((SaveCommand) c);
			}
		}
	}

	/**
	 * save the persons from the database to the xml file
	 * 
	 * @param sac
	 */
	private void saveTo(SaveCommand sac) {

		List<Person> persons = this.db.load(sac.getKey());

		File f = new File(sac.getFileName());
		if (f.exists()) {
			f.delete();
		}

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {

			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.newDocument();

			// TODO
			// ########## YOUR CODE STARTS HERE ##########
            org.w3c.dom.Element root = doc.createElement(Person.KEY_ROOT);
            doc.appendChild(root); // 创建节点后，还要把它挂到文档上。
            for (Person person : persons) {
                org.w3c.dom.Element row = doc.createElement(Person.KEY_ELEMENT);
                root.appendChild(row);
                addText(doc, row, Person.KEY_NAME, person.getName());
                addText(doc, row, Person.KEY_GENDER, person.getGender());
                addText(doc, row, Person.KEY_AGE, Integer.toString(person.getAge()));
                addText(doc, row, Person.KEY_OCCUPATION, person.getOccupation());
            }
            // ########## YOUR CODE ENDS HERE ##########

			Transformer transformer = TransformerFactory.newInstance().newTransformer();

			transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");

			// INDENT the xml file is optional, you can
			// uncomment the following statement if you would like the xml files to be more
			// readable
			// transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(f);
			transformer.transform(source, result);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * load the persons from the xml file into the database
	 * 
	 * @param lc
	 */
	private void loadFrom(LoadCommand lc) {

		List<Person> persons = new LinkedList<>();

		File f = new File(lc.getFileName());
		if (!f.exists()) {
			return;
		}

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
			Document doc = db.parse(f);

			doc.getDocumentElement().normalize();

			// TODO
			// ########## YOUR CODE STARTS HERE ##########
            org.w3c.dom.NodeList rows = doc.getElementsByTagName(Person.KEY_ELEMENT);
            for (int i = 0; i < rows.getLength(); i++) {
                org.w3c.dom.Element row = (org.w3c.dom.Element) rows.item(i);
                String name = row.getElementsByTagName(Person.KEY_NAME).item(0).getTextContent();
                String gender = row.getElementsByTagName(Person.KEY_GENDER).item(0).getTextContent();
                int age = Integer.parseInt(row.getElementsByTagName(Person.KEY_AGE).item(0).getTextContent());
                String occupation = row.getElementsByTagName(Person.KEY_OCCUPATION).item(0).getTextContent();
                persons.add(new Person(name, gender, age, occupation));
            }
            // ########## YOUR CODE ENDS HERE ##########

			this.db.save(lc.getKey(), persons);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Command> getCommands() {
		return commands;
	}

    private void addText(Document doc, org.w3c.dom.Element parent, String tag, String text) {
        org.w3c.dom.Element child = doc.createElement(tag);
        child.setTextContent(text);
        parent.appendChild(child);
    }

}
