package ascii_art;

public class IncorrectFormatException extends Exception {
    private static final String EXCEPTION_MESSAGE =
            "Did not %s due to incorrect format.";

    public IncorrectFormatException (String command) {
        super (String.format(EXCEPTION_MESSAGE, command));
    }
}
