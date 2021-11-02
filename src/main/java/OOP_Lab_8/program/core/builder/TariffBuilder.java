package OOP_Lab_8.program.core.builder;

import OOP_Lab_8.program.domain.entity.CallPrice;
import OOP_Lab_8.program.domain.entity.Parameters;
import OOP_Lab_8.program.domain.entity.Tariff;
import OOP_Lab_8.program.domain.entity.Tariffication;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class TariffBuilder implements ITariffBuilder {
    @Override
    public Tariff build(Map<String, String> dict) {
        return buildCore(dict);
    }

    private @NotNull Tariff buildCore(Map<String, String> dict) {
        var callPrice = buildCallPrice(dict);
        var parameters = buildParameters(dict);
        
        var id = dict.get("id");
        var name = dict.get("name");
        var operatorName = dict.get("operatorName");
        var payroll = Integer.parseInt(dict.get("payroll"));
        var smsPrice = Float.parseFloat(dict.get("smsPrice"));

        return new Tariff(id, name, operatorName, payroll, smsPrice, callPrice, parameters);
    }

    private @NotNull CallPrice buildCallPrice(Map<String, String> dict) {
        var withinTheNetwork = Float.parseFloat(dict.get("withinTheNetwork"));
        var outsideTheNetwork = Float.parseFloat(dict.get("outsideTheNetwork"));
        var toLandlinePhones = Float.parseFloat(dict.get("toLandlinePhones"));

        return new CallPrice(withinTheNetwork, outsideTheNetwork, toLandlinePhones);
    }

    private @NotNull Parameters buildParameters(Map<String, String> dict) {
        var isFavoriteNumberExist = Boolean.parseBoolean(dict.get("isFavoriteNumberExist"));
        var tariffication = Tariffication.valueOf(dict.get("tariffication"));
        var priceForConnection = Integer.parseInt(dict.get("priceForConnection"));

        return new Parameters(isFavoriteNumberExist, tariffication, priceForConnection);
    }
}
