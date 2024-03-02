package image;

import java.awt.*;

/**
 * This class takes an image such that its width and heights are multiples of 2, and returns an image that
 * is divided into sub images.
 */
public class ImageDivider {
    private final int numPixelsInSquare;
    private final int rows;
    private final int cols;
    private final Image img;
    private final Color[][][] dividedImage;

    /**
     * contructor for image divider.
     * @param img the image to divide.
     * @param resolution the resolution of the image, used to determine the square size.
     */
    public ImageDivider(Image img, int resolution) {
        this.img = img;
        int numPixelsInRow = this.img.getWidth();
        int numPixelsInCol = this.img.getHeight();
        numPixelsInSquare = numPixelsInRow / resolution;
        rows = numPixelsInCol / numPixelsInSquare;
        cols = numPixelsInRow / numPixelsInSquare;
        dividedImage = new Color[rows][cols][numPixelsInSquare*numPixelsInSquare];
    }

    /**
     * This method devides the image to sub images.
     * @return the divided image.
     */
    public Color[][][] divideImage() {
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                extractSubImage(i, j);
            }
        }
        return dividedImage;
    }

    /**
     * helper method for divideImage. it calculates 1 sub image based on cordnates i,j
     * @param i the starting row
     * @param j the starting col
     */
    private void extractSubImage(int i, int j) {
        int startX = i * numPixelsInSquare;
        int startY = j * numPixelsInSquare;
        for(int x = 0; x < numPixelsInSquare;++x) {
            for (int y = 0 ; y < numPixelsInSquare; ++y) {
                dividedImage[i][j][x * (numPixelsInSquare) + y] = img.getPixel(startX + x,
                        startY + y);
            }
        }
    }

}
