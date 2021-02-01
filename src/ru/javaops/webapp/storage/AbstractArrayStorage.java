package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.StorageOverflowException;
import ru.javaops.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    protected abstract int getIndex(String uuid);

    protected abstract void insertResume(Resume resume, int resumeIndex);

    protected abstract void removeResume(int resumeIndex);

    @Override
    protected void deleteResume(Integer key) {
        removeResume(key);
        size--;
    }

    @Override
    protected Integer getKey(String uuid) {
        return getIndex(uuid);
    }

    @Override
    protected Resume getResume(Integer key) {
        return storage[key];
    }

    @Override
    protected void updateResume(Integer key, Resume resume) {
        storage[key] = resume;
    }

    @Override
    protected boolean inStorage(Integer key) {
        return key >= 0;
    }

    @Override
    protected void saveResume(Integer key, Resume resume) {
        if (size == STORAGE_LIMIT) {
            throw new StorageOverflowException(resume.getUuid());
        }
        insertResume(resume, key);
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

}
