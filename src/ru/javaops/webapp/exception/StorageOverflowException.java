package ru.javaops.webapp.exception;

public class StorageOverflowException extends StorageException {
    public StorageOverflowException(String uuid) {
        super("Storage Overflow", uuid);
    }
}
