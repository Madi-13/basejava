package ru.javaops.webapp;

import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.storage.AbstractArrayStorage;
import ru.javaops.webapp.storage.ArrayStorage;

/**
 * Test for your ru.javaops.webapp.storage.ArrayStorage implementation
 */

public class MainTestArrayStorage {
    private static final AbstractArrayStorage ARRAY_STORAGE = new ArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid1");
        Resume r2 = new Resume("uuid0");
        Resume r3 = new Resume("uuid3");
        Resume r4 = new Resume("uuid0");
        Resume r5 = new Resume("uuid5");

        ARRAY_STORAGE.update(r1);
        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);

        ARRAY_STORAGE.update(r4);
        ARRAY_STORAGE.update(r5);
        System.out.println(ARRAY_STORAGE.get("uuid0") == r2);
        System.out.println(ARRAY_STORAGE.get("uuid0") == r4);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        System.out.println("good");
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
