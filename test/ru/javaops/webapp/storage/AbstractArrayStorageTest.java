package ru.javaops.webapp.storage;

import org.junit.Test;
import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.exception.StorageOverflowException;
import ru.javaops.webapp.model.Resume;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    protected AbstractArrayStorageTest(AbstractArrayStorage storage) {
        super(storage);
    }

    @Test(expected = StorageOverflowException.class)
    public void saveStorageOverflow() throws StorageException {
        try {
            String name = "name";
            ResumeTestData resumeFiller = new ResumeTestData();
            for (int i = 3; i < AbstractArrayStorage.STORAGE_LIMIT; ++i) {
                Resume resume = new Resume(name + (i + 1), "dummy");
                resumeFiller.setResumeFull(resume);
                storage.save(resume);
            }
        } catch (StorageException exception) {
            exception.printStackTrace();
            fail("Storage overflow exception was threw ahead of time");
        }

        Resume overflowResume = new Resume("nameOverflow", "dummy");
        new ResumeTestData().setResumeFull(overflowResume);
        storage.save(overflowResume);
    }
}