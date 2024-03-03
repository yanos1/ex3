package image;
import image_char_matching.SubImgCharMatcher;
import java.awt.*;

/**
 * This class uses the facade design pattern to execute all steps needed to convert an image to ascci art.
 */
public class ImageToAsciiConverter {
    private final Image img;
    private final int resolution;
    private final SubImgCharMatcher matcher;

    /**
     * Constructs an object that has access to convertImageToAsciiArt.
     * @param img image to convert
     * @param resolution image resolution
     * @param matcher an object that helps with turning pixels into ascii chars.
     */
    public ImageToAsciiConverter(Image img, int resolution, SubImgCharMatcher matcher) {
        this.img = img;
        this.resolution = resolution;
        this.matcher = matcher;
    }

    /**
     * this method does the entire process of turning an image to ascci art.
     * @return char[][] that represents the new ascii art image.
     */
    public char[][] convertImageToAsciiArt() {
        ImagePadder imagePadder = new ImagePadder(this.img);
        Image padded = imagePadder.padImage();
        ImageDivider imageDivider = new ImageDivider(padded, resolution);
        Image[][] dividedImage = imageDivider.divideImage();
        ImageGrayer grayer = new ImageGrayer(dividedImage);
        double[][] grayedImage = grayer.greyImage();

        char[][] asciiImage = new char[grayedImage.length][grayedImage[0].length];
        for(int i=0; i < asciiImage.length; ++i) {
            for (int j=0; j < asciiImage[0].length; ++j) {
                asciiImage[i][j] = matcher.getCharByImageBrightness(grayedImage[i][j]);
            }
        }
        return asciiImage;
    }


}
