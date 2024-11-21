package de.andidebob.databased.blockcipher.aes;

import de.andidebob.databased.blockcipher.blocks.ByteBlock;
import de.andidebob.helper.ResourceReader;

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
        String[] lines = ResourceReader.readResourceAsString(TABLE_FILE);
        // Drop first 2 lines
        for (int i = 2; i < lines.length; i++) {
            String[] values = lines[i].split("\t");
            if (values.length == 9) {
                BoxValue newValue = new BoxValue(ByteBlock.fromHex(values[0]),
                        ByteBlock.fromHex(values[1]),
                        ByteBlock.fromHex(values[2]),
                        ByteBlock.fromHex(values[3]),
                        ByteBlock.fromHex(values[4]),
                        ByteBlock.fromHex(values[5]),
                        ByteBlock.fromHex(values[6]),
                        ByteBlock.fromHex(values[7]),
                        ByteBlock.fromHex(values[8])
                );
                boxValues.add(newValue);
            }
        }
        if (boxValues.size() != 256) {
            System.err.println("ERROR: The number of box values is incorrect, expected 256, but was " + boxValues.size());
            throw new RuntimeException("ERROR: The number of box values is incorrect");
        }
    }

    public ByteBlock applySBox(byte in) {
        return applySBox(new ByteBlock(in));
    }

    public ByteBlock applySBox(ByteBlock in) {
        ByteBlock out = new ByteBlock(in.size());
        for (int i = 0; i < out.size(); i++) {
            BoxValue boxValue = getBoxValueByIn(in.getByte(i));
            out.setByte(i, boxValue.sb().getByte(0));
        }
        return out;
    }

    public ByteBlock applyInverseSBox(byte in) {
        return applyInverseSBox(new ByteBlock(in));
    }

    public ByteBlock applyInverseSBox(ByteBlock in) {
        ByteBlock out = new ByteBlock(in.size());
        for (int i = 0; i < out.size(); i++) {
            BoxValue boxValue = getBoxValueByIn(in.getByte(i));
            out.setByte(i, boxValue.si().getByte(0));
        }
        return out;
    }

    public ByteBlock applyMult02(byte in) {
        return applyMult02(new ByteBlock(in));
    }

    public ByteBlock applyMult02(ByteBlock in) {
        ByteBlock out = new ByteBlock(in.size());
        for (int i = 0; i < out.size(); i++) {
            BoxValue boxValue = getBoxValueByIn(in.getByte(i));
            out.setByte(i, boxValue.mult02().getByte(0));
        }
        return out;
    }

    public ByteBlock applyMult03(byte in) {
        return applyMult03(new ByteBlock(in));
    }

    public ByteBlock applyMult03(ByteBlock in) {
        ByteBlock out = new ByteBlock(in.size());
        for (int i = 0; i < out.size(); i++) {
            BoxValue boxValue = getBoxValueByIn(in.getByte(i));
            out.setByte(i, boxValue.mult03().getByte(0));
        }
        return out;
    }

    public ByteBlock applyMult09(byte in) {
        return applyMult09(new ByteBlock(in));
    }

    public ByteBlock applyMult09(ByteBlock in) {
        ByteBlock out = new ByteBlock(in.size());
        for (int i = 0; i < out.size(); i++) {
            BoxValue boxValue = getBoxValueByIn(in.getByte(i));
            out.setByte(i, boxValue.mult09().getByte(0));
        }
        return out;
    }

    public ByteBlock applyMult0B(byte in) {
        return applyMult0B(new ByteBlock(in));
    }

    public ByteBlock applyMult0B(ByteBlock in) {
        ByteBlock out = new ByteBlock(in.size());
        for (int i = 0; i < out.size(); i++) {
            BoxValue boxValue = getBoxValueByIn(in.getByte(i));
            out.setByte(i, boxValue.mult0B().getByte(0));
        }
        return out;
    }

    public ByteBlock applyMult0D(byte in) {
        return applyMult0D(new ByteBlock(in));
    }

    public ByteBlock applyMult0D(ByteBlock in) {
        ByteBlock out = new ByteBlock(in.size());
        for (int i = 0; i < out.size(); i++) {
            BoxValue boxValue = getBoxValueByIn(in.getByte(i));
            out.setByte(i, boxValue.mult0D().getByte(0));
        }
        return out;
    }

    public ByteBlock applyMult0E(byte in) {
        return applyMult0E(new ByteBlock(in));
    }

    public ByteBlock applyMult0E(ByteBlock in) {
        ByteBlock out = new ByteBlock(in.size());
        for (int i = 0; i < out.size(); i++) {
            BoxValue boxValue = getBoxValueByIn(in.getByte(i));
            out.setByte(i, boxValue.mult0E().getByte(0));
        }
        return out;
    }

    private BoxValue getBoxValueByIn(byte in) {
        return getBoxValueByIn(new ByteBlock(in));
    }

    private BoxValue getBoxValueByIn(ByteBlock in) {
        return boxValues.stream()
                .filter(b -> b.in.equals(in))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("ERROR: InValue should only be one char between 0X00 and 0xFF"));
    }

    private record BoxValue(ByteBlock in, ByteBlock sb, ByteBlock si, ByteBlock mult02, ByteBlock mult03,
                            ByteBlock mult09, ByteBlock mult0B, ByteBlock mult0D,
                            ByteBlock mult0E) {
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
