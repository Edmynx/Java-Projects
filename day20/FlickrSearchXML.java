import java.awt.*;

import java.awt.image.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.xml.sax.InputSource;
import org.w3c.dom.*;

/**
 * A GUI to search Flickr for images and step through 10 of them
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012; revised Winter 2014
 */
public class FlickrSearchXML extends JFrame {
	private static final int imageWidth = 640, imageHeight = 640;	// medium 640 on flickr
	private static String api_key = "809275a3e033d9823d5e17700e81be21";

	private JComponent canvas;										// drawing component
	private JTextField queryF;										// GUI text field for query
	private String sort = "relevance";								// how to sort when search
	private ArrayList<BufferedImage> images;						// loaded images, using Java type
	private int curr = 0;											// index of currently-displayed image

	public FlickrSearchXML() {
		super("Flickr Search");

		// Initially no images
		images = new ArrayList<BufferedImage>();

		// Create our graphics-handling component, sized to hold the images
		canvas = new JComponent() {
			public void paintComponent(Graphics g) { //called on repaint()
				super.paintComponent(g);
				if (images.size() > 0) {
					g.drawImage(images.get(curr), 0, 0, null);
				}
			}
		};
		canvas.setPreferredSize(new Dimension(imageWidth, imageHeight));

		// Create the GUI components
		JPanel gui = setupGUI();

		// Put the GUI and the canvas in the panel, one at the top and one taking the rest of the space
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(gui, BorderLayout.NORTH);
		cp.add(canvas, BorderLayout.CENTER);

		// Boilerplate
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	/**
	 * Creates all the GUI components and adds them to a panel
	 * @return	the panel holding the components
	 */
	private JPanel setupGUI() {
		// prev button steps backward through images
		JButton prevB = new JButton("prev");
		prevB.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (images.size() > 0) {
					curr--;
					if (curr < 0) curr = images.size() - 1;
					repaint();
				}
			}
		});

		// prev button steps forward through images
		JButton nextB = new JButton("next");
		nextB.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if (images.size() > 0) {
					curr = (curr + 1) % images.size();
					repaint();
				}
			}
		});

		// sort dropdown (combobox) lists possible ways to sort
		String[] sortOrders = { "relevance", "interestingness-desc", "interestingness-asc", 
				"date-taken-desc", "date-taken-asc" };
		JComboBox sortB = new JComboBox(sortOrders);
		sortB.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				sort = (String)((JComboBox)e.getSource()).getSelectedItem();
				System.out.println(sort);
			}
		});

		// text field for the search query
		queryF = new JTextField(20);

		// search button fires off the search
		JButton search = new JButton("search");
		search.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("searching for '" + queryF.getText() + "' by " + sort);
				try {
					loadImages(queryF.getText());
					curr = 0;
					repaint();
				}
				catch (Exception ex) {
					System.err.println("search failed");
				}
			}
		});

		// Put all the components in a panel
		JPanel gui = new JPanel();
		gui.setLayout(new FlowLayout());
		gui.add(queryF);
		gui.add(sortB);
		gui.add(search);
		gui.add(new JSeparator(SwingConstants.VERTICAL));
		gui.add(prevB);
		gui.add(nextB);

		return gui;
	}

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

	/**
	 * Performs a Flickr search for the query and loads all the images returned
	 */
	private void loadImages(String query) throws Exception {
		// Get rid of existing images
		images.clear();

		// Build the REST query as specified in the Flickr API
		String request = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=" + api_key +
				"&text=" + URLEncoder.encode(query,"UTF-8") + "&sort=" + sort + "&per_page=10";
		System.out.println("search:" + request);

		// Get the XML document as a string
		//BufferedReader in = new BufferedReader(new FileReader("inputs/test.xml"));
		BufferedReader in = new BufferedReader(new InputStreamReader(new URL(request).openStream()));
		String xml = collectString(in);

		// Parse XML, following Oracle example
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource source = new InputSource();
		source.setCharacterStream(new StringReader(xml));
		Document doc = builder.parse(source);

		// Loop over all photo elements
		NodeList photos = doc.getElementsByTagName("photo");
		for (int i = 0; i < photos.getLength(); i++) {
			Node n = photos.item(i);
			try {
				// Build the image URL as specified in the Flickr API
				String url = "http://farm" + attribute(n, "farm") + ".staticflickr.com/" + 
						attribute(n, "server") + "/" + attribute(n, "id") + "_" + 
						attribute(n, "secret") + "_z.jpg"; //_z means size=640
				System.out.println(attribute(n, "title") + " - " + url);
				
				//fetch image at URL and add to images
				BufferedImage img = ImageIO.read(new URL(url));
				images.add(img);
			}
			catch (IOException e) {
				System.out.println("couldn't load image");
			}
		}
	}

	/**
	 * Main method for the application
	 * @param args		command-line arguments (ignored)
	 */
	public static void main(String[] args) { 
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new FlickrSearchXML();
			}
		});
	}
}
