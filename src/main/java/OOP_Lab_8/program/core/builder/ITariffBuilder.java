package OOP_Lab_8.program.core.builder;

import OOP_Lab_8.program.domain.entity.Tariff;

import java.util.Map;

public interface ITariffBuilder {
    Tariff build(Map<String, String> dict);
}
