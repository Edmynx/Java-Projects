import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

/**
 * Demonstration of XML processing of a simple XML file produced by a Flickr API search.
 * Put the example from the lecture notes, flickr-dart.xml, in your project's inputs folder.
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012
 */
public class ProcessXML {
	/**
	 * Collects all the lines from the reader into a single string.
	 * Because I ran into a bug with my demo, also replaces & with + (for XML correctness).
	 * That's necessary for the Java XML parser; not sure if it's sufficient in all cases.
	 */
	private static String collectString(BufferedReader in) throws Exception {
		String str = "", line;
		while ((line = in.readLine()) != null) {
			str += line.replace('&','+') + "\n";
		}
		in.close();	
		return str;
	}

	/**
	 * Returns the value for the attribute of the given name in the node, "" if not found.
	 */
	private static String attribute(Node n, String name) {
		NamedNodeMap atts = n.getAttributes();
		for (int i = 0; i < atts.getLength(); i++) {
			Node att = atts.item(i);
			if (att.getNodeName() == name) {
				return att.getNodeValue();
			}
		}
		return "";
	}

	public static void main(String[] args) throws Exception {
		// Get the XML document as a string
		BufferedReader in = new BufferedReader(new FileReader("inputs/flickr-dart.xml"));
		String xml = collectString(in);

		// Parse it, following Oracle example
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource source = new InputSource();
		source.setCharacterStream(new StringReader(xml));
		Document doc = builder.parse(source);

		// Loop over all photo elements
		NodeList photos = doc.getElementsByTagName("photo");
		for (int i = 0; i < photos.getLength(); i++) {
			Node n = photos.item(i);
			System.out.println(attribute(n, "title") + " - http://farm" + attribute(n, "farm") +
					".staticflickr.com/" + attribute(n, "server") + "/" +
					attribute(n, "id") + "_" + attribute(n, "secret") + "_z.jpg");
		}
	}
}
