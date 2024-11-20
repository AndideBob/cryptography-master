package de.andidebob.blockcipher.aes;

import de.andidebob.helper.ResourceReader;
import de.andidebob.otp.hexstring.HexString;

import java.util.ArrayList;

public class AESSBox {
    private static final String TABLE_FILE = "aes_tables.txt";

    private static AESSBox instance;

    public static AESSBox getInstance() {
        if (instance == null) {
            instance = new AESSBox();
        }
        return instance;
    }

    private final ArrayList<BoxValue> boxValues = new ArrayList<>();

    private AESSBox() {
        String[] lines = ResourceReader.readResource(TABLE_FILE);
        // Drop first 2 lines
        for (int i = 2; i < lines.length; i++) {
            String[] values = lines[i].split("\t");
            if (values.length == 9) {
                BoxValue newValue = new BoxValue(HexString.fromHex(values[0]),
                        HexString.fromHex(values[1]),
                        HexString.fromHex(values[2]),
                        HexString.fromHex(values[3]),
                        HexString.fromHex(values[4]),
                        HexString.fromHex(values[5]),
                        HexString.fromHex(values[6]),
                        HexString.fromHex(values[7]),
                        HexString.fromHex(values[8])
                );
                boxValues.add(newValue);
            }
        }
        if (boxValues.size() != 256) {
            System.err.println("ERROR: The number of box values is incorrect, expected 256, but was " + boxValues.size());
            throw new RuntimeException("ERROR: The number of box values is incorrect");
        }
    }

    public HexString sBox(HexString in) {
        return getIn(in).sb;
    }

    public HexString inverseSBox(HexString in) {
        return getIn(in).si;
    }

    public HexString mult02(HexString in) {
        return getIn(in).mult02;
    }

    public HexString mult03(HexString in) {
        return getIn(in).mult03;
    }

    public HexString mult09(HexString in) {
        return getIn(in).mult09;
    }

    public HexString mult0B(HexString in) {
        return getIn(in).mult0B;
    }

    public HexString mult0D(HexString in) {
        return getIn(in).mult0D;
    }

    public HexString mult0E(HexString in) {
        return getIn(in).mult0E;
    }

    private BoxValue getIn(HexString in) {
        return boxValues.stream().filter(b -> b.in.equals(in)).findFirst().orElseThrow(() -> new RuntimeException("ERROR: InValue should only be one char between 0X00 and 0xFF"));
    }

    private record BoxValue(HexString in, HexString sb, HexString si, HexString mult02, HexString mult03,
                            HexString mult09, HexString mult0B, HexString mult0D,
                            HexString mult0E) {
        @Override
        public String toString() {
            return "BoxValue{" +
                    "in=" + in.toHex() +
                    ", sb=" + sb.toHex() +
                    ", si=" + si.toHex() +
                    ", 02=" + mult02.toHex() +
                    ", 03=" + mult03.toHex() +
                    ", 09=" + mult09.toHex() +
                    ", 0B=" + mult0B.toHex() +
                    ", 0D=" + mult0D.toHex() +
                    ", 0E=" + mult0E.toHex() +
                    '}';
        }
    }
}
