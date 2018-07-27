package com.codecool.thehistory;

import java.util.*;

public class TheHistoryLinkedList implements TheHistory {
    /**
     * This implementation should use a String LinkedList so don't change that!
     */
    private List<String> wordsLinkedList = new LinkedList<>();

    @Override
    public void add(String text) {
        wordsLinkedList.addAll(Arrays.asList(text.split("\\s+")));
    }

    @Override
    public void removeWord(String wordToBeRemoved) {
        while (wordsLinkedList.remove(wordToBeRemoved));
    }

    @Override
    public int size() {
        return wordsLinkedList.size();
    }

    @Override
    public void clear() {
        wordsLinkedList.clear();
    }

    @Override
    public void replaceOneWord(String from, String to) {
        Collections.replaceAll(wordsLinkedList, from, to);
    }

    @Override
    public void replaceMoreWords(String[] fromWords, String[] toWords) {
        ListIterator<String> iterator = wordsLinkedList.listIterator();
        while (iterator.hasNext()) {
            boolean match;
            String current = iterator.next();
            if (current.equals(fromWords[0]) && (iterator.hasNext() || fromWords.length == 1)) {
                match = true;
                int i;
                for (i = 0; i < fromWords.length; i++, current = iterator.next()) {
                    if (!current.equals(fromWords[i])) {
                        match = false;
                        break;
                    }
                    if (!iterator.hasNext()) {
                        if (i < fromWords.length - 1) match = false;
                        break;
                    }
                }
                if (i == size() - 1) i++;
                if (match && i != size() && i != 0 && !(i < fromWords.length)) iterator.previous();
                if (match && !iterator.hasNext() && (i == 0 || i < fromWords.length)) i++;
                for (; i > 0; i--) {
                    iterator.previous();
                    if (match) {
                        iterator.remove();
                    }
                }
                if (match) {
                    for (String toWord : toWords) {
                        iterator.add(toWord);
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String word : wordsLinkedList) {
            sb.append(word).append(" ");
        }
        if (sb.length() > 0) sb.deleteCharAt(sb.length() - 1); // last space char
        return sb.toString();
    }

}
