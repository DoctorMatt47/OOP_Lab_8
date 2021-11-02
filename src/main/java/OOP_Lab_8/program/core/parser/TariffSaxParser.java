package OOP_Lab_8.program.core.parser;

import OOP_Lab_8.program.domain.entity.CallPrice;
import OOP_Lab_8.program.domain.entity.Parameters;
import OOP_Lab_8.program.domain.entity.Tariff;
import OOP_Lab_8.program.domain.entity.Tariffication;
import OOP_Lab_8.program.domain.exception.TariffParseException;
import org.jetbrains.annotations.NotNull;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TariffSaxParser implements ITariffParser {
    private static final ArrayList<Tariff> tariffs = new ArrayList<>();

    @Override
    public ArrayList<Tariff> parse(File xmlFile) throws TariffParseException {
        try {
            SAXParserFactory.newInstance().newSAXParser().parse(xmlFile, new XmlHandler());
        } catch (Exception e) {
            throw new TariffParseException(e.getMessage());
        }
        return tariffs;
    }

    private static class XmlHandler extends DefaultHandler {
        private final Map<String, String> dict = new HashMap<>();
        private String id;
        private String elementName;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            if (qName.equals("tariff")) {
                id = attributes.getValue("id");
                return;
            }
            elementName = qName;
        }

        @Override
        public void endElement(String uri, String localName, String qName) {
            if (qName.equals("tariff")) {
                TariffSaxParser.tariffs.add(createTariff());
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            var value = new String(ch, start, length).replace("\n", "").trim();
            if (value.isEmpty()) return;
            dict.put(elementName, value);
        }

        private @NotNull Tariff createTariff() {
            var callPrice = createCallPrice();
            var parameters = createParameters();
            var name = dict.get("name");
            var operatorName = dict.get("operatorName");
            var payroll = Integer.parseInt(dict.get("payroll"));
            var smsPrice = Float.parseFloat(dict.get("smsPrice"));

            return new Tariff(id, name, operatorName, payroll, smsPrice, callPrice, parameters);
        }

        private @NotNull CallPrice createCallPrice() {
            var withinTheNetwork = Float.parseFloat(dict.get("withinTheNetwork"));
            var outsideTheNetwork = Float.parseFloat(dict.get("outsideTheNetwork"));
            var toLandlinePhones = Float.parseFloat(dict.get("toLandlinePhones"));

            return new CallPrice(withinTheNetwork, outsideTheNetwork, toLandlinePhones);
        }

        private @NotNull Parameters createParameters() {
            var isFavoriteNumberExist = Boolean.parseBoolean(dict.get("isFavoriteNumberExist"));
            var tariffication = Tariffication.valueOf(dict.get("tariffication"));
            var priceForConnection = Integer.parseInt(dict.get("priceForConnection"));

            return new Parameters(isFavoriteNumberExist, tariffication, priceForConnection);
        }
    }
}
