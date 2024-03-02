package ascii_art;

/**
 * Custom exception for attempting to change the resolution outside possible boundaries.
 */
public class ResolutionOutOfBoundsException extends Exception {
    private static final String EXCEPTION_MESSAGE =
            "Did not change resolution due to exceeding boundaries.";

    /**
     * Constructor.
     */
    public ResolutionOutOfBoundsException () {
        super(EXCEPTION_MESSAGE);
    }
}
