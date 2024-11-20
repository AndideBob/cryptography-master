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
        int blockSizeInBytes = decrypter.blockSize / 8;
        HexString[] bytes = cipher.splitToCharacters();
        HexString[] blocks = new HexString[blockCount];
        for (int i = 0; i < blockCount; i++) {
            HexString[] block = new HexString[blockSizeInBytes];
            for (int j = 0; j < blockSizeInBytes; j++) {
                block[j] = bytes[i * blockSizeInBytes + j];
            }
            blocks[i] = HexString.join(block);
        }
        return blocks;
    }
}
