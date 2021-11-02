package OOP_Lab_8.program.domain.exception;

public class TariffParseException extends Exception{
    private final String message;

    public TariffParseException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
