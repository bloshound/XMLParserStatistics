import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * created by bloshound
 * bloshound@gmail.com
 */

public class Main {

    static StatsUtil util = StatsUtil.INSTANCE;


    public static void main(String[] args) {

        try(BufferedInputStream buffIS = new BufferedInputStream(new FileInputStream("D:\\address.xml"))) {

            XMLEventReader reader = util.createReader(buffIS);
            util.findCoincidences(reader, "item", 2);

        } catch (IOException | XMLStreamException e) {
            e.printStackTrace();
        }

    }


    private static void findHouseFloors(XMLEventReader reader, S){
        StringBuilder sb = new StringBuilder();
        int[] floors = {1, 1, 1, 1, 1};

        try(BufferedInputStream buffIS = new BufferedInputStream(new FileInputStream("D:\\address.xml"))) {
            XMLEventReader reader = util.createReader(buffIS);

            while (reader.hasNext()){

                StartElement element = util.getStartElementByName(reader, "item"){

                }

            }





        } catch (IOException | XMLStreamException e) {
            e.printStackTrace();
        }



    }



}
