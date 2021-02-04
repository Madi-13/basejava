package ru.javaops.webapp.storage;

import ru.javaops.webapp.storage.Serializer.ObjectStreamStorage;

public class FileStorageTest extends AbstractStorageTest {
    public FileStorageTest() {
        super(new FileStorage(STORAGE_DIRECTORY, new ObjectStreamStorage()));
    }


}
