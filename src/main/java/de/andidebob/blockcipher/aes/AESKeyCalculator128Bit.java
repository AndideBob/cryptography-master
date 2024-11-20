package de.andidebob.blockcipher.aes;

import de.andidebob.otp.hexstring.HexString;

public class AESKeyCalculator128Bit implements AESKeyCalculator {

    @Override
    public HexString[] calculateRoundKeys(HexString key) {
        if (key.length() != 16) {
            throw new RuntimeException("Can not calculateKeys for a key value other than 128 bit!");
        }
        HexString[] keys = new HexString[10];
        keys[0] = key;
        for (int i = 1; i < keys.length; i++) {
            HexString shifted = byteShiftLeft(keys[i - 1]);
            HexString sBoxed = applySBox(shifted);
            keys[i] = applyRoundCoefficient(sBoxed, i);
        }
        return keys;
    }

    private HexString byteShiftLeft(HexString before) {
        String beforeS = before.toString();
        return HexString.fromString(beforeS.substring(1) + beforeS.charAt(0));
    }

    private HexString applySBox(HexString before) {
        HexString[] split = before.splitToCharacters();
        StringBuilder s = new StringBuilder();
        for (HexString hexStringChar : split) {
            s.append(AESSBox.getInstance().sBox(hexStringChar));
        }
        return HexString.fromString(s.toString());
    }

    private HexString applyRoundCoefficient(HexString before, int roundNumber) {
        HexString firstByte = HexString.fromString("" + before.charAt(0));
        HexString roundCoefficient = HexString.fromHex(String.format("%02X", roundNumber));
        String s = firstByte.xor(roundCoefficient).toString();
        return HexString.fromString(s + before.toString().substring(1));
    }
}
