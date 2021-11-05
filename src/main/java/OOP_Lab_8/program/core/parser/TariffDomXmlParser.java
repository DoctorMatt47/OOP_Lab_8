package OOP_Lab_8.program.core.parser;

import OOP_Lab_8.program.core.builder.ITariffBuilder;
import OOP_Lab_8.program.core.sorter.TariffComparator;
import OOP_Lab_8.program.domain.entity.Tariff;
import OOP_Lab_8.program.domain.exception.TariffParseException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents tariff xml parser. Uses dom way to parse xml file.
 */
@SuppressWarnings("ClassCanBeRecord")
public class TariffDomXmlParser implements ITariffXmlParser {
    /**
     * Tariff builder dependency.
     */
    private final ITariffBuilder builder;

    /**
     * Constructs tariff dom xml parser service.
     * @param builder Tariff builder dependency.
     */
    @Contract(pure = true)
    public TariffDomXmlParser(ITariffBuilder builder) {
        this.builder = builder;
    }

    /**
     * Parses xml file with list of tariff entities.
     * Uses dom way to parse xml file.
     * Xml file must be valid and must have tariffs with structure,
     * described in the tariff.xsd file.
     * @param xmlFile Xml file with tariffs.
     * @return Sorted by id tariffs from xml file.
     * @throws TariffParseException Be thrown, if parse was unsuccessful.
     */
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

    /**
     * Iterates throw the tariff nodes and builds collection of tariffs.
     * @param tariffNodes List of tariff nodes.
     * @return Sorted collection of tariff.
     */
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

    /**
     * Iterates throw the nodes and fills map for tariff.
     * @param fieldNodes Node list with fields of tariff.
     * @param dict Map to be filled.
     */
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

    /**
     * Iterates throw the nodes and fills map for parameters and call price.
     * @param childFieldNodes Node list with parameters fields or call prices.
     * @param dict Map to be filled.
     */
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
