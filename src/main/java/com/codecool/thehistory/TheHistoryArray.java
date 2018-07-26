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

    @Override
    public void replaceMoreWords(String[] fromWords, String[] toWords) {
        for (int i = 0; i < size(); i++) {
            if (wordsArray[i].equals(fromWords[0])) {
                String[] slice = Arrays.copyOfRange(wordsArray, i, i + fromWords.length);
                if (Arrays.equals(slice, fromWords)) {
                    String[] tempArray = new String[size() + (toWords.length - fromWords.length)];
                    System.arraycopy(wordsArray, 0, tempArray, 0, i);
                    System.arraycopy(toWords, 0, tempArray, i, toWords.length);
                    System.arraycopy(wordsArray, i + fromWords.length, tempArray, i + toWords.length, size() - i - fromWords.length);
                    wordsArray = tempArray;
                    i += toWords.length - 1;
                }
            }
        }
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
