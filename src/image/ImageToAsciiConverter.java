package image;
import image_char_matching.SubImgCharMatcher;
import java.awt.*;

public class ImageToAsciiConverter {
    private final Image img;
    private final int resolution;
    private final SubImgCharMatcher matcher;
    public ImageToAsciiConverter(Image img, int resolution, SubImgCharMatcher matcher) {
        this.img = img;
        this.resolution = resolution;
        this.matcher = matcher;
    }

    public char[][] convertImageToAsciiArt() {
        ImagePadder imagePadder = new ImagePadder(this.img);
        Color[][] padded = imagePadder.getPaddedImage();
        Image paddedImage = new Image(padded, padded.length, padded[0].length);
        ImageDivider imageDivider = new ImageDivider(paddedImage, resolution);
        Color[][][] dividedImage = imageDivider.divideImage();
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
