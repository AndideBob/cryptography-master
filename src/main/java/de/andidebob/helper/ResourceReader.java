package de.andidebob.helper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ResourceReader {

    public static String[] readResourceAsString(String resourceName) {
        ClassLoader classLoader = ResourceReader.class.getClassLoader();

        try (InputStream inputStream = Objects.requireNonNull(classLoader.getResourceAsStream(resourceName))) {
            List<String> lines = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            }
            return lines.toArray(new String[0]);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to load resource file: " + resourceName, e);
        }
    }

    public static byte[] readResourceAsBytes(String resourceName) {
        ClassLoader classLoader = ResourceReader.class.getClassLoader();

        try (InputStream inputStream = Objects.requireNonNull(classLoader.getResourceAsStream(resourceName))) {
            return inputStream.readAllBytes();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to load resource file: " + resourceName, e);
        }
    }
}
