package OOP_Lab_8.program.core.parser;

import OOP_Lab_8.program.core.builder.ITariffBuilder;
import OOP_Lab_8.program.domain.entity.Tariff;
import OOP_Lab_8.program.domain.exception.TariffParseException;
import org.jetbrains.annotations.NotNull;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ClassCanBeRecord")
public class TariffSaxXmlParser implements ITariffXmlParser {
    private final ITariffBuilder builder;

    public TariffSaxXmlParser(ITariffBuilder builder) {
        this.builder = builder;
    }

    @Override
    public ArrayList<Tariff> parse(File xmlFile) throws TariffParseException {
        var tariffs = new ArrayList<Tariff>();
        try {
            SAXParserFactory.newInstance().newSAXParser().parse(xmlFile, new XmlHandler(builder, tariffs));
        } catch (Exception e) {
            throw new TariffParseException(e.getMessage());
        }
        return tariffs;
    }

    private static class XmlHandler extends DefaultHandler {
        private final Map<String, String> dict = new HashMap<>();
        private final ITariffBuilder builder;
        private final ArrayList<Tariff> tariffs;

        private String elementName;

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
