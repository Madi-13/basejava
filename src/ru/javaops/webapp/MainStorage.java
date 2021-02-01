package ru.javaops.webapp;

import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.storage.MapStorageByResume;
import ru.javaops.webapp.storage.Storage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Interactive test for ru.javaops.webapp.storage.ArrayStorage implementation
 * (just run, no need to understand)
 */
public class MainStorage {
    private final static Storage STORAGE = new MapStorageByResume();

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Resume r;
        while (true) {
            System.out.print("Введите одну из команд - (list | save fullName or save uuid fullName | delete uuid  | get uuid | update uuid fullName | size | clear | exit): ");
            String[] params = reader.readLine().trim().split(" ");
            if (params.length < 1 || params.length > 3) {
                System.out.println("Неверная команда.");
                continue;
            }
            params[1] = params[1].toLowerCase();
            switch (params[0]) {
                case "list":
                    printAll();
                    break;
                case "size":
                    System.out.println(STORAGE.size());
                    break;
                case "save":
                    if (params.length == 2) {
                        r = new Resume(params[1]);
                    } else {
                        r = new Resume(params[1], params[2]);
                    }
                    STORAGE.save(r);
                    printAll();
                    break;
                case "delete":
                    STORAGE.delete(params[1]);
                    printAll();
                    break;
                case "get":
                    System.out.println(STORAGE.get(params[1]));
                    break;
                case "update":
                    r = new Resume(params[1], params[2]);
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
        List<Resume> all = STORAGE.getAllSorted();
        System.out.println("----------------------------");
        if (all.size() == 0) {
            System.out.println("Empty");
        } else {
            for (Resume r : all) {
                System.out.println(r);
            }
        }
        System.out.println("----------------------------");
    }
}