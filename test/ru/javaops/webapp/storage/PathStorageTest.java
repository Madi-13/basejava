package ru.javaops.webapp.storage;

import ru.javaops.webapp.storage.Serializer.ObjectStreamStorage;

public class PathStorageTest extends AbstractStorageTest {
    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIRECTORY.getAbsolutePath(), new ObjectStreamStorage()));
    }
}
