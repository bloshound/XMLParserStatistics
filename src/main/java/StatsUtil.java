import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

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

    public XMLEventReader createReader(Reader r) throws XMLStreamException {
        return new StaxEventProcessor(r).getReader();
    }


    //2 метода - получение уникальных элементов c колличеством повторений
    public HashMap<String, Integer> getUniqueElements(InputStream is, String name) throws XMLStreamException {
        XMLEventReader reader = createReader(is);
        return getUniqueElements(reader, name);
    }

    public HashMap<String, Integer> getUniqueElements(Reader r, String name) throws XMLStreamException {
        XMLEventReader reader = createReader(r);
        return getUniqueElements(reader, name);
    }

    public HashMap<String, Integer> getUniqueElements(XMLEventReader reader, String name) throws XMLStreamException {

        QName qName = new QName(name);
        HashMap<String, Integer> startElements = new HashMap<>();

        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();
            if (event.isStartElement() && event.asStartElement().getName().equals(qName)) {
                String element = event.asStartElement().toString();

                startElements.merge(element, 1, Integer::sum);
            }
        }
        return startElements;
    }


    public void findCoincidences(InputStream is, String name, int coincidenceLevel) throws XMLStreamException {
        supportFindCoincidences(getUniqueElements(is, name), coincidenceLevel);
    }

    public void findCoincidences(Reader r, String name, int coincidenceLevel) throws XMLStreamException {
      supportFindCoincidences(getUniqueElements(r, name), coincidenceLevel);
    }

    public void findCoincidences(XMLEventReader reader, String name, int coincidenceLevel) throws XMLStreamException {
        supportFindCoincidences(getUniqueElements(reader, name), coincidenceLevel);
    }


    private void supportFindCoincidences(HashMap<String, Integer> map, int coincidenceLevel){
         map.entrySet().stream()
                .filter(pair -> pair.getValue() >= coincidenceLevel)
                .sorted(Map.Entry.comparingByValue())
                .forEach(pair -> System.out.println(pair.getKey() + " колличество совпадений: " + pair.getValue()));
    }
}

