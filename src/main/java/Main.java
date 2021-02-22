import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * created by bloshound
 * bloshound@gmail.com
 */

public class Main {

    static StatsUtil util = StatsUtil.INSTANCE;

    public static void main(String[] args) {
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {

            System.out.print("Введите путь к файлу: ");
            try (BufferedInputStream fileStream = new BufferedInputStream(new FileInputStream(consoleReader.readLine()))) {


            } catch (FileNotFoundException e) {
                System.out.println("Не удается найти указзанный файл.");

            } catch (IOException e) {
                System.out.println("Ошибка чтения.");


            }
        }
    }


    private static void findHouseFloors() {

        QName cityQName = new QName("city");
        QName floorQName = new QName("floor");

        BiFunction<int[], int[], int[]> mergeIntArrays = (ints1, ints2) -> {
            if (ints1.length != ints2.length) throw new IllegalArgumentException("Массивы разного размера");

            int[] result = new int[ints1.length];
            for (int i = 0; i < ints1.length; i++) {
                result[i] = ints1[i] + ints2[i];
            }

            return result;
        };


        HashMap<String, int[]> floorHousesByCity = new HashMap<>();

        try (BufferedInputStream buffIS = new BufferedInputStream(new FileInputStream("D:\\address.xml"))) {
            XMLEventReader reader = util.createReader(buffIS);

            while (reader.hasNext()) {
                int[] floors = new int[6];

                XMLEvent event = reader.nextEvent();

                if (event.isStartElement()
                        && event.asStartElement().getAttributeByName(cityQName) != null
                        && event.asStartElement().getAttributeByName(floorQName) != null) {

                    StartElement startElement = event.asStartElement();
                    String city = startElement.getAttributeByName(cityQName).getValue();
                    int floor = Integer.parseInt(startElement.getAttributeByName(floorQName).getValue());

                    floors[floor] = floors[floor] + 1;
                    floorHousesByCity.merge(city, floors, mergeIntArrays);
                }
            }

            floorHousesByCity.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(pair -> System.out.println(
                    "город " + pair.getKey() + ": " + Arrays.toString(pair.getValue())));

        } catch (IOException | XMLStreamException e) {
            e.printStackTrace();
        }
    }
}
