package de.andidebob.databased.blockcipher;

import de.andidebob.databased.FileBytes;
import de.andidebob.databased.blockcipher.blocks.ByteBlock;
import de.andidebob.databased.blockcipher.padding.PKCS7Helper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PCBCSolver {

    private final BlockDecrypter decrypter;

    public FileBytes solve(FileBytes cipher, ByteBlock iv) {
        ByteBlock[] blocks = extractBlocks(cipher);
        System.out.println("Found " + blocks.length + " blocks!");
        ByteBlock[] decryptedBlocks = new ByteBlock[blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            decryptedBlocks[i] = decrypter.decryptBlock(blocks[i]);
        }
        ByteBlock[] resultBlocks = new ByteBlock[decryptedBlocks.length];
        for (int i = 0; i < decryptedBlocks.length; i++) {
            if (i == 0) {
                resultBlocks[i] = decryptedBlocks[i].xor(iv);
            } else {
                resultBlocks[i] = decryptedBlocks[i].xor(resultBlocks[i - 1].xor(blocks[i - 1]));
            }
        }
        ByteBlock fullBlock = joinBytesAndTrimEnd(resultBlocks);
        return new FileBytes(fullBlock.getBytes());
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

    private ByteBlock joinBytesAndTrimEnd(ByteBlock[] blocks) {
        ByteBlock[] fileBlocks = new ByteBlock[blocks.length - 1];
        System.arraycopy(blocks, 0, fileBlocks, 0, fileBlocks.length);
        ByteBlock lastBlock = blocks[blocks.length - 1];
        if (PKCS7Helper.isFullyPadded(lastBlock)) {
            return ByteBlock.join(fileBlocks);
        }
        ByteBlock blockWithRemovedPadding = PKCS7Helper.removePadding(lastBlock);
        return ByteBlock.join(fileBlocks, blockWithRemovedPadding);

    }
}
