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
        wordsLinkedList.removeIf(word -> word.equals(wordToBeRemoved));
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
            // checks if the first item matches, and whether there's more items to check for
            if (current.equals(fromWords[0]) && (iterator.hasNext() || fromWords.length == 1)) {
                match = true;
                int i;
                // loops through the items to check
                for (i = 0; i < fromWords.length; i++, current = iterator.next()) {
                    // breaks out of the loop if there's no match or no elements left to check
                    if (!current.equals(fromWords[i]) || !iterator.hasNext()) {
                        if (!current.equals(fromWords[i]) || i < fromWords.length - 1) match = false;
                        break;
                    }
                }
                // necessary adjustments if the loop exited without a break
                if (match && !(i < fromWords.length)) iterator.previous();
                if (match && i < fromWords.length) i++;
                // rewinds the iterator to the starting point and removes elements if there was a match
                for (; i > 0; i--) {
                    iterator.previous();
                    if (match) iterator.remove();
                }
                // adds the new elements if there was a match
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
