package image;

import java.awt.*;

/**
 * This class takes an image such that its width and heights are multiples of 2, and returns an image that
 * is divided into sub images.
 */
public class ImageDivider {
    private int numPixelsInSquare;
    private int rows;
    private int cols;
    private Image img;
    private Color[][][] dividedImage;

    public ImageDivider(Image img, int resolution) {
        this.img = img;
        int numPixelsInRow = this.img.getWidth();
        int numPixelsInCol = this.img.getHeight();
        numPixelsInSquare = numPixelsInRow / resolution;
        rows = numPixelsInCol / numPixelsInSquare;
        cols = numPixelsInRow / numPixelsInSquare;
        dividedImage = new Color[rows][cols][numPixelsInSquare*numPixelsInSquare];
    }

    public Color[][][] divideImage() {
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                int startX = i * numPixelsInSquare;
                int startY = j * numPixelsInSquare;
                for(int x = 0; x < numPixelsInSquare;++x) {
                    for (int y = 0 ; y < numPixelsInSquare; ++y) {
                        System.out.println(i + " " + j + " " + x + " " + y );
                        dividedImage[i][j][x * (numPixelsInSquare) + y] = img.getPixel(startX + x,
                                startY + y);

                    }
                    // 110 -> 22
                    // col factor - 1    row factor - 0
                }


            }
        }
        return dividedImage;
    }

//    public Color[][][] divideImage() {
//        // Determine the dimensions of the divided image
//        // Iterate over the sub-images
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < cols; j++) {
//                // Calculate the starting pixel coordinates for the sub-image
//                int startX = i * numPixelsInSquare;
//                int startY = j * numPixelsInSquare;
//
//                // Extract pixels for the sub-image
//                for (int x = 0; x < numPixelsInSquare; x++) {
//                    for (int y = 0; y < numPixelsInSquare; y++) {
//                        // Populate dividedImage with pixels from the original image
//                        dividedImage[i][j][x * numPixelsInSquare + y] = img.getPixel(startX + x, startY + y);
//                    }
//                }
//            }
//        }
//
//        return dividedImage;
//    }

}
