package OOP_Lab_8.program.core.parser;

import OOP_Lab_8.program.core.builder.ITariffBuilder;
import OOP_Lab_8.program.core.sorter.TariffComparator;
import OOP_Lab_8.program.domain.entity.Tariff;
import OOP_Lab_8.program.domain.exception.TariffParseException;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ClassCanBeRecord")
public class TariffDomXmlParser implements ITariffXmlParser {
    private final ITariffBuilder builder;

    public TariffDomXmlParser(ITariffBuilder builder) {
        this.builder = builder;
    }

    @Override
    public ArrayList<Tariff> parse(@NotNull File xmlFile) throws TariffParseException {
        System.out.println(xmlFile.getAbsolutePath());
        try {
            var tariffElements = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(xmlFile)
                    .getDocumentElement()
                    .getElementsByTagName("tariff");
            return iterateTariffNodes(tariffElements);
        } catch (Exception e) {
            throw new TariffParseException(e.getMessage());
        }
    }

    private @NotNull ArrayList<Tariff> iterateTariffNodes(@NotNull NodeList tariffNodes) {
        var tariffs = new ArrayList<Tariff>(tariffNodes.getLength());
        for (var i = 0; i < tariffNodes.getLength(); i++) {
            Element tariffElement;
            var tariffNode = tariffNodes.item(i);
            if (tariffNode instanceof Element) tariffElement = (Element) tariffNode;
            else continue;

            var dict = new HashMap<String, String>();
            dict.put("id", (tariffElement).getAttribute("id"));
            iterateFieldNodes(tariffElement.getChildNodes(), dict);
            tariffs.add(builder.build(dict));
        }
        tariffs.sort(new TariffComparator());
        return tariffs;
    }

    private void iterateFieldNodes(@NotNull NodeList fieldNodes, Map<String, String> dict) {

        for (int j = 0; j < fieldNodes.getLength(); j++) {
            Element fieldElement;
            var tariffNode = fieldNodes.item(j);
            if (tariffNode instanceof Element) fieldElement = (Element) tariffNode;
            else continue;

            var tagName = fieldElement.getTagName();
            if (tagName.equals("callPrice") || tagName.equals("parameters")) {
                iterateChildFieldNodes(fieldElement.getChildNodes(), dict);
            }

            dict.put(tagName, fieldElement.getTextContent());
        }
    }

    private void iterateChildFieldNodes(@NotNull NodeList childFieldNodes, Map<String, String> dict) {
        for (int k = 0; k < childFieldNodes.getLength(); k++) {
            Element childElement;
            var tariffNode = childFieldNodes.item(k);
            if (tariffNode instanceof Element) childElement = (Element) tariffNode;
            else continue;

            dict.put(childElement.getTagName(), childElement.getTextContent());
        }
    }
}
