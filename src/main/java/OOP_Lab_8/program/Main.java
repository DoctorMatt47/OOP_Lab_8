package OOP_Lab_8.program;

import OOP_Lab_8.program.core.parser.TariffDomParser;
import OOP_Lab_8.program.domain.exception.TariffParseException;

import javax.management.modelmbean.XMLParseException;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        var domParser = new TariffDomParser();
        try {
            domParser.parse(new File("src/main/resources/tariffs.xml"));
        } catch (TariffParseException e) {
            System.out.println(e.getMessage());
        }
    }
}
