package image;

import java.awt.*;

/**
 * This class takes an image such that its width and heights are multiples of 2, and returns an image that
 * is divided into sub images.
 */
public class ImageDivider {
    private final int subImageSize;
    private final int rows;
    private final int cols;
    private final Image img;
    private final Image[][] dividedImage;

    /**
     * contructor for image divider.
     *
     * @param img        the image to divide.
     * @param resolution the resolution of the image, used to determine the square size.
     */
    public ImageDivider(Image img, int resolution) {
        this.img = img;
        subImageSize = img.getWidth()/ resolution;
        rows = img.getHeight() / subImageSize;
        cols = img.getWidth() / subImageSize;
        dividedImage = new Image[rows][cols];
    }

    /**
     * This method devides the image to sub images.
     *
     * @return the divided image.
     */
    public Image[][] divideImage() {
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                extractSubImage(i, j);
            }
        }
        return dividedImage;
    }

    /**
     * helper method for divideImage. it calculates 1 sub image based on cordnates i,j
     *
     * @param i the starting row
     * @param j the starting col
     */
    private void extractSubImage(int i, int j) {
       Color[][] subImage = new Color[subImageSize][subImageSize];
        int startX = i*subImageSize;
        int startY = j*subImageSize;
        for (int x = 0; x < subImageSize; ++x) {
            for (int y = 0; y < subImageSize; ++y) {
                subImage[x][y] = new Color(img.getPixel(x + startX,
                        startY + y).getRGB());
            }
        }
        dividedImage[i][j] = new Image(subImage,subImageSize,subImageSize);
    }
}