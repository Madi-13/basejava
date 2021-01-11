package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int size = 0;

    private int indexInStorage(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(String uuidFrom, String uuidTo) {
        if (indexInStorage(uuidFrom) == -1) {
            System.out.println("Given resume(" + uuidTo + ") not in storage");
        } else {
            for (int i = 0; i < size; i++) {
                if (storage[i].toString().equals(uuidFrom)) {
                    storage[i].uuid = uuidTo;
                }
            }
        }
    }

    public void save(Resume resume) {
        if (resume == null) {
            System.out.println("Given empty resume to method save");
        } else if (indexInStorage(resume.toString()) != -1) {
            System.out.println("Given resume(" + resume.toString() + ") is already in storage");
        } else if (size == storage.length) {
            System.out.println("ru.javaops.webapp.model.Resume storage is full. Сan't save the resume");
        } else {
            storage[size] = resume;
            size++;
        }
    }

    public Resume get(String uuid) {
        int indexUuid = indexInStorage(uuid);
        if (indexUuid == -1) {
            System.out.println("Given resume(" + uuid + ") not in storage");
            return null;
        } else {
            return storage[indexUuid];
        }
    }

    public void delete(String uuid) {
        if (indexInStorage(uuid) == -1) {
            System.out.println("Can not delete a given resume(" + uuid + ") that is not in the storage");
        }
        for (int i = 0; i < size; i++) {
            if (storage[i].toString().equals(uuid)) {
                storage[i] = storage[size - 1];
                storage[size - 1] = null;
                size--;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] resumes = new Resume[size];
        System.arraycopy(storage, 0, resumes, 0, size);
        return resumes;
    }

    public int size() {
        return size;
    }

}
