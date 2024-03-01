package ascii_art;
import ascii_output.HtmlAsciiOutput;
import image.Image;
import image.ImageToAsciiConverter;
import image_char_matching.SubImgCharMatcher;

import java.io.IOException;

public class AsciiArtAlgorithm {

    private SubImgCharMatcher matcher;
    private Image img;
    private int resolution;

    public AsciiArtAlgorithm(Image img, int resolution, SubImgCharMatcher matcher) {
        this.matcher = matcher;
        this.img = img;
        this.resolution = resolution;
    }

    public char [][] run() {
        ImageToAsciiConverter converter = new ImageToAsciiConverter(this.img,this.resolution,this.matcher);
        return converter.convertImageToAsciiArt();
    }

//    public static void main(String[] args) throws IOException {
//        char[] asciiChars = new char[95]; // 126 - 32 + 1 = 95
//
//        // Populate the array with printable ASCII characters
//        for (int i = 32; i <= 126; i++) {
//            asciiChars[i - 32] = (char) i;
//        }
//        SubImgCharMatcher matcher = new SubImgCharMatcher(asciiChars);
//        Image img = new Image("assets/examples/cat.jpeg");
//        AsciiArtAlgorithm algo = new AsciiArtAlgorithm(img,256,matcher);
//        char[][] res = algo.run();
//        HtmlAsciiOutput output = new HtmlAsciiOutput("out.html","Courier New");
//        output.out(res);
//    }
}

