package de.andidebob.databased.blockcipher;

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
}