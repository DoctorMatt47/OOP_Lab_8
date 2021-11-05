package OOP_Lab_8.program;

import OOP_Lab_8.program.core.builder.TariffBuilder;
import OOP_Lab_8.program.core.parser.TariffDomXmlParser;
import OOP_Lab_8.program.core.parser.TariffSaxXmlParser;
import OOP_Lab_8.program.core.parser.TariffStaxXmlParser;
import OOP_Lab_8.program.core.validator.TariffXmlValidator;
import OOP_Lab_8.program.domain.entity.Tariff;
import OOP_Lab_8.program.domain.exception.TariffParseException;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        var domParser = new TariffDomXmlParser(new TariffBuilder());
        var saxParser = new TariffSaxXmlParser(new TariffBuilder());
        var staxParser = new TariffStaxXmlParser(new TariffBuilder());
        try {
            var domTariffs = domParser.parse(new File("src/main/resources/tariffs.xml"));
            var saxTariffs = saxParser.parse(new File("src/main/resources/tariffs.xml"));
            var staxTariffs = staxParser.parse(new File("src/main/resources/tariffs.xml"));

            printTariffs(domTariffs);
            printTariffs(saxTariffs);
            printTariffs(staxTariffs);
        } catch (TariffParseException e) {
            System.out.println(e.getMessage());
        }
        var validator = new TariffXmlValidator();
        var isValidMessage = validator.isValid(new File("src/main/resources/tariffs.xml"))
                ? "Xml is valid" : "Xml is invalid";
        System.out.println(isValidMessage);
    }

    private static void printTariffs(Iterable<Tariff> iterable) {
        for (var elem : iterable) {
            System.out.println(elem);
        }
        System.out.println();
    }
}
