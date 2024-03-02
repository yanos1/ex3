package image;

import java.awt.*;

/**
 * This class will be in charge of padding an image with white pixels to make its height and width be
 * a power of 2.
 */
public class ImagePadder {
    /**
     * the first power of 2
     */
    public static final int FIRST_POWER_OF_2 = 1;
    /**
     * mutiplication factor to calculate next powers of 2.
     */
    public static final int MULTIPLICATION_FACTOR = 2;

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
    public Color[][] padImage() {
        int rows = getNextMultipleOf2(this.img.getWidth());
        int cols = getNextMultipleOf2(this.img.getHeight());
        int rowPadding = rows - this.img.getWidth();   // num of row elements to add.
        int colPadding = cols - this.img.getHeight();  // num of col elements to add
        Color[][] paddedImage = new Color[rows][cols];
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                if (i < colPadding/2 || i >= rows - colPadding/2||
                        j <rowPadding/2 || j >= cols -rowPadding/2){
                    Color whiteColor = Color.WHITE;
                    paddedImage[i][j] = whiteColor;
                } else {
                    paddedImage[i][j] = this.img.getPixel(i-(colPadding/2),j-(rowPadding/2));
                }
            }
        }
        return paddedImage;
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
