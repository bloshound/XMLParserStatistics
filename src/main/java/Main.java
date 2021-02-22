import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
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

            util.findCoincidences(buffIS, "item", 2);

        } catch (IOException | XMLStreamException e) {
            e.printStackTrace();
        }

    }


    private static void findHouseFloors(){
        XMLEventReader reader = util.

        try(BufferedInputStream buffIS = new BufferedInputStream(new FileInputStream("D:\\address.xml"))) {

         HashMap<String, Integer> elements =  util.getUniqueElements(buffIS, "item");

         for(Map.Entry<String, Integer> pair: elements.entrySet()){

         }




        } catch (IOException | XMLStreamException e) {
            e.printStackTrace();
        }



    }



}
