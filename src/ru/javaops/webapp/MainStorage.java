package ru.javaops.webapp;

import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.storage.MapStorage;
import ru.javaops.webapp.storage.Storage;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Interactive test for ru.javaops.webapp.storage.ArrayStorage implementation
 * (just run, no need to understand)
 */
public class MainStorage {
    private final static Storage STORAGE = new MapStorage();

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Resume r;
        while (true) {
            System.out.print("Введите одну из команд - (list | save uuid | delete uuid | get uuid | update uuid | clear | exit): ");
            String[] params = reader.readLine().trim().toLowerCase().split(" ");
            if (params.length < 1 || params.length > 2) {
                System.out.println("Неверная команда.");
                continue;
            }
            String uuid = null;
            if (params.length == 2) {
                uuid = params[1].intern();
            }
            switch (params[0]) {
                case "list":
                    printAll();
                    break;
                case "size":
                    System.out.println(STORAGE.size());
                    break;
                case "save":
                    r = new Resume(uuid);
                    STORAGE.save(r);
                    printAll();
                    break;
                case "delete":
                    STORAGE.delete(uuid);
                    printAll();
                    break;
                case "get":
                    System.out.println(STORAGE.get(uuid));
                    break;
                case "update":
                    r = new Resume(uuid);
                    STORAGE.update(r);
                    printAll();
                    break;
                case "clear":
                    STORAGE.clear();
                    printAll();
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Неверная команда.");
                    break;
            }
        }
    }

    static void printAll() {
        if (STORAGE instanceof MapStorage) {
            return;
        }
        Resume[] all = STORAGE.getAll();
        System.out.println("----------------------------");
        if (all.length == 0) {
            System.out.println("Empty");
        } else {
            for (Resume r : all) {
                System.out.println(r);
            }
        }
        System.out.println("----------------------------");
    }
}