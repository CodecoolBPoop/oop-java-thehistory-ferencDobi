package com.codecool.thehistory;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class TheHistoryArrayList implements TheHistory {
    /**
     * This implementation should use a String ArrayList so don't change that!
     */
    private List<String> wordsArrayList = new ArrayList<>();

    @Override
    public void add(String text) {
        wordsArrayList.addAll(Arrays.asList(text.split("\\s+")));
    }

    @Override
    public void removeWord(String wordToBeRemoved) {
        wordsArrayList.removeIf(word -> word.equals(wordToBeRemoved));
    }

    @Override
    public int size() {
        return wordsArrayList.size();
    }

    @Override
    public void clear() {
        wordsArrayList.clear();
    }

    @Override
    public void replaceOneWord(String from, String to) {
        Collections.replaceAll(wordsArrayList, from, to);
    }

    @Override
    public void replaceMoreWords(String[] fromWords, String[] toWords) {
        int matchIndex, startIndex = 0;
        do {
            matchIndex = wordsArrayList.subList(startIndex, size()).indexOf(fromWords[0]);
            startIndex += matchIndex;
            if (matchIndex == -1 || startIndex >= size() - (fromWords.length - 1)) break;
            if (wordsArrayList.subList(startIndex, startIndex + fromWords.length).equals(Arrays.asList(fromWords))) {
                wordsArrayList.subList(startIndex, startIndex + fromWords.length).clear();
                wordsArrayList.addAll(startIndex, Arrays.asList(toWords));
                startIndex += toWords.length;
                if (startIndex >= size() - (fromWords.length - 1)) break;
            } else startIndex++;

        }
        while (true);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String word : wordsArrayList) {
            sb.append(word).append(" ");
        }
        if (sb.length() > 0) sb.deleteCharAt(sb.length() - 1); // last space char
        return sb.toString();
    }

}
