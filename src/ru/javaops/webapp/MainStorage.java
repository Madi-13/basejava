package ru.javaops.webapp;

import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.storage.MapStorageByUuid;
import ru.javaops.webapp.storage.Storage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Interactive test for ru.javaops.webapp.storage.ArrayStorage implementation
 * (just run, no need to understand)
 */
public class MainStorage {
    private final static Storage STORAGE = new MapStorageByUuid();

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Resume r;
        while (true) {
            System.out.print("Введите одну из команд - (list | save fullName | delete uuid  | get uuid | update uuid fullName | size | clear | exit): ");
            String[] params = reader.readLine().trim().toLowerCase().split(" ");
            if (params.length < 1 || params.length > 3) {
                System.out.println("Неверная команда.");
                continue;
            }
            String param = null;
            if (params.length == 3) {
                param = params[1].intern();
            }
            switch (params[0]) {
                case "list":
                    printAll();
                    break;
                case "size":
                    System.out.println(STORAGE.size());
                    break;
                case "save":
                    r = new Resume(param);
                    STORAGE.save(r);
                    printAll();
                    break;
                case "delete":
                    STORAGE.delete(param);
                    printAll();
                    break;
                case "get":
                    System.out.println(STORAGE.get(param));
                    break;
                case "update":
                    r = new Resume(params[2], param);
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