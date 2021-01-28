package ru.javaops.webapp.storage;

public class MapStorageByUuidTest extends MapStorageTest<String> {
    public MapStorageByUuidTest() {
        super(new MapStorageByUuid());
    }
}
