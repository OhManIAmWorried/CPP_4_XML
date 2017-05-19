import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Oly on 10.04.2017.
 */
public class XMLFileWriter {
    private String path;

    public XMLFileWriter(String path) {
        this.path = path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private static Element getElement(String date, double thatX, double thatY, Document doc) {
        Element res = doc.createElement("data");
        Attr attr = doc.createAttribute("date");
        attr.setValue(date.trim());
        res.setAttributeNode(attr);

        Element x = doc.createElement("x");
        x.appendChild(doc.createTextNode(thatX + ""));
        res.appendChild(x);

        Element y = doc.createElement("y");
        y.appendChild(doc.createTextNode(thatY + ""));
        res.appendChild(y);

        return res;
    }

    public static void writeDataSheet(DataSheet datasheet) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            Element root = document.createElement("datasheet");
            document.appendChild(root);

            for (Data data : datasheet.getDataArrayList()) {
                Element element = getElement(data.getDate(),data.getX(),data.getY(),document);
                document.getDocumentElement().appendChild(element);
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
}
