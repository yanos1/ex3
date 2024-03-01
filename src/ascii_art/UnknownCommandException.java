package ascii_art;

public class UnknownCommandException extends Exception {
    private static final String EXCEPTION_MESSAGE =
            "Did not execute due to incorrect command.";

    public UnknownCommandException () {
        super(EXCEPTION_MESSAGE);
    }
}
