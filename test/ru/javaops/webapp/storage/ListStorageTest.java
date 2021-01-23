package ru.javaops.webapp.storage;

import org.junit.Test;
import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.model.Resume;

import static org.junit.Assert.*;

public class ListStorageTest extends AbstractStorageTest {

    public ListStorageTest() {
        super(new ListStorage());
    }

    @Test
    public void testGetAll() {
        Resume[] allResumes = storage.getAll();
        assertEquals(3, allResumes.length);

        assertEquals(allResumes[0], RESUME_1);
        assertEquals(allResumes[1], RESUME_2);
        assertEquals(allResumes[2], RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getKey() {
        assertEquals(0, (int) storage.getKey(UUID_1));
        storage.getKey(UUID_4);
    }

    @Test
    public void inStorage() {
        assertTrue(storage.inStorage(RESUME_1));
        assertFalse(storage.inStorage(RESUME_4));
    }
}