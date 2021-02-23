import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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


    //2 метода - получение уникальных элементов c колличеством повторений. ПОлучаем String так как определен HashCode
    public HashMap<String, Long> getUniqueStartElements(InputStream is, String name) throws XMLStreamException {
        XMLEventReader reader = createReader(is);
        return getUniqueStartElements(reader, name);
    }

    public HashMap<String,Long> getUniqueStartElements(XMLEventReader reader, String name) throws XMLStreamException {
        QName qName = new QName(name);

        Stream.Builder<String> builder = Stream.builder();

        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();

            if (event.isStartElement() && event.asStartElement().getName().equals(qName)) {
                String startElement = event.toString();
                builder.add(startElement);
            }
        }

      return builder.build()
               .collect(Collectors.groupingBy(Function.identity(), HashMap::new, Collectors.counting()));

    }

    public void findCoincidences(InputStream is, String name, int coincidenceLevel) throws XMLStreamException {
        findCoincidences(createReader(is), name, coincidenceLevel);
    }

    public void findCoincidences(XMLEventReader reader, String name, int coincidenceLevel) throws XMLStreamException {
        getUniqueStartElements(reader, name).entrySet().stream()
                .filter(pair -> pair.getValue() >= coincidenceLevel)
                .sorted(Map.Entry.comparingByValue())
                .forEach(pair -> System.out.println(pair.getKey() + " колличество совпадений: " + pair.getValue()));
    }
}

