package de.andidebob.databased.blockcipher;

import lombok.Getter;

@Getter
public abstract class BlockDecrypter {

    protected final int blockSize;

    public BlockDecrypter(int blockSize, ByteBlock key) {
        if (blockSize != key.size() * 8) {
            throw new IllegalArgumentException("Block size must match key length!");
        }
        this.blockSize = blockSize;
    }

    public final ByteBlock decryptBlock(ByteBlock input) {
        validateBlockSize(input);
        return decrypt(input);
    }

    protected abstract ByteBlock decrypt(ByteBlock block);

    protected final void validateBlockSize(ByteBlock input) {
        if (blockSize != input.size() * 8) {
            throw new IllegalArgumentException("Block to decrypt must be of size " + blockSize + "!");
        }
    }
}
