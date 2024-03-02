package ascii_art;

/**
 * Custom exception for getting unknown commands.
 */
public class UnknownCommandException extends Exception {
    private static final String EXCEPTION_MESSAGE =
            "Did not execute due to incorrect command.";

    /**
     * Constructor.
     */
    public UnknownCommandException () {
        super(EXCEPTION_MESSAGE);
    }
}
