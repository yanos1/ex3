package ascii_art;

/**
 * Custom exception for calling the algorithm without any ascii chars to draw with.
 */
public class AsciiArtEmptyCharsetException extends Exception {
    private static final String EMPTY_CHARSET_EXCEPTION_MESSAGE = "Did not execute. Charset is empty.";

    /**
     * Constructor.
     */
    public AsciiArtEmptyCharsetException() {
        super(EMPTY_CHARSET_EXCEPTION_MESSAGE);
    }
}
