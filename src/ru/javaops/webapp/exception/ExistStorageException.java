package ru.javaops.webapp.exception;

public class ExistStorageException extends StorageException{
    public ExistStorageException(String uuid) {
        super("Given resume(" + uuid + ") is already in the storage", uuid);
    }
}
