package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.ExistStorageException;
import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;

import java.util.Collections;
import java.util.List;

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
        saveResume(key, resume);
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

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = getAll();
        Collections.sort(list);
        return list;
    }

    protected abstract List<Resume> getAll();

    protected abstract void deleteResume(Object key);

    protected abstract Object getKey(String uuid);

    protected abstract Resume getResume(Object key);

    protected abstract void updateResume(Object key, Resume resume);

    protected abstract boolean inStorage(Object key);

    protected abstract void saveResume(Object key, Resume resume);

    protected abstract void clearStorage();

    protected abstract int getSize();

}
