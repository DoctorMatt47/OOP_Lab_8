package OOP_Lab_8.program.domain.entity;

/**
 * Represents tariff entity.
 */
public record Tariff(String id,
                     String name,
                     String operatorName,
                     int payroll,
                     float smsPrice,
                     CallPrice callPrice,
                     Parameters parameters) {
}
