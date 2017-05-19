import org.w3c.dom.*;

/**
 * Created by Oly on 09.04.2017.
 */
public class DocumentWorkerDOM {

    private static void stepThrough(Node start) {
        System.out.println(start.getNodeName() + " = " + start.getNodeValue());
        if (start instanceof Element) {
            NamedNodeMap startAttr = start.getAttributes();
            for (int i = 0; i < startAttr.getLength(); i++) {
                Node attr = startAttr.item(i);
                System.out.println(" Attribute: " + attr.getNodeName() + " = " + attr.getNodeValue());
            }
        }
        for (Node child = start.getFirstChild(); child != null; child = child.getNextSibling()) {
            stepThrough(child);
        }
    }

    /*Recursive*/
    public static void processDocument(Document doc) {
        Element rootEl = doc.getDocumentElement();
        System.out.println("Root element: " + rootEl.getNodeName());
        System.out.println("Child elements: ");
        stepThrough(rootEl);
    }

    public void processValidatedDocument(Document doc) {
        Element root = doc.getDocumentElement();
        NodeList nodes = root.getChildNodes();

        System.out.println("processing Validated document...");

        for (int i = 0; i < nodes.getLength(); i++) {
            Element date = (Element)nodes.item(i);
            System.out.println(date.getTagName() + ": date " + date.getAttribute("date"));

            Element x = (Element)date.getFirstChild();
            System.out.println("\t" + x.getTagName() + ": " + (/*(Text)*/ x.getNodeValue())); //getFirstChild()).getData().trim());

            Element y = (Element)x.getNextSibling();
            System.out.println("\t" + y.getTagName() + ": " + ((Text)y.getFirstChild()).getData().trim());
        }
    }

    public static void getInformation(Document doc) {
        NodeList nodeListX = doc.getDocumentElement().getElementsByTagName("x");
        NodeList nodeListY = doc.getDocumentElement().getElementsByTagName("y");
        if (nodeListX.getLength() == nodeListY.getLength()) {
            for (int i = 0; i < nodeListX.getLength(); i++) {
                System.out.println(nodeListX.item(i).getNodeName() + " "
                        + nodeListX.item(i).getTextContent() + "\t"
                        + nodeListY.item(i).getNodeName() + " "
                        + nodeListY.item(i).getTextContent());
            }
        }
    }
}
