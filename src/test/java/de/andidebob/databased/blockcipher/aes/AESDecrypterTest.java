package de.andidebob.databased.blockcipher.aes;

import de.andidebob.databased.blockcipher.blocks.ByteBlock;
import de.andidebob.databased.blockcipher.blocks.ByteMatrix;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AESDecrypterTest extends AESDecrypter {

    private static final ByteBlock KEY = ByteBlock.fromHex("000102030405060708090A0B0C0D0E0F");

    public AESDecrypterTest() {
        super(128, KEY);
    }

    @Test
    void testApplyKeyAddition() {
        ByteMatrix before = ByteMatrix.of(ByteBlock.fromHex("000102030405060708090A0B0C0D0E0F"));
        ByteMatrix after = applyKeyAddition(before, KEY);
        assertEquals("00000000000000000000000000000000", after.merge().toString());
    }

    @Test
    void testApplyInverseMixColumns() {
        ByteMatrix before = ByteMatrix.of(ByteBlock.fromHex("473794ED40D4E4A5A3703AA64C9F42BC"));
        ByteMatrix after = applyInverseMixColumns(before);
        assertEquals("876E46A6F24CE78C4D904AD897ECC395", after.merge().toString());
    }

    @Test
    void testApplyInverseShiftRows() {
        ByteMatrix before = ByteMatrix.of(ByteBlock.fromHex("000102030405060708090A0B0C0D0E0F"));
        ByteMatrix after = applyInverseShiftRows(before);
        assertEquals("000D0A0704010E0B0805020F0C090603", after.merge().toString());
    }

    @Test
    void testApplyInverseByteSubstitution() {
        ByteMatrix before = ByteMatrix.of(ByteBlock.fromHex("000102030405060708090A0B0C0D0E0F"));
        ByteMatrix after = applyInverseByteSubstitution(before);
        assertEquals("52096AD53036A538BF40A39E81F3D7FB", after.merge().toString());
    }
}