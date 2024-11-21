package de.andidebob.databased.blockcipher.blocks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ByteBlockTest {

    @Test
    void testXor() {
        ByteBlock blockA = ByteBlock.fromHex("FF");
        ByteBlock blockB = ByteBlock.fromHex("FF");
        ByteBlock xor = blockA.xor(blockB);
        assertEquals("00", xor.toString());
    }

    @Test
    void testSplit() {
        ByteBlock block = ByteBlock.fromHex("AABBCCDD");
        ByteBlock[] split = block.split(4);
        assertEquals(4, split.length);
        assertEquals("AA", split[0].toString());
        assertEquals("BB", split[1].toString());
        assertEquals("CC", split[2].toString());
        assertEquals("DD", split[3].toString());
    }

    @Test
    void testShiftRight() {
        ByteBlock block = ByteBlock.fromHex("AABBCCDD");
        assertEquals("AABBCCDD", block.shift(0).toString());
        assertEquals("DDAABBCC", block.shift(1).toString());
        assertEquals("CCDDAABB", block.shift(2).toString());
        assertEquals("BBCCDDAA", block.shift(3).toString());
        assertEquals("AABBCCDD", block.shift(4).toString());
    }

    @Test
    void testShiftLeft() {
        ByteBlock block = ByteBlock.fromHex("AABBCCDD");
        assertEquals("AABBCCDD", block.shift(0).toString());
        assertEquals("BBCCDDAA", block.shift(-1).toString());
        assertEquals("CCDDAABB", block.shift(-2).toString());
        assertEquals("DDAABBCC", block.shift(-3).toString());
        assertEquals("AABBCCDD", block.shift(-4).toString());
    }
}