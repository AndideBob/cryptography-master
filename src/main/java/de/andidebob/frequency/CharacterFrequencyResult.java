package de.andidebob.frequency;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CharacterFrequencyResult {

    private final HashMap<Character, Integer> map;


    public CharacterFrequencyResult(Set<Character> charactersToConsider) {
        this.map = new HashMap<>();
        charactersToConsider.forEach(c -> map.put(c, 0));
    }

    public void increment(
            Character character
    ) {
        if (map.containsKey(character)) {
            map.put(character, map.get(character) + 1);
        }
    }

    public List<CharacterFrequency> getFrequencies() {
        int totalAmount = map.values().stream().mapToInt(Integer::intValue).sum();
        return map.entrySet()
                .stream()
                .map(entry -> new CharacterFrequency(entry.getKey(), entry.getValue(), 1f * entry.getValue() / totalAmount))
                .sorted(CharacterFrequency.comparator)
                .toList();
    }

    @Override
    public String toString() {
        List<CharacterFrequency> frequencies = getFrequencies();
        if (frequencies.isEmpty()) {
            return "";
        }
        int amountPadding = frequencies.stream()
                .map(CharacterFrequency::amount)
                .max(Integer::compareTo)
                .get()
                .toString()
                .length();
        return frequencies.stream()
                .map(frequency -> String.format("%s | %s | %s",
                        frequency.character,
                        frequency.getPaddedAmount(amountPadding),
                        new DecimalFormat("0.00000").format(frequency.percentage)))
                .collect(Collectors.joining("\n"));
    }

    public record CharacterFrequency(char character, int amount, float percentage) {
        public String getPaddedAmount(int padding) {
            StringBuilder sb = new StringBuilder();
            String amountString = "" + amount;
            while (sb.length() < padding - amountString.length()) {
                sb.append(" ");
            }
            sb.append(amountString);
            return sb.toString();
        }

        public static Comparator<CharacterFrequency> comparator = (o1, o2) -> {
            int comp = Integer.compare(o2.amount, o1.amount);
            return comp != 0 ? comp : Integer.compare(o1.character(), o2.character());
        };
    }
}
