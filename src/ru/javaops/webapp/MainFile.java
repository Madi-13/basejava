package ru.javaops.webapp;

import java.io.File;
import java.util.Objects;

public class MainFile {
    public static void traverse(File file, StringBuilder tab) {
        if (file.exists() && !file.getName().equals(".idea") &&
            !file.getName().equals(".git")) {
            System.out.println(tab + file.getName());
            if (file.isDirectory()) {
                tab.append('\t');
                for (File f: Objects.requireNonNull(file.listFiles())) {
                    traverse(f, tab);
                }
                tab.deleteCharAt(tab.length() - 1);
            }
        }
    }

    public static void main(String[] args) {
        traverse(new File("."), new StringBuilder());
    }
}
