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
    private final Image[][] coloredImage;

    /**
     * contributor receiving the colored image to grey out.
     * @param coloredImage the colored image.
     */
    public ImageGrayer(Image[][] coloredImage) {
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
                greyImg[i][j] =  calculateShade(coloredImage[i][j]);

            }
        }
        return greyImg;
    }
    /*
     helper function to calculate the shade of a sub image.
     */
    private double calculateShade(Image img) {
        double currentShade = 0;
        for (int i = 0; i < img.getWidth(); ++i) {
            for(int j =0; j < img.getHeight(); ++j) {
                currentShade += img.getPixel(i,j).getRed() * RED_TO_GREY_CONSTANT +
                            img.getPixel(i,j).getGreen() * GREEN_TO_GREY_CONSTANT +
                            img.getPixel(i,j).getBlue() * BLUE_TO_GREY_CONSTANT;
            }
        }
        return currentShade / (img.getHeight()*img.getWidth() * MAX_RGB_COLOR);
    }
}
