package ru.javaops.webapp.storage;

import org.junit.Test;
import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.exception.StorageOverflowException;
import ru.javaops.webapp.model.Resume;

import java.util.List;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    protected AbstractArrayStorageTest(AbstractArrayStorage storage) {
        super(storage);
    }

    @Test(expected = StorageOverflowException.class)
    public void saveStorageOverflow() throws StorageException {
        try {
            String name = "name";
            for (int i = 3; i < AbstractArrayStorage.STORAGE_LIMIT; ++i) {
                storage.save(new Resume(name + (i + 1)));
            }
        } catch (StorageException exception) {
            exception.printStackTrace();
            fail("Storage overflow exception was threw ahead of time");
        }

        storage.save(new Resume("nameOverflow"));
    }

    @Test
    public void getAll() {
        List<Resume> allResumes = storage.getAllSorted();
        assertEquals(3, allResumes.size());

        assertEquals(allResumes.get(0), RESUME_1);
        assertEquals(allResumes.get(1), RESUME_2);
        assertEquals(allResumes.get(2), RESUME_3);
    }

    @Test
    public void inStorage() {
        assertTrue(storage.inStorage(storage.getKey(UUID_1)));
        assertFalse(storage.inStorage(storage.getKey(UUID_4)));
    }
}