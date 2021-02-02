package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.HashMap;

public class MapStorageByResume extends MapStorage<Resume> {
    public MapStorageByResume() {
        super(new HashMap<>());
    }

    @Override
    protected void deleteResume(Resume key) {
        storage.remove(key.getUuid());
    }

    @Override
    protected Resume getKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected Resume getResume(Resume key) {
        return key;
    }

    @Override
    protected boolean isExist(Resume key) {
        return key != null;
    }

    @Override
    protected void updateResume(Resume key, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void saveResume(Resume key, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

}

