import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.util.HashMap;

/**
 * created by bloshound
 * bloshound@gmail.com
 */

public class StatsUtil {
    public static final StatsUtil INSTANCE = new StatsUtil();

    private StatsUtil() {
    }

    //получение XML ридера
    public XMLEventReader createReader(InputStream is) throws XMLStreamException {
        return new StaxEventProcessor(is).getReader();
    }


    //2 метода - получение уникальных элементов c колличеством повторений
    public HashMap<String, Integer> getUniqueElements(InputStream is, String name) throws XMLStreamException {
        XMLEventReader reader = createReader(is);
        return getUniqueElements(reader, name);
    }

    public HashMap<String, Integer> getUniqueElements(XMLEventReader reader, String name) throws XMLStreamException {

        QName qName = new QName(name);
        HashMap<String, Integer> elements = new HashMap<>();

        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();
            if (event.isStartElement() && event.asStartElement().getName().equals(qName)) {
                String element = event.asStartElement().toString();

                elements.put(element, elements.getOrDefault(element, 0) + 1);
            }
        }
        return elements;
    }


    public void findCoincidences() {

    }
}

