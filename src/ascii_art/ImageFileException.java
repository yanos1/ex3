package ascii_art;

import java.io.IOException;

/**
 * Custom exception for having a problem handling a given image.
 */
public class ImageFileException extends Exception {
    private static final String EXCEPTION_MESSAGE =
            "Did not execute due to problem with image file";

    /**
     * Constructor.
     * @param e IOException
     */
    public ImageFileException(IOException e) {
        super(EXCEPTION_MESSAGE, e);
    }
}
