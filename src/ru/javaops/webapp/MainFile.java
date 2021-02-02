package ru.javaops.webapp;

import java.io.File;
import java.util.Objects;

public class MainFile {
    public static void traverse(File file) {
        if (file.exists() && !file.getName().equals(".idea") &&
            !file.getName().equals(".git")) {
            System.out.println(file.getName());
            if (file.isDirectory()) {
                for (File f: Objects.requireNonNull(file.listFiles())) {
                    traverse(f);
                }
            }
        }
    }

    public static void main(String[] args) {
        traverse(new File("."));
    }
}
