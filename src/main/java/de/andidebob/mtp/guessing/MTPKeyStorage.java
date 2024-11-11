package de.andidebob.mtp.guessing;

import java.util.*;

public class MTPKeyStorage {

    private final ArrayList<HashSet<Character>> possibilities;

    public MTPKeyStorage() {
        possibilities = new ArrayList<>();
    }

    public void mergePossibilities(int index, Collection<Character> newPossibilities) {
        while (possibilities.size() <= index) {
            possibilities.add(new HashSet<>());
        }
        if (possibilities.get(index).isEmpty()) {
            possibilities.get(index).addAll(newPossibilities);
        } else {
            boolean hasOverlap = possibilities.get(index)
                    .stream()
                    .anyMatch(newPossibilities::contains);

            if (hasOverlap) {
                // Keep only the overlapping items
                possibilities.get(index).retainAll(newPossibilities);
            } else {
                // No overlap, add all new possibilities
                possibilities.get(index).addAll(newPossibilities);
            }
        }
    }

    public Set<Character> getCharactersAt(int index) {
        return Collections.unmodifiableSet(possibilities.get(index));
    }

    public long getPermutations() {
        long number = 1;
        for (HashSet<Character> possibility : possibilities) {
            if (!possibility.isEmpty()) {
                number *= possibility.size();
            }
        }
        return number;
    }

    public int getKeyLength() {
        return possibilities.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("KeyGuess{\n");
        for (int i = 0; i < possibilities.size(); i++) {
            sb.append("[").append(i).append("] -> ");
            sb.append(possibilities.get(i));
            sb.append("\n");
        }
        sb.append("}");
        return sb.toString();
    }
}
