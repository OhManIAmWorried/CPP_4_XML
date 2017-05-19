import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.*;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;
import java.util.Scanner;

/**
 * Created by Oly on 23.03.2017.
 */
public class MainFrame {
    private static Scanner scanner;
    private static final String filePath = "D:\\Study\\CP programming\\Projects\\Laba_4_XML\\src\\file\\File.xml";
    private static final String filePathXSD = "D:\\Study\\CP programming\\Projects\\Laba_4_XML\\src\\file\\XSDSpecifiedFile.xml";
    private static final String filePathDTD = "D:\\Study\\CP programming\\Projects\\Laba_4_XML\\src\\file\\DTDSpecifiedFile.xml";

    public static void main(String[] args) {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        scanner = new Scanner(System.in);
        String path = filePath;

        while (true) {
            System.out.println("Menu: ");
            System.out.println("0. Exit");
            System.out.println("SAX-----------------------------");
            System.out.println("1. Calculate without validation ");
            System.out.println("2. Calculate with DTD validation");
            System.out.println("3. Calculate with XSD validation");
            System.out.println("DOM-----------------------------");
            System.out.println("4. Calculate without validation ");
            System.out.println("5. Calculate with DTD validation");
            System.out.println("6. Calculate with XSD validation");
            System.out.println("--------------------------------");
            System.out.print("Choice: ");
            int cv = scanner.nextInt();
            switch (cv) {
                case 0: {
                    return;
                }
                case 1: {
                    path = filePath;
                    break;
                }
                case 2: {
                    path = filePathDTD;
                    validateDTD_SAX(saxParserFactory);
                    break;
                }
                case 3: {
                    path = filePath;
                    validateXSD_SAX(saxParserFactory);
                    break;
                }
                case 4: {
                    path = filePath;
                    break;
                }
                case 5: {
                    path = filePathDTD;
                    validateDTD_DOM(documentBuilderFactory);
                    break;
                }
                case 6: {
                    path = filePath;
                    validateXSD_DOM(documentBuilderFactory);
                    break;
                }
            }
            if (cv < 4) parseFile(saxParserFactory, path);
            else {
                parseFile(path, documentBuilderFactory, cv != 4);
            }
        }
    }

/*Common--------------------------------------------------------------------------------------------------------------*/

    private static Schema getSchema() {
        Schema schema = null;
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            schema = factory.newSchema(new File(filePath));
        } catch (org.xml.sax.SAXException e) {
            e.printStackTrace();
        }
        return schema;
    }

/*DOM-----------------------------------------------------------------------------------------------------------------*/

    private static void parseFile(String path, DocumentBuilderFactory documentBuilderFactory, boolean isValidated) {
        try {
            File file = new File(path);
            Document document;
            if (isValidated) {documentBuilderFactory.setIgnoringElementContentWhitespace(true);}
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            if (isValidated) {
                ErrorHandler errorHandler = new MyErrorHandler();
                documentBuilder.setErrorHandler(errorHandler);
            }
            document = documentBuilder.parse(file);
            DocumentWorkerDOM docWorker = new DocumentWorkerDOM();
            if (!isValidated) {
                docWorker.getInformation(document);
                docWorker.processDocument(document);
            } else
                docWorker.processValidatedDocument(document);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void validateDTD_DOM(DocumentBuilderFactory documentBuilderFactory) {
        documentBuilderFactory.setValidating(true);
    }

    private static void validateXSD_DOM(DocumentBuilderFactory documentBuilderFactory) {
        Schema schema = getSchema();
        documentBuilderFactory.setSchema(schema);
        documentBuilderFactory.setNamespaceAware(true);
        documentBuilderFactory.setValidating(false);
    }

/*SAX-----------------------------------------------------------------------------------------------------------------*/

    private static void parseFile(SAXParserFactory saxParserFactory, String path) {
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            DataHandler dataHandler = new DataHandler();
            InputStream xmlInputStream = new FileInputStream(path);
            saxParser.parse(xmlInputStream, dataHandler);
        } catch (org.xml.sax.SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void validateDTD_SAX(SAXParserFactory saxParserFactory) {
        saxParserFactory.setValidating(true);
        saxParserFactory.setNamespaceAware(true);
    }

    private static void validateXSD_SAX(SAXParserFactory saxParserFactory) {
        Schema schema = getSchema();
        saxParserFactory.setSchema(schema);
        saxParserFactory.setNamespaceAware(true);
        saxParserFactory.setValidating(false);
    }
}
