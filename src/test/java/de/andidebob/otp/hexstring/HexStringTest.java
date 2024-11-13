package de.andidebob.otp.hexstring;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HexStringTest {

    @Test
    public void testPadToLength() {
        HexString hexString = new HexString("1234".toCharArray());
        String hex = hexString.toHex();
        HexString padded = hexString.padToLength(6);
        assertEquals("0000" + hex, padded.toHex());
    }

    @Test
    public void testShortenToLength() {
        HexString hexString = new HexString("12345678".toCharArray());
        HexString shortened = hexString.shortenToLength(4);
        assertEquals("5678", shortened.toString());
    }

    @Test
    public void testXor() {
        HexString hexString1 = HexString.fromHex("2F7E");
        HexString hexString2 = HexString.fromHex("1111");
        HexString result = hexString1.xor(hexString2);
        assertEquals("3E6F", result.toHex());
    }

    @Test
    public void testToHex() {
        HexString hexString = new HexString("1234".toCharArray());
        assertEquals("31323334", hexString.toHex());
    }

    @Test
    public void testToAndFromHex() {
        String hex = "5F2DFF12";
        HexString hexString = HexString.fromHex(hex);
        assertEquals(hex, hexString.toHex());
    }

    @Test
    public void testToAndFromString() {
        String initial = "AbvA32!";
        HexString hexString = HexString.fromString(initial);
        assertEquals(initial, hexString.toString());
    }
}