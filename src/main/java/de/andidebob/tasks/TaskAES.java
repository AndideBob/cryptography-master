package de.andidebob.tasks;

import de.andidebob.blockcipher.PCBCSolver;
import de.andidebob.blockcipher.aes.AESDecrypter;
import de.andidebob.otp.hexstring.HexString;

import java.util.List;

public class TaskAES implements TaskHandler {
    @Override
    public String[] handleInput(List<String[]> linesByFile) {
        if (linesByFile.size() < 3) {
            throw new RuntimeException("Expected 3 input files! Key, IV and cipher");
        }
        HexString key = HexString.fromHex(linesByFile.get(0)[0]);
        HexString iv = HexString.fromHex(linesByFile.get(1)[0]);
        System.out.println("Key: ");
        System.out.println(key.toHex());
        System.out.println("IV: ");
        System.out.println(iv.toHex());
        HexString cipher = prepareData(linesByFile.get(2));
        System.out.println("Data: ");
        System.out.println(cipher.toHex());
        PCBCSolver solver = new PCBCSolver(new AESDecrypter(key.length() * 8, key));
        solver.solve(cipher, key, iv);
        return new String[0];
    }

    private HexString prepareData(String[] lines) {
        return HexString.fromString(String.join("\n", lines));
    }
}
