package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.ExistStorageException;
import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SearchKey> implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    protected abstract List<Resume> getAll();

    protected abstract void deleteResume(SearchKey key);

    protected abstract SearchKey getKey(String uuid);

    protected abstract Resume getResume(SearchKey key);

    protected abstract void updateResume(SearchKey key, Resume resume);

    protected abstract boolean isExist(SearchKey key);

    protected abstract void saveResume(SearchKey key, Resume resume);

    protected abstract void clearStorage();

    protected abstract int getSize();

    @Override
    public Resume get(String uuid) throws NotExistStorageException {
        LOG.info("Get" + uuid);
        SearchKey key = getExistedKey(uuid);
        return getResume(key);
    }

    @Override
    public void update(Resume resume) throws NotExistStorageException {
        LOG.info("Update " + resume);
        SearchKey key = getExistedKey(resume.getUuid());
        updateResume(key, resume);
    }

    @Override
    public void save(Resume resume) throws StorageException {
        LOG.info("Save " + resume);
        SearchKey key = getNotExistedKey(resume.getUuid());
        saveResume(key, resume);
    }

    @Override
    public void delete(String uuid) throws NotExistStorageException {
        LOG.info("Delete " + uuid);
        SearchKey key = getExistedKey(uuid);
        deleteResume(key);
    }

    @Override
    public void clear() {
        LOG.info("Clear");
        clearStorage();
    }

    @Override
    public int size() {
        return getSize();
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("Get all sorted");
        List<Resume> list = getAll();
        Collections.sort(list);
        return list;
    }

    private SearchKey getNotExistedKey(String uuid) {
        SearchKey key = getKey(uuid);
        if (isExist(key)) {
            LOG.warning("Resume " + uuid + " is exist");
            throw new ExistStorageException(uuid);
        }
        return key;
    }


    private SearchKey getExistedKey(String uuid) {
        SearchKey key = getKey(uuid);
        if (!isExist(key)) {
            LOG.warning("Resume " + uuid + " isn't exist");
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

}
