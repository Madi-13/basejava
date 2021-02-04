package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.storage.serializer.StreamSerializer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;
    private final StreamSerializer streamSerializer;

    public FileStorage(File directory, StreamSerializer streamSerializer) {
        Objects.requireNonNull(directory, "Directory must not be null");
        Objects.requireNonNull(streamSerializer, "StreamSerializer must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not directory");
        } else if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not readable or writeable");
        } else {
            this.directory = directory;
            this.streamSerializer = streamSerializer;
        }
    }

    @Override
    protected List<Resume> getAll() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Couldn't read directory" + directory.getName());
        }
        List<Resume> resumes = new ArrayList<>(files.length);
        for (File file: files) {
            resumes.add(getResume(file));
        }
        return resumes;
    }

    @Override
    protected void deleteResume(File file) {
        if (!file.delete()) {
            throw new  StorageException("Couldn't delete file" + file.getAbsolutePath() , file.getName());
        }
    }

    @Override
    protected File getKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected Resume getResume(File file) {
        try {
            return streamSerializer.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Couldn't read file" + file.getAbsolutePath(), file.getName(), e);
        }
    }

    @Override
    protected void updateResume(File file, Resume resume) {
        try {
            streamSerializer.doWrite(new BufferedOutputStream(new FileOutputStream(file)), resume);
        } catch (IOException e) {
            throw new StorageException("File write error", file.getName(), e);
        }
    }

    @Override
    protected boolean isExist(File file) {
        return file.isFile();
    }

    @Override
    protected void saveResume(File file, Resume resume) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + file.getAbsolutePath(), file.getName(), e);
        }
        updateResume(file, resume);
    }

    @Override
    protected void clearStorage() {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file: files) {
                deleteResume(file);
            }
        }
    }

    @Override
    protected int getSize() {
        String[] files = directory.list();
        if (files == null) {
            throw new StorageException("Couldn't read directory" + directory.getName());
        }
        return files.length;
    }
}
