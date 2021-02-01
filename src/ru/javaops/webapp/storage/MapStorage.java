package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class MapStorage<SearchKey> extends AbstractStorage<SearchKey> {
    protected final Map<String, Resume> storage;

    protected MapStorage(Map<String, Resume> storage) {
        this.storage = storage;
    }

    @Override
    protected void clearStorage() {
        storage.clear();
    }

    @Override
    protected int getSize() {
        return storage.size();
    }

    @Override
    public List<Resume> getAll() {
        return new ArrayList<>(storage.values());
    }

}
