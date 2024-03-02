package image;

import java.awt.*;

/**
 * This class transforms an image to black and white image.
 */
public class ImageGrayer {

    private static final int MAX_RGB_COLOR = 255;
    private static final double RED_TO_GREY_CONSTANT = 0.2126;
    private static final double GREEN_TO_GREY_CONSTANT = 0.7152;
    private static final double BLUE_TO_GREY_CONSTANT = 0.0722;
    private final Color[][][] coloredImage;

    /**
     * contributor receiving the colored image to grey out.
     * @param coloredImage the colored image.
     */
    public ImageGrayer(Color[][][] coloredImage) {
        this.coloredImage = coloredImage;
    }

    /**
     * this method gays out a colored image.
     * @return grey image.
     */
    public double[][] greyImage() {
        double[][] greyImg = new double[coloredImage.length][coloredImage[0].length];
        for (int i = 0; i < coloredImage.length; ++i) {
            for (int j = 0; j < coloredImage[0].length; ++j) {
                double currentShade = 0;
                for (int k = 0; k < coloredImage[0][0].length; ++k) {
                    currentShade +=
                            coloredImage[i][j][k].getRed() * RED_TO_GREY_CONSTANT +
                                    coloredImage[i][j][k].getGreen() * GREEN_TO_GREY_CONSTANT
                                    + coloredImage[i][j][k].getBlue() * BLUE_TO_GREY_CONSTANT;
                }
                greyImg[i][j] = currentShade / (coloredImage[0][0].length * MAX_RGB_COLOR);
            }
        }
        return greyImg;
    }
}
