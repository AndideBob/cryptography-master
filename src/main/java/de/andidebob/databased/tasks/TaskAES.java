package de.andidebob.databased.tasks;

import de.andidebob.databased.DataBasedTask;
import de.andidebob.databased.FileBytes;
import de.andidebob.databased.blockcipher.PCBCSolver;
import de.andidebob.databased.blockcipher.aes.AESDecrypter;
import de.andidebob.databased.blockcipher.blocks.ByteBlock;

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
        return solver.solve(cipher, iv);
    }

    private ByteBlock getByteBlockFromHexFile(FileBytes file) {
        return ByteBlock.fromHex(new String(file.getBytes()));
    }
}
