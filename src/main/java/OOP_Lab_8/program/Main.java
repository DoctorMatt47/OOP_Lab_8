package OOP_Lab_8.program;

import OOP_Lab_8.program.core.parser.TariffSaxParser;
import OOP_Lab_8.program.domain.exception.TariffParseException;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        var domParser = new TariffSaxParser();
        try {
            var tariffs = domParser.parse(new File("src/main/resources/tariffs.xml"));
            for (var tariff : tariffs) {
                System.out.println(tariff);
            }
        } catch (TariffParseException e) {
            System.out.println(e.getMessage());
        }
    }
}
