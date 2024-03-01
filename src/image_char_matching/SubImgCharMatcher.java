package image_char_matching;

import java.util.*;

/**
 * This class is in charge of matching a char to a sub image based on its brightness.
 */
public class SubImgCharMatcher {
    /**
     * The number of pixels in each character.
     */
    public static final int PIXELS_IN_BOOL_ARRAY = 256;
    private double maxBrightness = 0;
    private double minBrightness = 1;
    private TreeMap<Double, ArrayList<Character>> treeMap = new TreeMap<>();
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
                Collections.sort(treeMap.get(charBrightness));
            } else {
                var newList = new ArrayList<Character>();
                newList.add(c);
                treeMap.put(charBrightness,newList);
                // initialize all
                // starting values
            }
            maxBrightness = Math.max(maxBrightness, charBrightness);
            minBrightness = Math.min(minBrightness, charBrightness);
        }
        // normalize all cells.
        normalizeCells();
    }


    /**
     * perform a linear stretch of the given brightness
     *
     * @param brightness brightness of a character
     * @return the new brightness
     */
    private Double normalizeBrightness(Double brightness) {
        return (brightness - minBrightness) / (maxBrightness - minBrightness);
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
            return treeMap.get(closestHigherBrightness).get(0);
        }

        if (closestHigherBrightness == null) {
            return treeMap.get(closestLowerBrightness).get(0);
        }

        double diffLow = Math.abs(closestLowerBrightness - brightness);
        double diffHigh = Math.abs(closestHigherBrightness - brightness);

        if (diffHigh < diffLow) {
            return treeMap.get(closestHigherBrightness).get(0);
        }
        else if (diffLow < diffHigh) {
            return treeMap.get(closestLowerBrightness).get(0);
        }

        char c1 = treeMap.get(closestLowerBrightness).get(0);
        char c2 = treeMap.get(closestHigherBrightness).get(0);
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
            Collections.sort(charArray);
        } else {
            boolean needToNormalize = false;
            if (charBrightness > maxBrightness) {
                maxBrightness = charBrightness;
                needToNormalize = true;
            } else if (charBrightness < minBrightness) {
                minBrightness = charBrightness;
                needToNormalize = true;
            }
            treeMap.put(normalizedBrightness, new ArrayList<>(c));
            if (needToNormalize) {
                normalizeCells();
            }

        }
    }

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
        return (double) numOfWhitePixels / PIXELS_IN_BOOL_ARRAY;
    }

    private void normalizeCells() {
        TreeMap<Double, ArrayList<Character>> newMap = new TreeMap<>();
        for (var entry : treeMap.entrySet()) {
            double brightness = charBrightnesses.get(entry.getValue().get(0));
            ArrayList<Character> charArr = entry.getValue();
            newMap.put(normalizeBrightness(brightness), charArr);
        }
        treeMap = newMap;
    }

    private void print() {
        for (var entry : treeMap.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }
        System.out.println("-----------------------------------------------\n");
    }


}

