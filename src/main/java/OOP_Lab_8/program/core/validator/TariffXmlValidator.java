package OOP_Lab_8.program.core.validator;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class TariffXmlValidator {
    private static final File SCHEMA_FILE = new File("src" + File.separator +
            "main" + File.separator + "resources" + File.separator + "site.xsd");

    private final SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

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