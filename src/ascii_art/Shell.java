package ascii_art;

import java.util.SortedSet;
import java.util.TreeSet;

public class Shell {
    private static final String QUERY_PREFIX = ">>> ";
    private static final String EXIT = "exit";
    private static final String PRINT_ASCII_CHARACTERS = "chars";
    private static final String ADD_ASCII_CHARACTERS = "add";
    private static final String REMOVE_ASCII_CHARACTERS = "remove";
    private static final String ALL_ASCII_CHARACTERS_KEYWORD = "all";
    private static final int FIRST_ASCII_CHARACTER = 32;
    private static final int LAST_ASCII_CHARACTER = 126;
    private static final String SPACE_ASCII_CHARACTER_KEYWORD = "space";

    SortedSet<Character> asciiChars;

    Shell () {
        asciiChars = new TreeSet<>();
        for (int i = 0; i <= 9; i++) {
            asciiChars.add((char)(i + '0'));
        }
    }

    public void run () {
        boolean anotherQuery = true;

        while(anotherQuery) {
            try {
                anotherQuery = queryUser();
            }
            catch (IncorrectFormatException | UnknownCommandException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private boolean queryUser() throws IncorrectFormatException, UnknownCommandException {
        System.out.print(QUERY_PREFIX);
        String input = KeyboardInput.readLine();

        switch(input) {
            case EXIT:
                return false;
            case PRINT_ASCII_CHARACTERS:
                printAsciiChars();
                return true;
        }

        String[] splitInput = input.split(" ", 2);
        try {
            switch(splitInput[0]) {
                case ADD_ASCII_CHARACTERS:
                    parseAddCommand(splitInput[1]);
                    return true;
                case REMOVE_ASCII_CHARACTERS:
                    parseRemoveCommand(splitInput[1]);
                    return true;
                default:
                    throw new UnknownCommandException();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IncorrectFormatException(ADD_ASCII_CHARACTERS);
        }
    }

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
        }
        if (input.length() == 3 && input.charAt(1) == '-') {
            int a = Math.min(input.charAt(0), input.charAt(2));
            int b = Math.max(input.charAt(0), input.charAt(2));

            for (char c = (char)a; c <= b; c++) {
                asciiChars.add(c);
            }
            return;
        }
        throw new IncorrectFormatException(ADD_ASCII_CHARACTERS);
    }

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
        }
        if (input.length() == 3 && input.charAt(1) == '-') {
            int a = Math.min(input.charAt(0), input.charAt(2));
            int b = Math.max(input.charAt(0), input.charAt(2));

            for (char c = (char)a; c <= b; c++) {
                asciiChars.remove(c);
            }
            return;
        }
        throw new IncorrectFormatException(REMOVE_ASCII_CHARACTERS);
    }

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


    public static void main(String[] args) {
        Shell shell = new Shell();
        shell.run();
    }
}
