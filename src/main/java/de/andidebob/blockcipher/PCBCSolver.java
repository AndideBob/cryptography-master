package de.andidebob.blockcipher;

import de.andidebob.otp.hexstring.HexString;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PCBCSolver {

    private final BlockDecrypter decrypter;

    public String solve(HexString cipher, HexString key, HexString iv) {
        HexString[] blocks = extractBlocks(cipher);
        System.out.println("Found " + blocks.length + " blocks!");
        for (HexString block : blocks) {
            System.out.println(block.toHex());
        }
        return "";
    }

    private HexString[] extractBlocks(HexString cipher) {
        int cipherLength = cipher.length() * 8;
        if (cipherLength % decrypter.blockSize != 0) {
            throw new IllegalArgumentException("cipherLength must be a multiple of " + decrypter.blockSize);
        }
        int blockCount = cipherLength / decrypter.blockSize;
        int blockStep = decrypter.blockSize / 8;
        HexString[] blocks = new HexString[blockCount];
        for (int i = 0; i < blockCount; i++) {
            int start = i * blockStep;
            int end = start + blockStep;
            blocks[i] = HexString.fromString(cipher.toString().substring(start, end));
        }
        return blocks;
    }
}
