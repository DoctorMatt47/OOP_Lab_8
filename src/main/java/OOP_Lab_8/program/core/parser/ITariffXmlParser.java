package OOP_Lab_8.program.core.parser;

import OOP_Lab_8.program.domain.entity.Tariff;
import OOP_Lab_8.program.domain.exception.TariffParseException;

import java.io.File;
import java.util.ArrayList;

public interface ITariffXmlParser {
    ArrayList<Tariff> parse(File xmlFile) throws TariffParseException;
}
