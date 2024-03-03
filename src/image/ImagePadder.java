package image;

import java.awt.*;

/**
 * This class will be in charge of padding an image with white pixels to make its height and width be
 * a power of 2.
 */
public class ImagePadder {
    private static final int FIRST_POWER_OF_2 = 1;
    private static final int MULTIPLICATION_FACTOR = 2;
    private final Image img;

    /**
     * constructor for image padder. pads images with white color
     * @param img the image to add padding to.
     */
    public ImagePadder(Image img) {
       this.img = img;
    }

    /**
     * This method takes an image and adds padding to it.
     * @return padded image.
     */
    public Image padImage() {
        int newRowCount = getNextMultipleOf2(this.img.getHeight());
        int newColCount = getNextMultipleOf2(this.img.getWidth());
        int verticalPadding = (newRowCount - this.img.getHeight()) / 2;
        int horizontalPadding = (newColCount - this.img.getWidth()) / 2;

        Color[][] paddedImage = new Color[newRowCount][newColCount];
        for (int row = 0; row < newRowCount; ++row) {
            for (int col = 0; col < newColCount; ++col) {
                boolean isPixelPadding = (row < verticalPadding || row >= newRowCount - verticalPadding)
                        || (col < horizontalPadding || col >= newColCount - horizontalPadding);
                paddedImage[row][col] = isPixelPadding ? Color.WHITE :
                        img.getPixel(row-verticalPadding, col-horizontalPadding);
            }
        }
        return new Image(paddedImage,newColCount, newRowCount);
    }

    /*
    based on a given number returns the next multiple of 2.
     */
    private static int getNextMultipleOf2(int size) {
        int cur_power = FIRST_POWER_OF_2;
        while (cur_power < size) {
            cur_power *= MULTIPLICATION_FACTOR;
        }
        return cur_power;
    }
}
