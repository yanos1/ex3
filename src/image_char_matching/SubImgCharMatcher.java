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
            print();
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
        Double closestKey = treeMap.floorKey(brightness);
        if (closestKey == null) {
            closestKey = treeMap.ceilingKey(brightness);
            return treeMap.get(closestKey).get(0);
        } else {
            double diffLow = Math.abs(closestKey - brightness);
            Double ceilingValue = treeMap.ceilingKey(brightness);
            if (ceilingValue != null) {
                double diffHigh = Math.abs(ceilingValue - brightness);
                if (diffHigh < diffLow) {
                    return treeMap.get(ceilingValue).get(0);
                } else if (diffHigh == diffLow) {
                    char c1 = treeMap.get(closestKey).get(0);
                    char c2 = treeMap.get(ceilingValue).get(0);
                    return c1 < c2 ? c1 : c2;
                }
            }
            return treeMap.get(closestKey).get(0);
        }
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
        print();
        TreeMap<Double, ArrayList<Character>> newMap = new TreeMap<>();
        for (var entry : treeMap.entrySet()) {
            System.out.println(entry);
            System.out.println("value " + entry.getValue());
            System.out.println("item " + entry.getValue().get(0));
//            System.out.println(entry);
//            System.out.println(entry);
            double brightness = charBrightnesses.get(entry.getValue().get(0));
            ArrayList<Character> charArr = entry.getValue();
            newMap.put(normalizeBrightness(brightness), charArr);
            System.out.println("Sucess");

        }
        treeMap = newMap;
        print();
    }

    private void print() {
        for (var entry : treeMap.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }
        System.out.println("-----------------------------------------------\n");
    }


}
