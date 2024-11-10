package de.andidebob.otp;

import de.andidebob.otp.hexstring.BasicHexString;
import de.andidebob.otp.hexstring.HexString;
import lombok.Getter;

import java.util.*;

public class StringXORMap {

    @Getter
    private static final StringXORMap instance = new StringXORMap();

    private final HashMap<String, HashSet<CharacterMapping>> basicMappings = new HashMap<>();
    private final HashMap<String, HashSet<CharacterMapping>> extendedAsciiMapping = new HashMap<>();

    private StringXORMap() {
        HashSet<Character> basicCharacters = new HashSet<>();
        HashSet<Character> extendedAsciiCharacters = new HashSet<>();
        for (int i = 32; i <= 255; i++) {
            char c = (char) i;
            if (i <= 127) {
                basicCharacters.add((char) i);
            } else {
                extendedAsciiCharacters.add((char) i);
            }
        }
        for (Character relevantCharacterA : basicCharacters) {
            for (Character relevantCharacterB : basicCharacters) {
                addXORedToMap(basicMappings, relevantCharacterA, relevantCharacterB);
            }
            for (Character extendedCharacterB : extendedAsciiCharacters) {
                addXORedToMap(extendedAsciiMapping, relevantCharacterA, extendedCharacterB);
            }
        }
        for (Character extendedCharacterA : extendedAsciiCharacters) {
            for (Character extendedCharacterB : extendedAsciiCharacters) {
                addXORedToMap(extendedAsciiMapping, extendedCharacterA, extendedCharacterB);
            }
        }
    }

    private static void addXORedToMap(final Map<String, HashSet<CharacterMapping>> map, char a, char b) {
        if (a == b) {
            return;
        }
        BasicHexString aHex = BasicHexString.ofString("" + a);
        BasicHexString bHex = BasicHexString.ofString("" + b);
        BasicHexString xor = aHex.xor(bHex);
        if (!map.containsKey(xor.toString())) {
            map.put(xor.toString(), new HashSet<>());
        }
        map.get(xor.toString()).add(new CharacterMapping(a, b, xor));
    }

    public Set<CharacterMapping> getPossibleMappings(HexString hexString, boolean useExtendedAscii) {
        Set<CharacterMapping> result = new HashSet<>();
        if (useExtendedAscii) {
            result.addAll(getPossibleExtendedMappings(hexString));
        }
        result.addAll(getPossibleBasicMappings(hexString));
        return result;
    }

    private Set<CharacterMapping> getPossibleBasicMappings(HexString hexString) {
        if (!basicMappings.containsKey(hexString.toString())) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(basicMappings.get(hexString.toString()));
    }

    private Set<CharacterMapping> getPossibleExtendedMappings(HexString hexString) {
        if (!extendedAsciiMapping.containsKey(hexString.toString())) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(extendedAsciiMapping.get(hexString.toString()));
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
