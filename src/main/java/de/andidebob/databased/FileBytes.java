package de.andidebob.databased;

public class FileBytes {

    private final byte[] data;

    public FileBytes(byte[] data) {
        this.data = data;
    }

    public int length() {
        return data.length;
    }

    public byte[] getBytes() {
        return data;
    }
}
