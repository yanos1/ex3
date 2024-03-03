package image_char_matching;

import java.util.*;

/**
 * This class is in charge of matching a char to a sub image based on its brightness.
 */
public class SubImgCharMatcher {
    private static final int ASCII_CHAR_PIXEL_WIDTH = 256;
    private double maxBrightness = 0;
    private double minBrightness = 1;
    private TreeMap<Double, TreeSet<Character>> treeMap = new TreeMap<>();
    private final HashMap<Character,Double> charBrightnesses = new HashMap<>();

    /**
     * public constructor.
     *
     * @param charset the given charset by the user.
     */
    public SubImgCharMatcher(char[] charset) {

        for (char c : charset) {
            double charBrightness = getCharBrightness(c);
            if (treeMap.containsKey(charBrightness)){
                treeMap.get(charBrightness).add(c);
            } else {
                var newSet = new TreeSet<Character>();
                newSet.add(c);
                treeMap.put(charBrightness,newSet);

            }
            maxBrightness = Math.max(maxBrightness, charBrightness);
            minBrightness = Math.min(minBrightness, charBrightness);
        }
        // normalize all cells.
        normalizeCells();
    }

    /**
     * given a brightness value of a sub image, this method returns the char with the closest brightness to
     * the given brightness.
     *
     * @param brightness the given brightness.
     * @return the closest char.
     */
    public char getCharByImageBrightness(double brightness) {
        Double closestLowerBrightness = treeMap.floorKey(brightness);
        Double closestHigherBrightness = treeMap.ceilingKey(brightness);

        if (closestLowerBrightness == null) {
            return treeMap.get(closestHigherBrightness).first();
        }

        if (closestHigherBrightness == null) {
            return treeMap.get(closestLowerBrightness).first();
        }

        double diffLow = Math.abs(closestLowerBrightness - brightness);
        double diffHigh = Math.abs(closestHigherBrightness - brightness);

        if (diffHigh < diffLow) {
            return treeMap.get(closestHigherBrightness).first();
        }
        else if (diffLow < diffHigh) {
            return treeMap.get(closestLowerBrightness).first();
        }
        char c1 = treeMap.get(closestLowerBrightness).first();
        char c2 = treeMap.get(closestHigherBrightness).first();
        return c1 < c2 ? c1 : c2;
    }

    /**
     * Add a char to the charset.
     */
    public void addChar(char c) {
        double charBrightness = getCharBrightness(c);
        double normalizedBrightness = normalizeBrightness(charBrightness);
        if (treeMap.containsKey(normalizedBrightness)) {
            var charArray = treeMap.get(normalizedBrightness);
            charArray.add(c);
        } else {
            boolean needToNormalize = false;
            if (charBrightness > maxBrightness) {
                maxBrightness = charBrightness;
                needToNormalize = true;
            } else if (charBrightness < minBrightness) {
                minBrightness = charBrightness;
                needToNormalize = true;
            }
            TreeSet<Character> newSet = new TreeSet<>();
            newSet.add(c);
            treeMap.put(normalizedBrightness, newSet);
            if (needToNormalize) {
                normalizeCells();
            }

        }
    }
    /**
     * Removes a char from the charset
     */
    public void removeChar(char c) {
        double charBrightness = getCharBrightness(c);
        double normalizedBrightness = normalizeBrightness(charBrightness);
        var charArr = treeMap.get(normalizedBrightness);
        charArr.remove(c);
        if (charArr.isEmpty()) {
            treeMap.remove(normalizedBrightness);
            if (charBrightness == maxBrightness) {
                maxBrightness = treeMap.lastKey();
                normalizeCells();
            } else if (charBrightness == minBrightness) {
                minBrightness = treeMap.firstKey();
                normalizeCells();
            }
        }
    }
    /**
        reset all characters in the tree.
     */
    public void resetChars() {
        this.treeMap.clear();
    }
    /*
        calculate brightness for a single characrter.
     */
    private double calculateBrightnessOfChar(char c) {
        boolean[][] boolArray = CharConverter.convertToBoolArray(c);
        int numOfWhitePixels = 0;
        for (boolean[] pixelArr : boolArray) {
            for (boolean pixel : pixelArr) {
                if (pixel) {
                    numOfWhitePixels++;
                }
            }
        }
        return (double) numOfWhitePixels / ASCII_CHAR_PIXEL_WIDTH;
    }
    /*
    apply normalization for all cells. (Lienar stretch).
     */
    private void normalizeCells() {
        TreeMap<Double, TreeSet<Character>> newMap = new TreeMap<>();
        for (var entry : treeMap.entrySet()) {
            double brightness = charBrightnesses.get(entry.getValue().first());
            TreeSet<Character> charSet = entry.getValue();
            newMap.put(normalizeBrightness(brightness), charSet);
        }
        treeMap = newMap;
    }

    /*
    retrive or calculate a character brigthness
     */
    private double getCharBrightness(char c) {
        double charBrightness;
        if (charBrightnesses.containsKey(c)) {
            charBrightness = charBrightnesses.get(c);
        } else {
            charBrightness = calculateBrightnessOfChar(c);
            charBrightnesses.put(c,charBrightness);
        }
        return charBrightness;
    }

    /*
    calculate normalized value for given brightness.
     */
    private Double normalizeBrightness(Double brightness) {
        return (brightness - minBrightness) / (maxBrightness - minBrightness);
    }
}

