package ascii_art;

public class AsciiArtEmptyCharsetException extends Exception {
    private static final String EMPTY_CHARSET_EXCEPTION_MESSAGE = "Did not execute. Charset is empty.";

    public AsciiArtEmptyCharsetException() {
        super(EMPTY_CHARSET_EXCEPTION_MESSAGE);
    }
}
