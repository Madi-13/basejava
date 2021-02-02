package ru.javaops.webapp.storage;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public abstract class MapStorageTest extends AbstractStorageTest {

    protected MapStorageTest(MapStorage storage) {
        super(storage);
    }

    @Test
    public void inStorage() {
        assertTrue(storage.isExist(storage.getKey(UUID_1)));
        assertFalse(storage.isExist(storage.getKey(UUID_4)));
    }

}