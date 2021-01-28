package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }

        return -1;
    }


    @Override
    protected void insertResume(Resume resume, int resumeIndex) {
        storage[size] = resume;
    }

    @Override
    protected void deleteResume(int resumeIndex) {
        storage[resumeIndex] = storage[size - 1];
        storage[size - 1] = null;
    }

    @Override
    public List<Resume> getAll() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }
}
