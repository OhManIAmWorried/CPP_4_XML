import org.xml.sax.Attributes;
import org.xml.sax.SAXParseException;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/**
 * Created by Oly on 02.04.2017.
 */
public class DataHandler extends DefaultHandler {
    private DataSheetSAX datasheet = null;
    private Data tmpData = null;
    private boolean isX, isY;

    public DataSheetSAX getDataSheet() {
        return datasheet;
    }

    public void setDataSheet(DataSheetSAX dsh) {
        this.datasheet = dsh;
    }

    @Override
    public void startDocument() throws SAXException {
        System.out.println("String Document Parsing Process ...");
        if (datasheet == null){
            datasheet = new DataSheetSAX("New DataSheetSAX");
        }
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("\tData structure received");
        System.out.println("\t"+datasheet.getEquationString());
        System.out.println("End Document Parsing Process ...");
    }

    @Override
    public void startElement(String uri, String name, String qName, Attributes attrs) throws SAXException {
        if (qName.equals("data")) {
            tmpData = new Data();
            if (attrs.getLength() > 0) {
                tmpData.setDate(attrs.getValue(0));
            }
        } else if (qName.equals("x")) {
            isX = true;
        } else if (qName.equals("y")) {
            isY = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("x")) {
            isX = false;
        } else if (qName.equals("y")) {
            isY = false;
        } else if (qName.equals("data")) {
            datasheet.addData(tmpData);
            tmpData = null;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String str = new String(ch,start,length).trim();
        if (isX) {
            tmpData.setX(Double.parseDouble(str));
        } else if (isY) {
            tmpData.setY(Double.parseDouble(str));
        }
    }

    @Override
    public void warning(SAXParseException ex) {
        System.err.println("Warning: " + ex);
        System.err.println("line = " + ex.getLineNumber() + " col = " + ex.getColumnNumber());
    }

    @Override
    public void error(SAXParseException ex) {
        System.err.println("Error: " + ex);
        System.err.println("line = " + ex.getLineNumber() + " col = " + ex.getColumnNumber());
    }

    @Override
    public void fatalError(SAXParseException ex) {
        System.err.println("Fatal error: " + ex);
        System.err.println("line = " + ex.getLineNumber() + " col = " + ex.getColumnNumber());
    }
}
