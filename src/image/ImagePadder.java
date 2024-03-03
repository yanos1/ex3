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
        //   300 x 300
        // 512 X 512

    /**
     * This method takes an image and adds padding to it.
     * @return padded image.
     */
//    public Image padImage() {
//        int newHeight = getNextMultipleOf2(this.img.getHeight());
//        int newWidth = getNextMultipleOf2(this.img.getWidth());
//        int rowPadding = (newHeight - this.img.getHeight()) / 2;   // num of row elements to add.
//        int colPadding = (newWidth - this.img.getWidth()) / 2;  // num of col elements to add
//        Color[][] paddedImage = new Color[newWidth][newHeight];
//        for (int i = 0; i < newHeight; ++i) {
//            for (int j = 0; j < newWidth; ++j) {
////                System.out.println("i: " + i);
////                System.out.println("j: " + j);
////                System.out.println("newHeight: " + newHeight);
////                System.out.println("cols: " + cols);
////                System.out.println("rowPadding: " + rowPadding/2);
////                System.out.println("colPadding: " + colPadding/2);
//                if (i < rowPadding || i >= newHeight - rowPadding||
//                        j <colPadding/2 || j >= newWidth -colPadding/2){
//                    Color whiteColor = Color.WHITE;
//                    paddedImage[i][j] = whiteColor;
//                } else {
//                    paddedImage[i][j] = this.img.getPixel(i-(rowPadding/2),j-(colPadding/2));
//                }
//            }
//        }
//        return new Image(paddedImage, newHeight,newWidth);
//    }
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
