package ru.javaops.webapp.storage.serializer;

import ru.javaops.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface StreamSerializer {
    void doWrite(OutputStream os, Resume resume) throws IOException;

    Resume doRead(InputStream is) throws IOException;

}
