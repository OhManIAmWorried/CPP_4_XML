import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Created by Oly on 09.04.2017.
 */
public class MyErrorHandler implements ErrorHandler {

    @Override
    public void warning(SAXParseException e) throws SAXException {
        System.err.println("Warning: " + e);
        System.err.println("line = " + e.getLineNumber() + " col = " + e.getColumnNumber());
    }

    @Override
    public void error(SAXParseException e) throws SAXException {
        System.err.println("Error: " + e);
        System.err.println("line = " + e.getLineNumber() + " col = " + e.getColumnNumber());
    }

    @Override
    public void fatalError(SAXParseException e) throws SAXException {
        System.err.println("Fatal error: " + e);
        System.err.println("line = " + e.getLineNumber() + " col = " + e.getColumnNumber());
    }
}
