package de.andidebob.databased.tasks;

import de.andidebob.databased.DataBasedTask;
import de.andidebob.databased.FileBytes;
import de.andidebob.databased.blockcipher.ByteBlock;
import de.andidebob.databased.blockcipher.PCBCSolver;
import de.andidebob.databased.blockcipher.aes.AESDecrypter;

import java.util.List;

public class TaskAES extends DataBasedTask {
    @Override
    public FileBytes handleInput(List<FileBytes> files) {
        if (files.size() < 3) {
            throw new RuntimeException("Expected 3 input files! Key, IV and cipher");
        }
        ByteBlock key = getByteBlockFromHexFile(files.get(0));
        ByteBlock iv = getByteBlockFromHexFile(files.get(1));
        System.out.println("Key: ");
        System.out.println(key.toHex());
        System.out.println("IV: ");
        System.out.println(iv.toHex());
        FileBytes cipher = files.get(2);
        PCBCSolver solver = new PCBCSolver(new AESDecrypter(key.size() * 8, key));
        solver.solve(cipher, key, iv);
        return new FileBytes(new byte[0]);
    }

    private ByteBlock getByteBlockFromHexFile(FileBytes file) {
        return ByteBlock.fromHex(new String(file.getBytes()));
    }
}
