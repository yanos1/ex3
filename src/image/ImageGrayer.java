package image;

import java.awt.*;

public class ImageGrayer {
    public static final int MAX_RGB_COLOR = 255;
    public static final double RED_TO_GREY_CONSTANT = 0.2126;
    public static final double GREEN_TO_GREY_CONSTANT = 0.7152;
    public static final double BLUE_TO_GREY_CONSTANT = 0.0722;
    private Color[][][] coloredImage;

    public ImageGrayer(Color[][][] coloredImage) {
        this.coloredImage = coloredImage;
    }

    double[][] greyImage() {
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
