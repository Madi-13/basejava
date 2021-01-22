package ru.javaops.webapp.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javaops.webapp.exception.ExistStorageException;
import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;

import static org.junit.Assert.*;

public abstract class AbstractStorageTest {
    protected AbstractStorage storage;

    protected static final String UUID_1 = "uuid1";
    protected static final Resume RESUME_1 = new Resume(UUID_1);

    protected static final String UUID_2 = "uuid2";
    protected static final Resume RESUME_2 = new Resume(UUID_2);

    protected static final String UUID_3 = "uuid3";
    protected static final Resume RESUME_3 = new Resume(UUID_3);

    protected static final String UUID_4 = "uuid4";
    protected static final Resume RESUME_4 = new Resume(UUID_4);


    protected AbstractStorageTest(AbstractStorage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws StorageException {
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void testGet() throws NotExistStorageException {
        assertEquals(RESUME_1, storage.get(UUID_1));
        assertEquals(RESUME_1, storage.get(UUID_1));
        assertEquals(RESUME_1, storage.get(UUID_1));
        assertEquals(RESUME_1, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws NotExistStorageException {
        storage.get(UUID_4);
    }

    @Test
    public void testUpdate() throws NotExistStorageException {
        Resume oldResume = storage.get(UUID_2);
        Resume newResume = new Resume(UUID_2);

        storage.update(newResume);

        assertNotSame(storage.get(UUID_2), oldResume);
        assertSame(storage.get(UUID_2), newResume);

    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws NotExistStorageException {
        storage.update(RESUME_4);
    }

    @Test
    public void testSave() throws ExistStorageException {
        storage.save(RESUME_4);
        assertEquals(4, storage.size());
        assertSame(RESUME_4, storage.get(UUID_4));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws StorageException {
        storage.save(RESUME_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void testDelete() throws NotExistStorageException {
        storage.delete(UUID_1);
        assertEquals(2, storage.size());
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws NotExistStorageException {
        storage.delete(UUID_4);
    }

    @Test
    public void testClear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void testSize() {
        Assert.assertEquals(3, storage.size());
    }
}