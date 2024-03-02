package ascii_art;

/**
 * Custom exception for giving a recognized command in the wrong format (mainly due to argument count).
 */
public class IncorrectFormatException extends Exception {
    private static final String EXCEPTION_MESSAGE =
            "Did not %s due to incorrect format.";

    /**
     * Constructor.
     * @param command Which command caused the exception.
     */
    public IncorrectFormatException (String command) {
        super (String.format(EXCEPTION_MESSAGE, command));
    }
}
