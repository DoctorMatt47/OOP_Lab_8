package OOP_Lab_8.program.core.builder;

import OOP_Lab_8.program.domain.entity.Tariffication;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TariffBuilderTest {

    @Test
    void build() {
        var tariff = new TariffBuilder().build(buildMap());
        assertEquals(tariff.id(), "id");
        assertEquals(tariff.name(), "name");
        assertEquals(tariff.operatorName(), "operatorName");
        assertEquals(tariff.payroll(), 10);
        assertEquals(tariff.smsPrice(), 0.1f);
        assertEquals(tariff.callPrice().withinTheNetwork(), 0.1f);
        assertEquals(tariff.callPrice().outsideTheNetwork(), 0.1f);
        assertEquals(tariff.callPrice().toLandlinePhones(), 0.1f);
        assertTrue(tariff.parameters().isFavoriteNumberExist());
        assertEquals(tariff.parameters().tariffication(), Tariffication.NO);
        assertEquals(tariff.parameters().priceForConnection(), 10);
    }

    private @NotNull Map<String, String> buildMap() {
        var dict = new HashMap<String, String>();
        dict.put("id", "id");
        dict.put("name", "name");
        dict.put("operatorName", "operatorName");
        dict.put("payroll", "10");
        dict.put("smsPrice", "0.1");
        dict.put("withinTheNetwork", "0.1");
        dict.put("outsideTheNetwork", "0.1");
        dict.put("toLandlinePhones", "0.1");
        dict.put("isFavoriteNumberExist", "true");
        dict.put("tariffication", "NO");
        dict.put("priceForConnection", "10");
        return dict;
    }
}