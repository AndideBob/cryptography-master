package de.andidebob.databased.blockcipher;

import de.andidebob.databased.FileBytes;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PCBCSolver {

    private final BlockDecrypter decrypter;

    public String solve(FileBytes cipher, ByteBlock key, ByteBlock iv) {
        ByteBlock[] blocks = extractBlocks(cipher);
        System.out.println("Found " + blocks.length + " blocks!");
        for (ByteBlock block : blocks) {
            System.out.println(block.toHex());
        }
        return "";
    }

    private ByteBlock[] extractBlocks(FileBytes cipher) {
        int cipherLength = cipher.length() * 8;
        if (cipherLength % decrypter.blockSize != 0) {
            throw new IllegalArgumentException("cipherLength must be a multiple of " + decrypter.blockSize);
        }
        int blockCount = cipherLength / decrypter.blockSize;
        int blockSizeInBytes = decrypter.blockSize / 8;
        byte[] bytes = cipher.getBytes();
        ByteBlock[] blocks = new ByteBlock[blockCount];
        for (int i = 0; i < blockCount; i++) {
            blocks[i] = new ByteBlock(blockSizeInBytes);
            for (int j = 0; j < blockSizeInBytes; j++) {
                blocks[i].setByte(j, bytes[i * blockSizeInBytes + j]);
            }
        }
        return blocks;
    }
}
