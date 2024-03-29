package ascii_art;

import ascii_output.AsciiOutput;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.Image;
import java.io.IOException;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *  Handles shell UI: gets/validates all parameters, activates the algorithm.
 */
public class Shell {
    private static final char[] DEFAULT_CHARACTERS = {'0','1','2','3','4','5','6','7','8','9'};
    private static final int DEFAULT_RESOLUTION = 128;
    private static final String DEFAULT_IMAGE_PATH = "cat.jpeg";
    private static final String QUERY_PREFIX = ">>> ";
    private static final String EXIT = "exit";
    private static final String PRINT_ASCII_CHARACTERS = "chars";
    private static final String GENERATE_ASCII_ART = "asciiArt";
    private static final String ADD_ASCII_CHARACTERS_COMMAND = "add";
    private static final String REMOVE_ASCII_CHARACTERS_COMMAND = "remove";
    private static final String ALL_ASCII_CHARACTERS_KEYWORD = "all";
    private static final int FIRST_ASCII_CHARACTER = 32;
    private static final int LAST_ASCII_CHARACTER = 126;
    private static final String SPACE_ASCII_CHARACTER_KEYWORD = "space";
    private static final String CHANGE_RESOLUTION_COMMAND = "res";
    private static final String INCREASE_RESOLUTION_KEYWORD = "up";
    private static final String DECREASE_RESOLUTION_KEYWORD = "down";
    private static final String RESOLUTION_CHANGED_MESSAGE = "Resolution set to %d.";
    private static final String RESOLUTION_WRONG_FORMAT_EXCEPTION_ACTION = "change resolution";
    private static final String CHANGE_IMAGE_COMMAND = "image";
    private static final String CHANGE_OUTPUT_COMMAND = "output";
    private static final String OUTPUT_TO_CONSOLE_KEYWORD = "console";
    private static final String OUTPUT_TO_HTML_KEYWORD = "html";
    private static final String OUTPUT_WRONG_FORMAT_EXCEPTION_ACTION = "change output";
    private static final String OUTPUT_HTML_FILE_NAME = "out.html";
    private static final String OUTPUT_HTML_FONT = "Courier New";

    private final SortedSet<Character> asciiChars;
    private int resolution;
    private Image image;
    private AsciiOutput output;

    /**
     * Default constructor.
     * @throws IOException If image is not found.
     */
    public Shell () throws IOException {
        this(DEFAULT_CHARACTERS, DEFAULT_RESOLUTION, new Image(DEFAULT_IMAGE_PATH),
                new ConsoleAsciiOutput());
    }

    /**
     * Main constructor.
     * @param asciiChars A list of ascii chars from which to build the ascii image.
     * @param resolution Resolution of the created image (number of chars in each row).
     * @param image Original image to convert to ascii image.
     * @param output Method for showing new image.
     */
    public Shell (char[] asciiChars, int resolution, Image image, AsciiOutput output) {
        this.asciiChars = new TreeSet<>();
        for (char c : asciiChars) {
            this.asciiChars.add(c);
        }
        this.resolution = resolution;
        this.image = image;
        this.output = output;
    }

