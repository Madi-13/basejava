package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage implements Storage {
    private static final int storageLimit = 10000;
    private final Resume[] storage = new Resume[storageLimit];
    private int size = 0;

    private int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }

        return -1;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index == -1) {
            System.out.println("Given resume(" + resume.getUuid() + ") not in storage");
        } else {
            storage[index] = resume;
        }
    }

    public void save(Resume resume) {
        if (resume == null) {
            System.out.println("Given empty resume to method save");
        } else if (getIndex(resume.getUuid()) != -1) {
            System.out.println("Given resume(" + resume.getUuid() + ") is already in storage");
        } else if (size == storageLimit) {
            System.out.println("ru.javaops.webapp.model.Resume storage is full. Сan't save the resume");
        } else {
            storage[size] = resume;
            size++;
        }
    }

    public Resume get(String uuid) {
        int indexUuid = getIndex(uuid);
        if (indexUuid == -1) {
            System.out.println("Given resume(" + uuid + ") not in storage");
            return null;
        } else {
            return storage[indexUuid];
        }
    }

    public void delete(String uuid) {
        int uuidIndex = getIndex(uuid);
        if (uuidIndex == -1) {
            System.out.println("Can not delete a given resume(" + uuid + ") that is not in the storage");
        } else {
            storage[uuidIndex] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() { return size;}

}
