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
        for (int i = 0; i < size(); i++) {
            if (wordsArray[i].equals(fromWords[0])) {
                String[] slice = Arrays.copyOfRange(wordsArray, i, i + fromWords.length);
                if (Arrays.equals(slice, fromWords)) {
                    String[] tempArray = new String[fromWords.length];
                    Arrays.fill(tempArray, "");
                    System.arraycopy(toWords, 0, tempArray, 0, toWords.length);
                    System.arraycopy(tempArray, 0, wordsArray, i, tempArray.length);
                    i += toWords.length - 1;
                }
            }
        }
        removeWord("");
    }

    private void insertionReplace(String[] fromWords, String[] toWords) {
        int matches = 0, difference = toWords.length - fromWords.length;
        for (int i = 0; i < size(); i++) {
            if (wordsArray[i].equals(fromWords[0])) {
                String[] slice = Arrays.copyOfRange(wordsArray, i, i + fromWords.length);
                if (Arrays.equals(slice, fromWords)) {
                    matches++;
                    i += fromWords.length - 1;
                }
            }
        }
        String[] tempArray = new String[size() + (difference * matches)];
        System.arraycopy(wordsArray, 0, tempArray, 0, size());
        int offset = 0;
        for (int i = 0; i < size(); i++) {
            if (wordsArray[i].equals(fromWords[0])) {
                String[] slice = Arrays.copyOfRange(wordsArray, i, i + fromWords.length);
                if (Arrays.equals(slice, fromWords)) {
                    System.arraycopy(toWords, 0, tempArray, i + offset, toWords.length);
                    System.arraycopy(wordsArray, i + fromWords.length, tempArray, i + toWords.length + offset, size() - i - fromWords.length);
                    i += fromWords.length - 1;
                    offset += difference;
                }
            }
        }
        wordsArray = tempArray;
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
