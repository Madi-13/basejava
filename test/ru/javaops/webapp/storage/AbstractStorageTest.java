package ru.javaops.webapp.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javaops.webapp.exception.ExistStorageException;
import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.util.Config;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIRECTORY = Config.get().getStorageDir();
    protected AbstractStorage storage;

    protected static final String UUID_1 = "uuid1";
    protected static final Resume RESUME_1 = new Resume(UUID_1, "NAME_1");

    protected static final String UUID_2 = "uuid2";
    protected static final Resume RESUME_2 = new Resume(UUID_2, "NAME_2");

    protected static final String UUID_3 = "uuid3";
    protected static final Resume RESUME_3 = new Resume(UUID_3, "NAME_3");

    protected static final String UUID_4 = "uuid4";
    protected static final Resume RESUME_4 = new Resume(UUID_4, "NAME_4");

    protected static final ResumeTestData RESUME_TEST_DATA = new ResumeTestData();

    static {
        RESUME_TEST_DATA.setResumeFull(RESUME_1);
        RESUME_TEST_DATA.setResumeWithContacts(RESUME_2);
        RESUME_TEST_DATA.setResumeWithSections(RESUME_3);
        RESUME_TEST_DATA.setResumeFull(RESUME_4);
    }

    protected AbstractStorageTest(AbstractStorage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws StorageException {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void testGet() throws NotExistStorageException {
        assertEquals(RESUME_1, storage.get(UUID_1));
        assertEquals(RESUME_2, storage.get(UUID_2));
        assertEquals(RESUME_3, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws NotExistStorageException {
        storage.get(UUID_4);
    }

    @Test
    public void testUpdate() throws NotExistStorageException {
        Resume oldResume = storage.get(UUID_2);
        Resume newResume = new Resume(UUID_2, "NEW_NAME");
        RESUME_TEST_DATA.setResumeWithSections(newResume);

        storage.update(newResume);

        assertNotEquals(storage.get(UUID_2).getFullName(), oldResume.getFullName());
        assertEquals(storage.get(UUID_2).getFullName(), newResume.getFullName());

    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws NotExistStorageException {
        storage.update(RESUME_4);
    }

    @Test
    public void testSave() throws ExistStorageException {
        storage.save(RESUME_4);
        assertEquals(4, storage.size());
        assertEquals(RESUME_4, storage.get(UUID_4));
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
    public void getAllSorted() {
        List allResumes = storage.getAllSorted();
        assertEquals(3, allResumes.size());

        assertEquals(allResumes.get(0), RESUME_1);
        assertEquals(allResumes.get(1), RESUME_2);
        assertEquals(allResumes.get(2), RESUME_3);
    }

    @Test
    public void testSize() {
        Assert.assertEquals(3, storage.size());
    }

}