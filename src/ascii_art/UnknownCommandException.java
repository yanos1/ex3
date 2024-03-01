package ascii_art;

public class UnknownCommandException extends Throwable {
    private static final String EXCEPTION_MESSAGE_UNKNOWN_COMMAND =
            "Did not execute due to incorrect command.";

    public UnknownCommandException () {
        super(EXCEPTION_MESSAGE_UNKNOWN_COMMAND);
    }
}
