package OOP_Lab_8.program.core.parser;

import OOP_Lab_8.program.core.builder.TariffBuilder;
import OOP_Lab_8.program.domain.entity.Tariff;
import OOP_Lab_8.program.domain.exception.TariffParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TariffDomXmlParserTest {

    @Test
    void parse() {
        ArrayList<Tariff> tariffs = null;
        try {
            tariffs = new TariffDomXmlParser(new TariffBuilder())
                    .parse(new File("src/test/resources/valid-tariffs.xml"));
        } catch (TariffParseException e) {
            Assertions.fail();
        }
        Assertions.assertNotNull(tariffs);
        Assertions.assertFalse(tariffs.isEmpty());
    }
}