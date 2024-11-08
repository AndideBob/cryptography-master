package de.andidebob.otp;

import lombok.Getter;

import java.util.*;

public class StringXORMap {

    @Getter
    private static final StringXORMap instance = new StringXORMap();

    private HashMap<HexString, HashSet<CharacterMapping>> mappings = new HashMap<>();

    private StringXORMap() {
        HashSet<Character> relevantCharacters = new HashSet<>();
        for (int i = 32; i < 127; i++) {
            relevantCharacters.add((char) i);
        }
        for (Character relevantCharacterA : relevantCharacters) {
            for (Character relevantCharacterB : relevantCharacters) {
                if (relevantCharacterA == relevantCharacterB) {
                    continue;
                }
                HexString a = HexString.ofString("" + relevantCharacterA);
                HexString b = HexString.ofString("" + relevantCharacterB);
                HexString xor = a.xor(b);
                if (!mappings.containsKey(xor)) {
                    mappings.put(xor, new HashSet<>());
                }
                mappings.get(xor).add(new CharacterMapping(relevantCharacterA, relevantCharacterB, xor));
            }
        }
    }

    public Set<CharacterMapping> getPossibleMappings(HexString hexString) {
        if (!mappings.containsKey(hexString)) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(mappings.get(hexString));
    }

    public record CharacterMapping(char a, char b, HexString result) {
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof CharacterMapping other)) return false;

            // Check for interchangeable equality
            return (this.a == other.a && this.b == other.b) ||
                    (this.a == other.b && this.b == other.a);
        }

        // Overriding hashCode method to make pairs interchangeable
        @Override
        public int hashCode() {
            // Using a commutative hash calculation to make pairs interchangeable
            return Objects.hash(Math.min(a, b), Math.max(a, b));
        }
    }
}
