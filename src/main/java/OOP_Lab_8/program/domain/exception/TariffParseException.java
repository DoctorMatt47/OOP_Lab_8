package OOP_Lab_8.program.domain.exception;

/**
 * Represents exception, which will be thrown when error in tariff parse occurs.
 */
public class TariffParseException extends Exception{
    /**
     * Represents exception message. Describes error reason.
     */
    private final String message;

    /**
     * Constructs tariff parse exception.
     * @param message Message of the exception.
     */
    public TariffParseException(String message) {
        this.message = message;
    }

    /**
     * Returns exception message.
     * @return Message of the exception.
     */
    public String getMessage() {
        return message;
    }
}
