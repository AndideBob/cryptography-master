package de.andidebob.databased.blockcipher.aes;

import de.andidebob.databased.blockcipher.blocks.ByteBlock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AESKeyCalculator128BitTest {

    private final AESKeyCalculator128Bit keyCalculator = new AESKeyCalculator128Bit();

    @Test
    void testCalculateRoundKeysExample1() {
        ByteBlock key = ByteBlock.fromHex("00000000000000000000000000000000");
        ByteBlock[] keys = keyCalculator.calculateRoundKeys(key);
        assertEquals(11, keys.length);
        assertEquals("00000000000000000000000000000000".toUpperCase(), keys[0].toString());
        assertEquals("62636363626363636263636362636363".toUpperCase(), keys[1].toString());
        assertEquals("9b9898c9f9fbfbaa9b9898c9f9fbfbaa".toUpperCase(), keys[2].toString());
        assertEquals("90973450696ccffaf2f457330b0fac99".toUpperCase(), keys[3].toString());
        assertEquals("ee06da7b876a1581759e42b27e91ee2b".toUpperCase(), keys[4].toString());
        assertEquals("7f2e2b88f8443e098dda7cbbf34b9290".toUpperCase(), keys[5].toString());
        assertEquals("ec614b851425758c99ff09376ab49ba7".toUpperCase(), keys[6].toString());
        assertEquals("217517873550620bacaf6b3cc61bf09b".toUpperCase(), keys[7].toString());
        assertEquals("0ef903333ba9613897060a04511dfa9f".toUpperCase(), keys[8].toString());
        assertEquals("b1d4d8e28a7db9da1d7bb3de4c664941".toUpperCase(), keys[9].toString());
        assertEquals("b4ef5bcb3e92e21123e951cf6f8f188e".toUpperCase(), keys[10].toString());
    }

    @Test
    void testCalculateRoundKeysExample2() {
        ByteBlock key = ByteBlock.fromHex("2b7e151628aed2a6abf7158809cf4f3c".toUpperCase());
        ByteBlock[] keys = keyCalculator.calculateRoundKeys(key);
        assertEquals(11, keys.length);
        assertEquals("2b7e151628aed2a6abf7158809cf4f3c".toUpperCase(), keys[0].toString());
        assertEquals("a0fafe1788542cb123a339392a6c7605".toUpperCase(), keys[1].toString());
        assertEquals("f2c295f27a96b9435935807a7359f67f".toUpperCase(), keys[2].toString());
        assertEquals("3d80477d4716fe3e1e237e446d7a883b".toUpperCase(), keys[3].toString());
        assertEquals("ef44a541a8525b7fb671253bdb0bad00".toUpperCase(), keys[4].toString());
        assertEquals("d4d1c6f87c839d87caf2b8bc11f915bc".toUpperCase(), keys[5].toString());
        assertEquals("6d88a37a110b3efddbf98641ca0093fd".toUpperCase(), keys[6].toString());
        assertEquals("4e54f70e5f5fc9f384a64fb24ea6dc4f".toUpperCase(), keys[7].toString());
        assertEquals("ead27321b58dbad2312bf5607f8d292f".toUpperCase(), keys[8].toString());
        assertEquals("ac7766f319fadc2128d12941575c006e".toUpperCase(), keys[9].toString());
        assertEquals("d014f9a8c9ee2589e13f0cc8b6630ca6".toUpperCase(), keys[10].toString());
    }
}