package OOP_Lab_8.program.core.builder;

import OOP_Lab_8.program.domain.entity.Tariff;

import java.util.Map;

/**
 * Represents interface for tariff entity builder method.
 */
public interface ITariffBuilder {

    /**
     * Builds tariff entity.
     * @param dict Map with fields and values of tariff entity. Fields and values must be represented in string format.
     * @return Built tariff.
     */
    Tariff build(Map<String, String> dict);
}
