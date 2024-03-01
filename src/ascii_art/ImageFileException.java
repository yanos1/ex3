package ascii_art;

import java.io.IOException;

public class ImageFileException extends Exception {
    private static final String EXCEPTION_MESSAGE =
            "Did not execute due to problem with image file";

    public ImageFileException(IOException e) {
        super(EXCEPTION_MESSAGE, e);
    }
}
