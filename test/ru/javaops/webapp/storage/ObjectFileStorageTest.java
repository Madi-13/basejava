package ru.javaops.webapp.storage;

import ru.javaops.webapp.storage.serializer.ObjectStreamStorage;

public class ObjectFileStorageTest extends AbstractStorageTest {
    public ObjectFileStorageTest() {
        super(new FileStorage(STORAGE_DIRECTORY, new ObjectStreamStorage()));
    }


}
