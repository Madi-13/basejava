package ru.javaops.webapp.exception;

public class NotExistStorageException extends StorageException {
    public NotExistStorageException(String uuid) {
        super("Given resume(" + uuid + ") is not in the storage", uuid);
    }
}
