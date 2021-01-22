package ru.javaops.webapp.storage;

import org.junit.Test;
import ru.javaops.webapp.exception.NotExistStorageException;

import static org.junit.Assert.*;

public class MapStorageTest extends AbstractStorageTest {

    public MapStorageTest() {
        super(new MapStorage());
    }

    @Test(expected = NotExistStorageException.class)
    public void getKey() {
        assertEquals(UUID_1, storage.getKey(UUID_1));
        storage.getKey(UUID_4);
    }

    @Test
    public void inStorage() {
        assertTrue(storage.inStorage(RESUME_1));
        assertFalse(storage.inStorage(RESUME_4));
    }

}