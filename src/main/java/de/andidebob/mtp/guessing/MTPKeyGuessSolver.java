package de.andidebob.mtp.guessing;

import de.andidebob.mtp.MTPSolver;
import de.andidebob.otp.hexstring.BasicHexString;
import de.andidebob.otp.hexstring.HexString;
import de.andidebob.otp.hexstring.XORHexString;

import java.util.Collection;
import java.util.List;

public class MTPKeyGuessSolver extends MTPSolver {
    @Override
    public String[] solve(Collection<HexString> ciphertexts, String cipherTextToDecipher) {
        HexString guessedKey = determineKeyByGuessingProbability(ciphertexts, cipherTextToDecipher.length());
        return new String[0];
    }

    public BasicHexString determineKeyByGuessingProbability(Collection<HexString> ciphertexts, int requiredKeyLength) {
        List<XORHexString> paddedXORs = getPaddedXORs(ciphertexts);

        MTPKeyStorage possibleKeyCharacters = determinePossibleKeyCharacters(paddedXORs, requiredKeyLength);

        System.out.println(possibleKeyCharacters);

        return new BasicHexString("asdf");
    }

    private MTPKeyStorage determinePossibleKeyCharacters(List<XORHexString> paddedXORs, int requiredKeyLength) {
        System.out.println("Determining possible Key Characters...");
        MTPKeyGuesser keyGuesser = new MTPKeyGuesser(paddedXORs);

        MTPKeyStorage possibleKeyCharacters = keyGuesser.determinePossibleKeyCharacters(false);

        int keyLength = possibleKeyCharacters.getKeyLength();
        int earliestCharacterNeeded = keyLength - requiredKeyLength;
        boolean needExtendedAscii = false;
        for (int i = keyLength - 1; i >= earliestCharacterNeeded; i--) {
            if (possibleKeyCharacters.getCharactersAt(i).isEmpty()) {
                needExtendedAscii = true;
            }
        }
        if (needExtendedAscii) {
            System.out.println("Extended Ascii needed, recomputing...");
            return keyGuesser.determinePossibleKeyCharacters(true);
        }
        return possibleKeyCharacters;
    }
}
