import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

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



}
