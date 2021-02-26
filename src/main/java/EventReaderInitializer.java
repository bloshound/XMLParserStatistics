import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.InputStream;


/**
 * created by bloshound
 * bloshound@gmail.com
 */

public class EventReaderInitializer implements AutoCloseable {
    private static final XMLInputFactory FACTORY = XMLInputFactory.newInstance();

    private final XMLEventReader reader;

    public EventReaderInitializer(InputStream is) throws XMLStreamException {
        this.reader = FACTORY.createXMLEventReader(is);
    }


    public XMLEventReader getReader() {
        return reader;
    }

   @Override
    public void close()  {
        if (reader != null) {
            try {
                reader.close();
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }
        }
    }
}