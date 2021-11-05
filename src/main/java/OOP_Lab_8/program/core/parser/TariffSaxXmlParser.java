package OOP_Lab_8.program.core.parser;

import OOP_Lab_8.program.core.builder.ITariffBuilder;
import OOP_Lab_8.program.core.sorter.TariffComparator;
import OOP_Lab_8.program.domain.entity.Tariff;
import OOP_Lab_8.program.domain.exception.TariffParseException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents tariff xml parser. Uses sax way to parse xml file.
 */
@SuppressWarnings("ClassCanBeRecord")
public class TariffSaxXmlParser implements ITariffXmlParser {
    /**
     * Tariff builder dependency.
     */
    private final ITariffBuilder builder;

    /**
     * Constructs tariff sax xml parser service.
     * @param builder Tariff builder dependency.
     */
    @Contract(pure = true)
    public TariffSaxXmlParser(ITariffBuilder builder) {
        this.builder = builder;
    }

    /**
     * Parses xml file with list of tariff entities.
     * Uses sax way to parse xml file.
     * Xml file must be valid and must have tariffs with structure,
     * described in the tariff.xsd file.
     * @param xmlFile Xml file with tariffs.
     * @return Sorted by id tariffs from xml file.
     * @throws TariffParseException Be thrown, if parse was unsuccessful.
     */
    @Override
    public ArrayList<Tariff> parse(File xmlFile) throws TariffParseException {
        var tariffs = new ArrayList<Tariff>();
        try {
            SAXParserFactory.newInstance().newSAXParser().parse(xmlFile, new XmlHandler(builder, tariffs));
        } catch (Exception e) {
            throw new TariffParseException(e.getMessage());
        }
        tariffs.sort(new TariffComparator());
        return tariffs;
    }

    /**
     * Nested class for sax parse method events handler.
     */
    private static class XmlHandler extends DefaultHandler {
        private final Map<String, String> dict = new HashMap<>();
        private final ITariffBuilder builder;
        private final ArrayList<Tariff> tariffs;

        /**
         * Current element name.
         */
        private String elementName;

        /**
         * Constructs xml event handler.
         * @param builder Tariff builder dependency.
         * @param tariffs List of tariffs to be filled.
         */
        public XmlHandler(ITariffBuilder builder, ArrayList<Tariff> tariffs) {
            this.builder = builder;
            this.tariffs = tariffs;
        }

        @Override
        public void startElement(String uri, String localName, @NotNull String qName, Attributes attributes) {
            if (qName.equals("tariff")) {
                dict.put("id", attributes.getValue("id"));
                return;
            }
            elementName = qName;
        }

        @Override
        public void endElement(String uri, String localName, @NotNull String qName) {
            if (qName.equals("tariff")) {
                tariffs.add(builder.build(dict));
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            var value = new String(ch, start, length).replace("\n", "").trim();
            if (value.isEmpty()) return;
            dict.put(elementName, value);
        }

    }
}
