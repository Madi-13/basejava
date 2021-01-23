package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.StorageOverflowException;
import ru.javaops.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    protected abstract int getIndex(String uuid);

    protected abstract void insertResume(Resume resume, int resumeIndex);

    protected abstract void deleteResume(int resumeIndex);

    @Override
    protected void deleteResume(Object key) {
        int index = (int) key;
        deleteResume(index);
        size--;
    }

    @Override
    protected Integer getKey(String uuid) {
        return getIndex(uuid);
    }

    @Override
    protected Resume getResume(Object key) {
        int index = (int) key;
        return storage[index];
    }

    @Override
    protected void updateResume(Object key, Resume resume) {
        int index = (int) key;
        storage[index] = resume;
    }

    @Override
    protected boolean inStorage(Object key) {
        return (int) key >= 0;
    }

    @Override
    protected void saveResume(Resume resume, Object key) {
        if (size == STORAGE_LIMIT) {
            throw new StorageOverflowException(resume.getUuid());
        }
        insertResume(resume, (int) key);
        size++;
    }

    @Override
    protected void clearStorage() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected int getSize() {
        return size;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }
}
