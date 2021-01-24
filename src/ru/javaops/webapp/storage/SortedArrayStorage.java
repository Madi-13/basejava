package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {
    private static final Comparator<Resume> RESUME_COMPARATOR = (resume1, resume2) -> {
        return resume1.getUuid().compareTo(resume2.getUuid());
    };

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey, RESUME_COMPARATOR);
    }

    @Override
    protected void insertResume(Resume resume, int resumeIndex) {
        resumeIndex = -resumeIndex - 1;
        if (size - resumeIndex >= 0) {
            System.arraycopy(storage, resumeIndex, storage, resumeIndex + 1, size - resumeIndex);
            storage[resumeIndex] = resume;
        }
    }

    @Override
    protected void deleteResume(int resumeIndex) {
        if (size - 1 - resumeIndex >= 0) {
            System.arraycopy(storage, resumeIndex + 1, storage, resumeIndex, size - 1 - resumeIndex);
        }
    }
}
