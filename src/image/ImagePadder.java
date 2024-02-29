package image;

import java.awt.*;

/**
 * This class will be in charge of padding an image with white pixels to make its height and width be
 * a power of 2.
 */
public class ImagePadder {
    public static final int FIRST_POWER_OF_2 = 1;
    public static final int MULTIPLICATION_FACTOR = 2;
    Color whiteColor = Color.WHITE;
    private int rows;
    private int cols;
    private Color[][] paddedImage;

    public ImagePadder(Image img) {
        rows = getNextMultipleOf2(img.getWidth());
        cols = getNextMultipleOf2(img.getHeight());
        int rowPadding =rows - img.getWidth();
        int colPadding = cols - img.getHeight();

        paddedImage = new Color[rows][cols];
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {

                    if (i < colPadding/2 || i >= rows - colPadding/2|| j <rowPadding/2 || j >= cols-rowPadding/2) {
                        paddedImage[i][j] = whiteColor;
                    } else {
                        paddedImage[i][j] = img.getPixel(i-(colPadding/2),j-(rowPadding/2));
                    }
            }
        }
    }

    public Color[][] getPaddedImage() {
        return this.paddedImage;
    }

    private static int getNextMultipleOf2(int size) {
        int cur_power = FIRST_POWER_OF_2;
        while (cur_power < size) {
            cur_power *= MULTIPLICATION_FACTOR;
        }
        return cur_power;

    }


}
