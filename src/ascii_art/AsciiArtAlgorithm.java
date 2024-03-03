package ascii_art;
import image.Image;
import image.ImageToAsciiConverter;
import image_char_matching.SubImgCharMatcher;


/**
 *  This class executes the ascii art algorithm.
 */
public class AsciiArtAlgorithm {
    static private SubImgCharMatcher matcher;
    private final Image img;
    private final int resolution;

    /**
     * Constructor for the class used by the user.
     * @param img the image to convert to ascii art
     * @param resolution the expected resolution for the image
     * @param asciiChars the charset to use for thr art
     */
    public AsciiArtAlgorithm(Image img, int resolution, char[] asciiChars) {
        if (matcher == null) {
            matcher = new SubImgCharMatcher(asciiChars);
        } else {
            for (char c : asciiChars) {
                matcher.addChar(c);
            }

        }
        this.img = img;
        this.resolution = resolution;
    }

    /**
     * executes the conversion of the image.
     * @return the array of characters that represents the ascii art image.
     */
    public char [][] run() {
        ImageToAsciiConverter converter = new ImageToAsciiConverter(this.img,this.resolution,matcher);
        return converter.convertImageToAsciiArt();
    }
}

