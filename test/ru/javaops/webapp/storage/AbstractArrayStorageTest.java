package ru.javaops.webapp.storage;

import org.junit.Test;
import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.exception.StorageOverflowException;

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
                storage.save(new ResumeTestData().getFullResume(name + (i + 1), "dummy"));
            }
        } catch (StorageException exception) {
            exception.printStackTrace();
            fail("Storage overflow exception was threw ahead of time");
        }

        storage.save(new ResumeTestData().getFullResume("nameOverflow", "dummy"));
    }

    @Test
    public void inStorage() {
        assertTrue(storage.isExist(storage.getKey(UUID_1)));
        assertFalse(storage.isExist(storage.getKey(UUID_4)));
    }
}