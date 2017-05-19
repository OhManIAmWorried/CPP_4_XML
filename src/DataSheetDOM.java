import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Created by Oly on 09.04.2017.
 */
public class DataSheetDOM extends DataSheet {

    private Document doc;

    private ArrayList<Data> dataArrayList;

    public DataSheetDOM(Document doc) {
        super();
        dataArrayList = new ArrayList<Data>();
        this.doc = doc;
    }

    public DataSheetDOM() {
        this(null);
    }

    public Document getDoc() {
        return doc;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }

    public int numData() {
        return doc.getDocumentElement().getElementsByTagName("data").getLength();
    }

    public double getX(int pos) {
        String s = doc.getDocumentElement().getElementsByTagName("x").item(pos).getTextContent();
        return Double.parseDouble(s);
    }

    public void setX(int pos, double val) {
        doc.getDocumentElement().getElementsByTagName("x").item(pos).setTextContent(val+"");
        Data data = dataArrayList.get(pos);
        data.setX(val);
        dataArrayList.set(pos,data);
    }

    public double getY(int pos) {
        String s = doc.getDocumentElement().getElementsByTagName("y").item(pos).getTextContent();
        return Double.parseDouble(s);
    }

    public void setY(int pos, double val) {
        doc.getDocumentElement().getElementsByTagName("y").item(pos).setTextContent(val+"");
        Data data = dataArrayList.get(pos);
        data.setY(val);
        dataArrayList.set(pos,data);
    }

    public Element newElement(String date, double x, double y) {
        Element data = doc.createElement("data");
        Attr attr = doc.createAttribute("date");
        attr.setValue(date.trim());
        data.setAttributeNode(attr);
        Element elemX = doc.createElement("x");
        elemX.appendChild(doc.createTextNode(x+""));
        data.appendChild(elemX);
        Element elemY = doc.createElement("y");
        elemY.appendChild(doc.createTextNode(y+""));
        data.appendChild(elemY);
        return data;
    }

    private Data createData(Element data) {
        Data resdata = new Data();
        resdata.setDate(data.getAttributeNode("date").getValue());
        resdata.setX(Double.parseDouble(data.getElementsByTagName("x").item(0).getNodeValue()));
        resdata.setY(Double.parseDouble(data.getElementsByTagName("y").item(0).getNodeValue()));
        return resdata;
    }

    public void addElement(Element data) {
        this.doc.getDocumentElement().appendChild(data);
        dataArrayList.add(createData(data));
    }

    public void removeElement(int pos) {
        Node el = doc.getDocumentElement().getElementsByTagName("data").item(pos);
        doc.getDocumentElement().removeChild(el);
        dataArrayList.remove(pos);
    }

    public void insertElementAt(int pos, Node nd) {
        Node el = doc.getDocumentElement().getElementsByTagName("data").item(pos);
        doc.getDocumentElement().insertBefore(nd, el);
        dataArrayList.add(pos,createData((Element)nd));
    }

    public void replaceElementAt(int pos, Node nd) {
        Node el = doc.getDocumentElement().getElementsByTagName("data").item(pos);
        doc.getDocumentElement().replaceChild(nd, el);
        dataArrayList.remove(pos);
        dataArrayList.add(pos,createData((Element)nd));
    }

    public void writeXML(String path) {
        TransformerFactory transFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = transFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            File newXMLFile = new File(path);
            FileOutputStream fos = null;
            fos = new FileOutputStream(newXMLFile);
            StreamResult result = new StreamResult(fos);
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Data> getDataArrayList() {
        return dataArrayList;
    }
}
