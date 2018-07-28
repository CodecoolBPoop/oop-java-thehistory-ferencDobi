package com.codecool.thehistory;

import java.util.Arrays;

public class TheHistoryArray implements TheHistory {

    /**
     * This implementation should use a String array so don't change that!
     */
    private String[] wordsArray = new String[0];

    @Override
    public void add(String text) {
        wordsArray = text.split("\\s+");
    }

    @Override
    public void removeWord(String wordToBeRemoved) {
        wordsArray = Arrays.stream(wordsArray).filter(word -> !word.equals(wordToBeRemoved)).toArray(String[]::new);
    }

    @Override
    public int size() {
        return wordsArray.length;
    }

    @Override
    public void clear() {
        wordsArray = new String[0];
    }

    @Override
    public void replaceOneWord(String from, String to) {
        for (int i = 0; i < size(); i++) {
            if (wordsArray[i].equals(from)) wordsArray[i] = to;
        }
    }

    private void equalNumberReplace(String[] fromWords, String[] toWords) {
        for (int i = 0; i < size(); i++) {
            if (wordsArray[i].equals(fromWords[0])) {
                String[] slice = Arrays.copyOfRange(wordsArray, i, i + fromWords.length);
                if (Arrays.equals(slice, fromWords)) {
                    System.arraycopy(toWords, 0, wordsArray, i, toWords.length);
                    i += toWords.length - 1;
                }
            }
        }
    }

    private void removalReplace(String[] fromWords, String[] toWords) {
        String[] toWordsPad = new String[fromWords.length];
        Arrays.fill(toWordsPad, "");
        System.arraycopy(toWords, 0, toWordsPad, 0, toWords.length);
        equalNumberReplace(fromWords, toWordsPad);
        removeWord("");
    }

    private void insertionReplace(String[] fromWords, String[] toWords) {
        int matches = 0, difference = toWords.length - fromWords.length;
        // loops through the array and counts matches
        for (int i = 0; i < size(); i++) {
            if (wordsArray[i].equals(fromWords[0])) {
                String[] slice = Arrays.copyOfRange(wordsArray, i, i + fromWords.length);
                if (Arrays.equals(slice, fromWords)) {
                    matches++;
                    i += fromWords.length - 1;
                }
            }
        }
        String[] tempArray = new String[size() + (difference * matches)]; // creates new array with correct size
        System.arraycopy(wordsArray, 0, tempArray, 0, size()); // copies initial items
        int offset = 0;
        // loops through the array a second time
        for (int i = 0; i < size(); i++) {
            if (wordsArray[i].equals(fromWords[0])) {
                String[] slice = Arrays.copyOfRange(wordsArray, i, i + fromWords.length);
                if (Arrays.equals(slice, fromWords)) {
                    // if there's a match copies the new words to the new array at the correct position
                    System.arraycopy(toWords, 0, tempArray, i + offset, toWords.length);
                    // copies the remaining elements from the original array to their new position in the new array
                    System.arraycopy(wordsArray, i + fromWords.length, tempArray, i + toWords.length + offset, size() - i - fromWords.length);
                    i += fromWords.length - 1; // makes the loop skip to the end of the matched elements
                    offset += difference; // saves the amount of positions the elements need to be shifted each time when copying
                }
            }
        }
        wordsArray = tempArray; // sets the original array's reference to the new array
    }

    @Override
    public void replaceMoreWords(String[] fromWords, String[] toWords) {
        if (fromWords.length == toWords.length) equalNumberReplace(fromWords, toWords);
        else if (fromWords.length > toWords.length) removalReplace(fromWords, toWords);
        else insertionReplace(fromWords, toWords);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String word : wordsArray) {
            sb.append(word).append(" ");
        }
        if (sb.length() > 0) sb.deleteCharAt(sb.length() - 1); // last space char
        return sb.toString();
    }
}
