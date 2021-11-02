package OOP_Lab_8.program.core.parser;

import OOP_Lab_8.program.core.builder.ITariffBuilder;
import OOP_Lab_8.program.domain.entity.CallPrice;
import OOP_Lab_8.program.domain.entity.Parameters;
import OOP_Lab_8.program.domain.entity.Tariff;
import OOP_Lab_8.program.domain.entity.Tariffication;
import OOP_Lab_8.program.domain.exception.TariffParseException;
import org.jetbrains.annotations.NotNull;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("ClassCanBeRecord")
public class TariffStaxParser implements ITariffParser {
    private final ITariffBuilder builder;

    public TariffStaxParser(ITariffBuilder builder) {
        this.builder = builder;
    }

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

    private ArrayList<Tariff> parseCore(@NotNull XMLEventReader reader) throws XMLStreamException {
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
        return tariffs;
    }
}
