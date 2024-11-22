package de.andidebob.databased.blockcipher.padding;

import de.andidebob.databased.blockcipher.blocks.ByteBlock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PKCS7HelperTest {

    @Test
    void testIsFullyPadded() {
        ByteBlock padBlock = ByteBlock.fromHex("10101010101010101010101010101010");
        ByteBlock nonPadBlock = ByteBlock.fromHex("100F0F0F0F0F0F0F0F0F0F0F0F0F0F0F");
        ByteBlock nonPadDataBlock = ByteBlock.fromHex("100F0F0F0F0F0F0F0F0F0F0F0F0F0F10");
        assertTrue(PKCS7Helper.isFullyPadded(padBlock));
        assertFalse(PKCS7Helper.isFullyPadded(nonPadBlock));
        assertFalse(PKCS7Helper.isFullyPadded(nonPadDataBlock));
    }

    @Test
    void testRemovePadding() {
        ByteBlock pad01 = ByteBlock.fromHex("000102030405060708090A0B0C0D0E01");
        assertEquals("000102030405060708090A0B0C0D0E", PKCS7Helper.removePadding(pad01).toString());
        ByteBlock pad02 = ByteBlock.fromHex("000102030405060708090A0B0C0D0202");
        assertEquals("000102030405060708090A0B0C0D", PKCS7Helper.removePadding(pad02).toString());
        ByteBlock pad15 = ByteBlock.fromHex("000F0F0F0F0F0F0F0F0F0F0F0F0F0F0F");
        assertEquals("00", PKCS7Helper.removePadding(pad15).toString());
    }
}