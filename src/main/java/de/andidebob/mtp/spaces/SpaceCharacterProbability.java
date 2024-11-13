package de.andidebob.mtp.spaces;

import de.andidebob.otp.hexstring.HexString;

import java.util.Objects;

public record SpaceCharacterProbability(HexString hexString, int characterIndex, double probability) {

    public Character getHexStringChar() {
        String s = hexString().convertToString();
        return s.length() > characterIndex ? hexString.convertToString().charAt(characterIndex) : null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpaceCharacterProbability that = (SpaceCharacterProbability) o;
        return characterIndex == that.characterIndex && Objects.equals(hexString, that.hexString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hexString, characterIndex);
    }
}
