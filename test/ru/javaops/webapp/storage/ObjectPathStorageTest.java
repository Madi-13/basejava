package ru.javaops.webapp.storage;

import ru.javaops.webapp.storage.serializer.ObjectStreamStorage;

public class ObjectPathStorageTest extends AbstractStorageTest {
    public ObjectPathStorageTest() {
        super(new PathStorage(STORAGE_DIRECTORY.getAbsolutePath(), new ObjectStreamStorage()));
    }
}
