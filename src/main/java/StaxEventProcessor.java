import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;

/**
 * created by bloshound
 * bloshound@gmail.com
 */

public class StaxEventProcessor implements AutoCloseable {
    private static final XMLInputFactory FACTORY = XMLInputFactory.newInstance();

    private final XMLEventReader reader;

    public StaxEventProcessor(InputStream is) throws XMLStreamException {
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