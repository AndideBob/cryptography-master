package de.andidebob.textbased.frequency;

import java.util.Comparator;

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
        int comp = Float.compare(o2.percentage, o1.percentage);
        return comp != 0 ? comp : Integer.compare(o1.character(), o2.character());
    };
}