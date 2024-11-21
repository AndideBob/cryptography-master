package de.andidebob.databased.blockcipher.blocks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ByteMatrixTest {

    @Test
    void mergeShouldMatchInput() {
        ByteBlock block = ByteBlock.fromHex("000102030405060708090A0B0C0D0E0F");
        ByteMatrix matrix = ByteMatrix.of(block);
        assertEquals(block, matrix.merge());
    }

    @Test
    void rowsShouldMatchInput() {
        ByteBlock block = ByteBlock.fromHex("000102030405060708090A0B0C0D0E0F");
        ByteMatrix matrix = ByteMatrix.of(block);
        assertEquals("0004080C", matrix.getRow(0).toString());
        assertEquals("0105090D", matrix.getRow(1).toString());
        assertEquals("02060A0E", matrix.getRow(2).toString());
        assertEquals("03070B0F", matrix.getRow(3).toString());
    }
}