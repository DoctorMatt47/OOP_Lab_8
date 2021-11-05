package OOP_Lab_8.program.core.validator;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;

/**
 * Represents tariff xml validator. Check xml file on schema file accordance.
 */
public class TariffXmlValidator {
    /**
     * Xsd file with the described tariff entity scheme described.
     */
    private static final File SCHEMA_FILE = new File("src/main/resources/tariff.xsd");

    /**
     * Schema factory dependency.
     */
    private final SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

    /**
     * Checks if xml file matches xsd tariff scheme.
     * @param xmlFile File to be checked.
     * @return True if file matches xsd tariff scheme, otherwise false.
     */
    public boolean isValid(File xmlFile) {
        Schema schema;
        try {
            schema = factory.newSchema(SCHEMA_FILE);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }

        var validator = schema.newValidator();
        try {
            validator.validate(new StreamSource(xmlFile));
        } catch (SAXException | IOException e) {
            return false;
        }
        return true;
    }
}