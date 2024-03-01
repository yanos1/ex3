package ascii_art;

public class ResolutionOutOfBoundsException extends Exception {
    private static final String EXCEPTION_MESSAGE =
            "Did not change resolution due to exceeding boundaries.";

    public ResolutionOutOfBoundsException () {
        super(EXCEPTION_MESSAGE);
    }
}
