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

    private int countMatches(String[] fromWords) {
        int matches = 0;
        for (int i = 0; i < size(); i++) {
            if (wordsArray[i].equals(fromWords[0])) {
                String[] slice = Arrays.copyOfRange(wordsArray, i, i + fromWords.length);
                if (Arrays.equals(slice, fromWords)) {
                    matches++;
                    i += fromWords.length - 1;
                }
            }
        }
        return matches;
    }

    private int[] gatherMatchIndexes(String[] fromWords, int matches) {
        int[] matchIndexes = new int[matches + 1];
        matches = 0;
        for (int i = 0; i < size(); i++) {
            if (wordsArray[i].equals(fromWords[0])) {
                String[] slice = Arrays.copyOfRange(wordsArray, i, i + fromWords.length);
                if (Arrays.equals(slice, fromWords)) {
                    matchIndexes[matches++] = i;
                    i += fromWords.length - 1;
                }
            }
        }
        matchIndexes[matches] = size() - fromWords.length;
        return matchIndexes;
    }

    private void insertionReplace(String[] fromWords, String[] toWords) {
        int matches = countMatches(fromWords); // counts the number of matches
        int[] matchIndexes = gatherMatchIndexes(fromWords, matches); // gathers the indexes of the matches
        int difference = toWords.length - fromWords.length;
        String[] tempArray = new String[size() + (difference * matches)]; // creates new array with correct size
        // copies the items before first match index
        System.arraycopy(wordsArray, 0, tempArray, 0, matchIndexes[0]);
        int offset = 0;
        // loops through the matches
        for (int i = 0; i < matches; i++) {
            // copies the new words to the match index
            System.arraycopy(toWords, 0, tempArray, matchIndexes[i] + offset, toWords.length);
            // copies the items from the original array between two match indexes (replaceable words excluded)
            System.arraycopy(wordsArray, matchIndexes[i] + fromWords.length, tempArray, matchIndexes[i] + toWords.length + offset,  matchIndexes[i + 1] - matchIndexes[i]);
            offset += difference; // saves the amount of index shifting needed for the next copying
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
