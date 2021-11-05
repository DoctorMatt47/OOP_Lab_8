package OOP_Lab_8.program.domain.entity;

/**
 * Represents different parameters for the tariff entity.
 */
public record Parameters(boolean isFavoriteNumberExist,
                         Tariffication tariffication,
                         int priceForConnection) {
}
