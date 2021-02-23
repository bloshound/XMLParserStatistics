import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * created by bloshound
 * bloshound@gmail.com
 */

public class Main {

    static StatsUtil util = StatsUtil.INSTANCE;

    public static void main(String[] args) {

        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        String input;

        String needElement = "item";
        int coincidenceLevel = 2;

        while (true) {
            System.out.print("Для выхода наберите <exit>. Для запуска программы - введите путь к файлу: ");
            try {
                input = consoleReader.readLine();
                if (input.equalsIgnoreCase("exit")) {
                    System.out.println("Выход из программы");
                    break;
                }

                try (BufferedInputStream fileStream = new BufferedInputStream(new FileInputStream(input))) {

                    util.findCoincidences(fileStream, needElement, coincidenceLevel);

                    findHouseFloors(input);
                    findHouseFloorsOnStream(input);

                } catch (FileNotFoundException e) {
                    System.out.println(e.getMessage());

                } catch (XMLStreamException e) {
                    System.out.println(e.getMessage());
                }

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void findHouseFloors(final String input) throws IOException, XMLStreamException {
        System.out.println("Список городов с 1, 2, 3, 4, 5 - этажными зданиями:");

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

        try (BufferedInputStream buffIS = new BufferedInputStream(new FileInputStream(input))) {
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

            floorHousesByCity.forEach((key, value) -> System.out.println(
                    "город " + key + ": " + Arrays.toString(value)));
        }
        System.out.println("<------------------------------------------------------------>");
    }

    private static void findHouseFloorsOnStream(final String input) throws IOException, XMLStreamException {
        System.out.println("Список городов с 1, 2, 3, 4, 5 - этажными зданиями:");

        QName cityQName = new QName("city");
        QName floorQName = new QName("floor");

        Consumer<Map.Entry<String, Map<String, Long>>> printer = entry1Level -> {
            String city = String.format("|%30s|", entry1Level.getKey());
            StringBuilder stringBuilder = new StringBuilder(city).append(": ");
            entry1Level.getValue().forEach((key, value) -> stringBuilder.append(key).append(" - ").append(value).append(", "));
            System.out.println(stringBuilder);
        };

        Stream.Builder<StartElement> builder = Stream.builder();

        try (BufferedInputStream buffIS = new BufferedInputStream(new FileInputStream(input))) {
            XMLEventReader reader = util.createReader(buffIS);

            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();

                if (event.isStartElement()
                        && event.asStartElement().getAttributeByName(cityQName) != null
                        && event.asStartElement().getAttributeByName(floorQName) != null) {

                    StartElement startElement = event.asStartElement();
                    builder.add(startElement);
                }
            }

            builder.build()
                    .collect(Collectors.groupingBy(startElement -> startElement.getAttributeByName(cityQName).getValue(),
                            Collectors.groupingBy(startElement -> startElement.getAttributeByName(floorQName).getValue(), Collectors.counting())))
                    .entrySet().forEach(printer);
        }
        System.out.println("<------------------------------------------------------------>");
    }
}