    /**
     * Main function, starts the shell UI.
     */
    public void run () {
        boolean anotherQuery = true;

        while(anotherQuery) {
            try {
                anotherQuery = queryUser();
            }
            catch (IncorrectFormatException | UnknownCommandException | ResolutionOutOfBoundsException |
                   ImageFileException | AsciiArtEmptyCharsetException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /* -------------------- *\
         Private Methods
    \* -------------------- */

    /*
     * Gets one command from user and call relevant function
     */
    private boolean queryUser() throws IncorrectFormatException, UnknownCommandException,
            ResolutionOutOfBoundsException, ImageFileException, AsciiArtEmptyCharsetException {
        System.out.print(QUERY_PREFIX);
        String input = KeyboardInput.readLine();

        switch(input) {
            case EXIT:
                return false;
            case PRINT_ASCII_CHARACTERS:
                printAsciiChars();
                return true;
            case GENERATE_ASCII_ART:
                generateAsciiArt();
                return true;
        }

        String[] splitInput = input.split(" ", 2);
        switch(splitInput[0]) {
            case ADD_ASCII_CHARACTERS_COMMAND:
                if (splitInput.length < 2) {
                    throw new IncorrectFormatException(ADD_ASCII_CHARACTERS_COMMAND);
                }
                parseAddCommand(splitInput[1]);
                return true;
            case REMOVE_ASCII_CHARACTERS_COMMAND:
                if (splitInput.length < 2) {
                    throw new IncorrectFormatException(REMOVE_ASCII_CHARACTERS_COMMAND);
                }
                parseRemoveCommand(splitInput[1]);
                return true;
            case CHANGE_RESOLUTION_COMMAND:
                if (splitInput.length < 2) {
                    throw new IncorrectFormatException(RESOLUTION_WRONG_FORMAT_EXCEPTION_ACTION);
                }
                parseResolutionCommand(splitInput[1]);
                return true;
            case CHANGE_IMAGE_COMMAND:
                if (splitInput.length < 2) {
                    throw new IncorrectFormatException(CHANGE_IMAGE_COMMAND);
                }
                parseImageCommand(splitInput[1]);
                return true;
            case CHANGE_OUTPUT_COMMAND:
                if (splitInput.length < 2) {
                    throw new IncorrectFormatException(OUTPUT_WRONG_FORMAT_EXCEPTION_ACTION);
                }
                parseOutputCommand(splitInput[1]);
                return true;
            default:
                throw new UnknownCommandException();
        }
    }

    /*
     * Shows list of current ascii characters.
     */
    private void printAsciiChars() {
        if (asciiChars.isEmpty()) {
            System.out.println();
            return;
        }
        StringBuilder output = new StringBuilder();
        for (char c : asciiChars) {
            output.append(c).append(" ");
        }
        output.deleteCharAt(output.length()-1);
        System.out.println(output);
    }

    /*
     * Calls algorithm to generate ascii art image.
     */
    private void generateAsciiArt() throws IllegalArgumentException, AsciiArtEmptyCharsetException {
        if (asciiChars.isEmpty()) {
            throw new AsciiArtEmptyCharsetException();
        }
        char[] asciiCharArray = new char[asciiChars.size()];
        for (int i=0; i<asciiChars.size(); i++) {
            asciiCharArray[i] = asciiChars.toArray(new Character[0])[i];
        }

        AsciiArtAlgorithm algo = new AsciiArtAlgorithm(image,resolution,asciiCharArray);
        char[][] res = algo.run();
        output.out(res);
    }

    /*
     * Handles command to add additional ascii characters
     */
    private void parseAddCommand(String input) throws IncorrectFormatException {
        if (input.length() == 1) {
            asciiChars.add(input.charAt(0));
            return;
        }
        if (input.equals(ALL_ASCII_CHARACTERS_KEYWORD)) {
            for (char c=FIRST_ASCII_CHARACTER; c <= LAST_ASCII_CHARACTER; c++) {
                asciiChars.add(c);
            }
            return;
        }
        if (input.equals(SPACE_ASCII_CHARACTER_KEYWORD)) {
            asciiChars.add(' ');
            return;
        }
        if (input.length() == 3 && input.charAt(1) == '-') {
            int a = Math.min(input.charAt(0), input.charAt(2));
            int b = Math.max(input.charAt(0), input.charAt(2));

            for (char c = (char)a; c <= b; c++) {
                asciiChars.add(c);
            }
            return;
        }
        throw new IncorrectFormatException(ADD_ASCII_CHARACTERS_COMMAND);
    }

    /*
     * Handles command to remove ascii characters
     */
    private void parseRemoveCommand(String input) throws IncorrectFormatException {
        if (input.length() == 1) {
            asciiChars.remove(input.charAt(0));
            return;
        }
        if (input.equals(ALL_ASCII_CHARACTERS_KEYWORD)) {
            for (char c=FIRST_ASCII_CHARACTER; c <= LAST_ASCII_CHARACTER; c++) {
                asciiChars.remove(c);
            }
            return;
        }
        if (input.equals(SPACE_ASCII_CHARACTER_KEYWORD)) {
            asciiChars.remove(' ');
            return;
        }
        if (input.length() == 3 && input.charAt(1) == '-') {
            int a = Math.min(input.charAt(0), input.charAt(2));
            int b = Math.max(input.charAt(0), input.charAt(2));

            for (char c = (char)a; c <= b; c++) {
                asciiChars.remove(c);
            }
            return;
        }
        throw new IncorrectFormatException(REMOVE_ASCII_CHARACTERS_COMMAND);
    }

    /*
     * Handles command to change resolution
     */
    private void parseResolutionCommand(String input) throws
            IncorrectFormatException, ResolutionOutOfBoundsException {
        if (input.equals(INCREASE_RESOLUTION_KEYWORD)) {
            if (resolution*2 > calculateMaxResolution()) {
                throw new ResolutionOutOfBoundsException();
            }
            resolution *= 2;
            System.out.printf((RESOLUTION_CHANGED_MESSAGE) + "%n", resolution);
            return;
        }
        if (input.equals(DECREASE_RESOLUTION_KEYWORD)) {
            if (resolution/2 < calculateMinResolution()) {
                throw new ResolutionOutOfBoundsException();
            }
            resolution /= 2;
            System.out.printf((RESOLUTION_CHANGED_MESSAGE) + "%n", resolution);
            return;
        }
        throw new IncorrectFormatException(RESOLUTION_WRONG_FORMAT_EXCEPTION_ACTION);
    }

    /*
     * Calculates maximum width of ascii image.
     */
    private int calculateMaxResolution() {
        return image.getWidth();
    }

    /*
     * Calculates minimum width of ascii image.
     */
    private int calculateMinResolution() {
        return Math.max(1, image.getWidth()/image.getHeight());
    }

    /*
     * Handles command to change image
     */
    private void parseImageCommand(String input) throws ImageFileException {
        Image temp = image;
        try {
            image = new Image(input);
        } catch (IOException e) {
            throw new ImageFileException(e);
        }

        while (true) {
            boolean tooSmall = resolution < calculateMinResolution();
            boolean tooBig = resolution > calculateMaxResolution();
            if (!tooSmall && !tooBig) {
                break;
            }
            if (tooSmall && tooBig) {
                image = temp;
                throw new ImageFileException(null);
            }
            resolution = tooSmall ? resolution*2 : resolution/2;
        }
    }

    /*
     * Handles command to change output method
     */
    private void parseOutputCommand(String input) throws IncorrectFormatException {
        if (input.equals(OUTPUT_TO_CONSOLE_KEYWORD)) {
            output = new ConsoleAsciiOutput();
            return;
        }
        if (input.equals(OUTPUT_TO_HTML_KEYWORD)) {
            output = new HtmlAsciiOutput(OUTPUT_HTML_FILE_NAME, OUTPUT_HTML_FONT);
            return;
        }
        throw new IncorrectFormatException(OUTPUT_WRONG_FORMAT_EXCEPTION_ACTION);
    }

    /* -------------------- *\
               Main
    \* -------------------- */

    /**
     * Main function.
     * @param args expects no arguments.
     */
    public static void main(String[] args) {
        Shell shell = null;
        try {
            shell = new Shell();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        shell.run();
    }
}
