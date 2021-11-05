package OOP_Lab_8.program.core.parser;

import OOP_Lab_8.program.domain.entity.Tariff;
import OOP_Lab_8.program.domain.exception.TariffParseException;

import java.io.File;
import java.util.ArrayList;

/**
 * Represents tariff xml parser.
 */
public interface ITariffXmlParser {
    /**
     * Parses xml file with list of tariff entities.
     * Xml file must be valid and must have tariffs with structure,
     * described in the tariff.xsd file.
     * @param xmlFile Xml file with tariffs.
     * @return Sorted by id tariffs from xml file.
     * @throws TariffParseException Be thrown, if parse was unsuccessful.
     */
    ArrayList<Tariff> parse(File xmlFile) throws TariffParseException;
}
