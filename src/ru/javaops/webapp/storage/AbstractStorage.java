package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.ExistStorageException;
import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public Resume get(String uuid) throws NotExistStorageException {
        Object key = getExistedKey(uuid);
        return getResume(key);
    }

    @Override
    public void update(Resume resume) throws NotExistStorageException {
        Object key = getExistedKey(resume.getUuid());
        updateResume(key, resume);
    }

    @Override
    public void save(Resume resume) throws StorageException {
        Object key = getNotExistedKey(resume.getUuid());
        saveResume(resume, key);
    }

    @Override
    public void delete(String uuid) throws NotExistStorageException {
        Object key = getExistedKey(uuid);
        deleteResume(key);
    }

    @Override
    public void clear() {
        clearStorage();
    }

    @Override
    public int size() {
        return getSize();
    }

    private Object getNotExistedKey(String uuid) {
        Object key = getKey(uuid);
        if (inStorage(key)) {
            throw new ExistStorageException(uuid);
        }
        return key;
    }


    private Object getExistedKey(String uuid) {
        Object key = getKey(uuid);
        if (!inStorage(key)) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    protected abstract void deleteResume(Object key);

    protected abstract Object getKey(String uuid) throws NotExistStorageException;

    protected abstract Resume getResume(Object key);

    protected abstract void updateResume(Object key, Resume resume);

    protected abstract boolean inStorage(Object key);

    protected abstract void saveResume(Resume resume, Object key);

    protected abstract void clearStorage();

    protected abstract int getSize();

}
