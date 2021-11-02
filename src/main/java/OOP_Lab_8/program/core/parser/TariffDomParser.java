package OOP_Lab_8.program.core.parser;

import OOP_Lab_8.program.domain.entity.CallPrice;
import OOP_Lab_8.program.domain.entity.Parameters;
import OOP_Lab_8.program.domain.entity.Tariff;
import OOP_Lab_8.program.domain.entity.Tariffication;
import OOP_Lab_8.program.domain.exception.TariffParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TariffDomParser implements ITariffParser{
    @Override
    public ArrayList<Tariff> parse(File xmlFile) throws TariffParseException {
        System.out.println(xmlFile.getAbsolutePath());
        Document document;
        try {
            document = DocumentBuilderFactory
                    .newDefaultInstance()
                    .newDocumentBuilder()
                    .parse(xmlFile);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new TariffParseException(e.getMessage());
        }
        return iterateTariffNodes(document.getDocumentElement().getElementsByTagName("tariff"));
    }

    private ArrayList<Tariff> iterateTariffNodes(NodeList tariffsNodes) {
        var tariffs = new ArrayList<Tariff>(tariffsNodes.getLength());
        for (var i = 0; i < tariffsNodes.getLength(); i++) {
            tariffs.add(parseTariffNode(tariffsNodes.item(i)));
        }
        return tariffs;
    }

    private Tariff parseTariffNode(Node tariffNode) {
        var id = tariffNode.getAttributes().item(0).getTextContent();
        var fields = tariffNode.getChildNodes();
        var callPrice = parseCallPrice(fields.item(9));
        var parameters = parseParameters(fields.item(11));
        var name = fields.item(1).getTextContent();
        var operatorName = fields.item(3).getTextContent();
        var payroll = Integer.parseInt(fields.item(5).getTextContent());
        var smsPrice = Float.parseFloat(fields.item(7).getTextContent());

        return new Tariff(id, name, operatorName, payroll, smsPrice, callPrice, parameters);
    }

    private CallPrice parseCallPrice(Node callPriceNode) {
        var callPriceNodes = callPriceNode.getChildNodes();
        var withinTheNetwork = Float.parseFloat(callPriceNodes.item(1).getTextContent());
        var outsideTheNetwork = Float.parseFloat(callPriceNodes.item(3).getTextContent());
        var toLandlinePhones = Float.parseFloat(callPriceNodes.item(5).getTextContent());

        return new CallPrice(withinTheNetwork, outsideTheNetwork, toLandlinePhones);
    }

    private Parameters parseParameters(Node parametersNode) {
        var parametersNodes = parametersNode.getChildNodes();
        var isFavoriteNumberExist = Boolean.parseBoolean(parametersNodes.item(1).getTextContent());
        var tariffication = Tariffication.valueOf(parametersNodes.item(3).getTextContent());
        var priceForConnection = Integer.parseInt(parametersNodes.item(5).getTextContent());

        return new Parameters(isFavoriteNumberExist, tariffication, priceForConnection);
    }
}
