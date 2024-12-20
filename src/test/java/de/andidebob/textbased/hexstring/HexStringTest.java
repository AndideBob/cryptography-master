package de.andidebob.textbased.hexstring;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    public void fromHex() {
        String example00 = "6421140f812b6ac177680631813db03b4cb3221ba047771d0f494ad5274e306cccd7afe23d257685c740bbd63a597070914b983e5d13772755638d2f099aa26e4ac731879ed8efc30f4b356583b73e91be1c204e373c07db27fd49dddb63d730f43b02f2bdbd9a9055be1c997aea782a55aa2911bdd6425bc44bb6d86ffbe9c64a00caf23c23f60144bc9d";
        String example01 = "7237142995292fd5367c1d2b9f79e4235bfc2e0faf496b4f4a16159a2907306c8dd2faed3c2623808e50f4cf22496b678655967c6f153829517e8d2f458aa23c42c320d382dea0d9020e7a6dd6b92e8dbc487e5b3a6b2fc121b24684d263c563a0260dbb96ad92d553f64fdb6ff0766553a736";
        String example02 = "13105c09c02b23c4732b06369a73a37348f1231bb905590a4a01139a21072d77ccc8b5f4732666c88451adcb3e4363678248de7b7805772755638d2a1780bb2b0cc7729f9edeefd8010e736a98b925d4af5d630837664eeb2eb308bfd062d578";
        String example03 = "672c514c832c3acf7379063b8b69e4235bfc281bae40764f4d015f94705021658786bfed30237a98934abbd56a4d68728c4adf6a621b77285b62c63d4588be6e4bc93d97d1cbbc970447656393a82891b448310b65240ada2cb84cdddd759071a03a17e9bcb6999044bf5f8962e867634eac7b15b4915940c05aad9562f69bf9400cd4ef6c4bcd1a45b48196a8bfc864";
        String example04 = "6a2b414c842a2487622b053f9d69e4735dfc6c0cb85c320e0f0b1a81704822248fc7a8a338347a9bc745a6d4270c6535844dcf3e7d1e3864477dc82d0c88a12756c321d398c4efc4134b74679fb43bd4af5d630837664ee22eaf4bdded63c475ee2b06e9b4f89ddf4cbc59956ff17d6d01ad35549b9a5f42d94bb7";
        String example05 = "672c511e85652bd5732b06299c3db02a59f63f4ea243320c5d010f813f4036659ccea3a37e7177808657f4cc2245677dc34fdf7266563c21517d8d3d008abf2b58d5728090ccaa97015c7a66d6a33381be1c7d12633f02ca6fae418ecb69c23ca0280dfff3ac96d155f14b9372fb7b2a56ab3718f89d5357d90eb69d21a9dedd5b45cbe77a0eb7155ab689c4bcb1d3785aa6f7d862c764c752171550e95b9600b3f3a9dad61e3270a7c91c8a";
        String example06 = "672c514c943225876272023bd372a2734aea3c1aa242600e5f1006cf70482a61ccd2b2e2277162848b4ca3c86a586c70c37fd9686f0439295163d96e1186ed3b5fc3729183dfbbd247487a7995bf7c80a31c7309722a058f3bb54ddddc63d475ac6902f5b7f891de44f148937aec337844b32e1daa934512dd46a0d805b4cdcc5a0bd5e3721fb70747f99197a0fec4780fb5fd8e61da78c95259151fe419a617a7fbec83ea08603ebad801";
        String example07 = "6421140f812b6ad4736e522a9b78e42346fa221aed527a0a5d1d5f813842646784cfaaa33a22239d894bb5cb3a55247c8518d73e7d04382a532dcf2711c9a43d0cd5379d858aaed9030e766498a92999a94f311678390b8f3fb25f98cd2cd662ef2443efbbbdded54fa7558974f67e6f4fb67b59f8b7525b897dad992fb2c9";
        String example08 = "72641c1c922c3cc6626e52359664ed734cfd2f1cb455660640165f86334f21698986a9f73225669bc710f4da264b6b678a4cde73795a772a5560c8221cc9ac6e5cd43d9094cebac5020e736484fa3b91a259631a632200c86fb64d84cc209071a03911f4b0bd9ac553b41c9d74ea336f4fa1290da8825f5cce02e5992cbf9bc80815cae97f0ef3065abcc482aaac866e1fa2ead777c163c450575e";
        String example09 = "672c514ca32a24c47f78177ebc65a23c5bf70807ae517b0041190d8c700f7634dc90f3a3373465818946a79b295e7d659757967f7956232c512dcc3c11c9a2280c86258198dea6d9000e7a2b84fa2f9ba04a7815706b0dc02bb85bd39f";
        String example10 = "7228584c892b6ac67a67522a9b78b63609fa3f4ebe48730343580b9d3949232498c9faf032282dc8a603b2d226406167c35bd7702a1e3228442dd921459aa2225ac3728799cfefc50e4a716793fa3dd4ae55655b752e1adb2aaf0889d76dde30f72017f3bcad8ad8";
        String example11 = "722a504c8e2a3e87626452389c6fa3365dbf6c1aa540321c40140a8139482a2482c3bfe7207162868857bcde380c77708d4cd37069137737416ec56e1181ac3a0cc93c96d1c6aec4130e616298a37c97ec54311a37394ece6fbe08899f699062a02a02f5f3b992c34ef15e9e3bfc766953bb2b00bd921641dc4da69d31a8dddc4409c1a63c";
        String example12 = "672c514c932029d5737f5233966eb7324ef66c07be1f3238471d11d525542d6a8b86bba32025718d864ef4d8235c6c70911496706f0032361478de2b459da52b0ccd378ad1c7a0c5020e616397b47c9ba25f74";
        HexString hexString00 = HexString.fromHex(example00);
        HexString hexString01 = HexString.fromHex(example01);
        HexString hexString02 = HexString.fromHex(example02);
        HexString hexString03 = HexString.fromHex(example03);
        HexString hexString04 = HexString.fromHex(example04);
        HexString hexString05 = HexString.fromHex(example05);
        HexString hexString06 = HexString.fromHex(example06);
        HexString hexString07 = HexString.fromHex(example07);
        HexString hexString08 = HexString.fromHex(example08);
        HexString hexString09 = HexString.fromHex(example09);
        HexString hexString10 = HexString.fromHex(example10);
        HexString hexString11 = HexString.fromHex(example11);
        HexString hexString12 = HexString.fromHex(example12);
        assertTrue(example00.equalsIgnoreCase(hexString00.toHex()));
        assertTrue(example01.equalsIgnoreCase(hexString01.toHex()));
        assertTrue(example02.equalsIgnoreCase(hexString02.toHex()));
        assertTrue(example03.equalsIgnoreCase(hexString03.toHex()));
        assertTrue(example04.equalsIgnoreCase(hexString04.toHex()));
        assertTrue(example05.equalsIgnoreCase(hexString05.toHex()));
        assertTrue(example06.equalsIgnoreCase(hexString06.toHex()));
        assertTrue(example07.equalsIgnoreCase(hexString07.toHex()));
        assertTrue(example08.equalsIgnoreCase(hexString08.toHex()));
        assertTrue(example09.equalsIgnoreCase(hexString09.toHex()));
        assertTrue(example10.equalsIgnoreCase(hexString10.toHex()));
        assertTrue(example11.equalsIgnoreCase(hexString11.toHex()));
        assertTrue(example12.equalsIgnoreCase(hexString12.toHex()));
    }

    @Test
    public void testToAndFromString() {
        String initial = "AbvA32!";
        HexString hexString = HexString.fromString(initial);
        assertEquals(initial, hexString.toString());
    }
}