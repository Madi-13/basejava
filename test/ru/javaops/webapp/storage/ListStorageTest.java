package ru.javaops.webapp.storage;

import org.junit.Test;
import ru.javaops.webapp.model.Resume;

import java.util.List;

import static org.junit.Assert.*;

public class ListStorageTest extends AbstractStorageTest {

    public ListStorageTest() {
        super(new ListStorage());
    }

    @Test
    public void testGetAll() {
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