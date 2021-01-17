package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.ExistStorageException;
import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    protected abstract int getIndex(String uuid);

    protected abstract void insertResume(Resume resume, int resumeIndex);

    protected abstract void deleteResume(int resumeIndex);

    public Resume get(String uuid) {
        int indexUuid = getIndex(uuid);
        if (indexUuid < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            return storage[indexUuid];
        }
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0 || index >= size) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            storage[index] = resume;
        }
    }

    public void save(Resume resume) {
        if (resume == null) {
            System.out.println("Given empty resume to method save");
        } else {
            int resumeIndex = getIndex(resume.getUuid());

            if (size == STORAGE_LIMIT) {
                throw new StorageException("Storage overflow", resume.getUuid());
            } else if (resumeIndex >= 0) {
                throw new ExistStorageException(resume.getUuid());
            } else {
                insertResume(resume, resumeIndex);
                size++;
            }
        }
    }

    public void delete(String uuid) {
        int uuidIndex = getIndex(uuid);

        if (uuidIndex < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            deleteResume(uuidIndex);
            size--;
        }

    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return size;
    }

}
