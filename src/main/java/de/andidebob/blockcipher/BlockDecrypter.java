package de.andidebob.blockcipher;

import de.andidebob.otp.hexstring.HexString;
import lombok.Getter;

@Getter
public abstract class BlockDecrypter {

    protected final int blockSize;

    public BlockDecrypter(int blockSize, HexString key) {
        if (blockSize != key.length() * 8) {
            throw new IllegalArgumentException("Block size must match key length!");
        }
        this.blockSize = blockSize;
    }

    public final HexString decryptBlock(HexString input) {
        validateBlockSize(input);
        return decrypt(input);
    }

    protected abstract HexString decrypt(HexString block);

    protected final void validateBlockSize(HexString input) {
        if (blockSize != input.length() * 8) {
            throw new IllegalArgumentException("Block to decrypt must be of size " + blockSize + "!");
        }
    }
}
