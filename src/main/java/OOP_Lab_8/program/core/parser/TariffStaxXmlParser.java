package OOP_Lab_8.program.core.parser;

import OOP_Lab_8.program.core.builder.ITariffBuilder;
import OOP_Lab_8.program.core.sorter.TariffComparator;
import OOP_Lab_8.program.domain.entity.Tariff;
import OOP_Lab_8.program.domain.exception.TariffParseException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents tariff xml parser. Uses stax way to parse xml file.
 */
@SuppressWarnings("ClassCanBeRecord")
public class TariffStaxXmlParser implements ITariffXmlParser {
    /**
     * Tariff builder dependency.
     */
    private final ITariffBuilder builder;

    /**
     * Constructs tariff stax xml parser service.
     * @param builder Tariff builder dependency.
     */
    @Contract(pure = true)
    public TariffStaxXmlParser(ITariffBuilder builder) {
        this.builder = builder;
    }

    /**
     * Parses xml file with list of tariff entities.
     * Uses stax way to parse xml file.
     * Xml file must be valid and must have tariffs with structure,
     * described in the tariff.xsd file.
     * @param xmlFile Xml file with tariffs.
     * @return Sorted by id tariffs from xml file.
     * @throws TariffParseException Be thrown, if parse was unsuccessful.
     */
    @Override
    public ArrayList<Tariff> parse(File xmlFile) throws TariffParseException {
        try {
            var eventReader = XMLInputFactory.newInstance()
                    .createXMLEventReader(new FileReader(xmlFile));
            return parseCore(eventReader);
        } catch (Exception e) {
            throw new TariffParseException(e.getMessage());
        }
    }

    /**
     * Parses xml file, uses stax way.
     * @param reader Event reader.
     * @return Sorted by id tariffs from xml file.
     * @throws XMLStreamException Invalid xml exception.
     */
    private @NotNull ArrayList<Tariff> parseCore(@NotNull XMLEventReader reader) throws XMLStreamException {
        var tariffs = new ArrayList<Tariff>();
        var dict = new HashMap<String, String>();
        while (reader.hasNext()) {
            var nextEvent = reader.nextEvent();
            if (nextEvent.isStartElement()) {
                var startElement = nextEvent.asStartElement();
                var elementName = startElement.getName().getLocalPart();
                if (elementName.equals("tariff")) {
                    var id = startElement.getAttributeByName(new QName("id")).getValue();
                    dict.put("id", id);
                } else {
                    nextEvent = reader.nextEvent();
                    var value = nextEvent.asCharacters().getData().replace("\n", "").trim();
                    if (!value.isEmpty()) {
                        dict.put(startElement.getName().getLocalPart(), value);
                    }
                }
            }
            if (nextEvent.isEndElement()) {
                var startElement = nextEvent.asEndElement();
                var elementName = startElement.getName().getLocalPart();
                if (elementName.equals("tariff")) {
                    tariffs.add(builder.build(dict));
                }
            }
        }
        tariffs.sort(new TariffComparator());
        return tariffs;
    }
}
