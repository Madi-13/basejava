package ru.javaops.webapp.storage;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public abstract class MapStorageTest<K> extends AbstractStorageTest {

    protected MapStorageTest(MapStorage<K> storage) {
        super(storage);
    }

    @Test
    public void inStorage() {
        assertTrue(storage.inStorage(storage.getKey(UUID_1)));
        assertFalse(storage.inStorage(storage.getKey(UUID_4)));
    }

}