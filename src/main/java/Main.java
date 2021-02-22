import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * created by bloshound
 * bloshound@gmail.com
 */

public class Main {

    static StatsUtil util = StatsUtil.INSTANCE;


    public static void main(String[] args) {

        try (BufferedInputStream buffIS = new BufferedInputStream(new FileInputStream("D:\\address.xml"))) {

            XMLEventReader reader = util.createReader(buffIS);
            util.findCoincidences(reader, "item", 2);

        } catch (IOException | XMLStreamException e) {
            e.printStackTrace();
        }

    }


    private static void findHouseFloors() {

        int[] floors = {1, 1, 1, 1, 1};

        QName cityQName = new QName("city");
        QName floorQName = new QName("floor");

        HashMap<String, int[]> floorHousesByCity = new HashMap<>();

        try (BufferedInputStream buffIS = new BufferedInputStream(new FileInputStream("D:\\address.xml"))) {
            XMLEventReader reader = util.createReader(buffIS);

            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if (event.isStartElement()
                        && event.asStartElement().getAttributeByName(cityQName) != null
                        && event.asStartElement().getAttributeByName(floorQName) != null) {

                }

            }


        } catch (IOException | XMLStreamException e) {
            e.printStackTrace();
        }


    }


}
