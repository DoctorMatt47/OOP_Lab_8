package OOP_Lab_8.program.core.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class TariffXmlValidatorTest {

    @Test
    void isValid() {
        var tariffValidator = new TariffXmlValidator();
        Assertions.assertTrue(tariffValidator.isValid(new File("src/test/resources/valid-tariffs.xml")));
        Assertions.assertFalse(tariffValidator.isValid(new File("src/test/resources/invalid-tariffs.xml")));
    }
}