package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int storageLimit = 10000;

    protected final Resume[] storage = new Resume[storageLimit];
    protected int size = 0;

    public int size() {
        return size;
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

    protected abstract int getIndex(String uuid);

}
