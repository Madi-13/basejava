package ru.javaops.webapp.storage;

import org.junit.*;
import ru.javaops.webapp.exception.ExistStorageException;
import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.exception.StorageOverflowException;
import ru.javaops.webapp.model.Resume;

public abstract class AbstractArrayStorageTest {
    protected Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final Resume RESUME_1 = new Resume(UUID_1);

    private static final String UUID_2 = "uuid2";
    private static final Resume RESUME_2 = new Resume(UUID_2);

    private static final String UUID_3 = "uuid3";
    private static final Resume RESUME_3 = new Resume(UUID_3);

    private static final String UUID_4 = "uuid4";
    private static final Resume RESUME_4 = new Resume(UUID_4);

    private static int count = 0;


    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }


    @BeforeClass
    public static void start() {
        System.out.println("Start testing");
    }

    @Before
    public void setUp() throws StorageException {
        count++;
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @After
    public void setDown() {
        storage.clear();
    }

    @AfterClass
    public static void end() {
        System.out.println("Unit test amount is " + count);
    }

    @Test
    public void get() throws NotExistStorageException {
        Assert.assertEquals(RESUME_1, storage.get(UUID_1));
        Assert.assertEquals(RESUME_2, storage.get(UUID_2));
        Assert.assertEquals(RESUME_3, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws NotExistStorageException {
        storage.get(UUID_4);
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() throws NotExistStorageException {
        Resume oldResume = storage.get(UUID_2);
        Resume newResume = new Resume(UUID_2);

        storage.update(newResume);

        Assert.assertNotSame(storage.get(UUID_2), oldResume);
        Assert.assertSame(storage.get(UUID_2), newResume);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws NotExistStorageException {
        storage.update(RESUME_4);
    }

    @Test
    public void save() throws StorageException {
        storage.save(RESUME_4);
        Assert.assertEquals(4, storage.size());
        Assert.assertSame(RESUME_4, storage.get(UUID_4));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws StorageException {
        storage.save(RESUME_1);
    }

    @Test(expected = StorageOverflowException.class)
    public void saveStorageOverflow() throws StorageException {
        try {
            String uuid = "uuid";
            for (int i = 3; i < AbstractArrayStorage.STORAGE_LIMIT; ++i) {
                storage.save(new Resume(uuid + (i + 1)));
            }
        } catch (StorageException exception) {
            exception.printStackTrace();
            Assert.fail("Storage overflow exception was threw ahead of time");
        }

        storage.save(new Resume("uuid10000"));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws NotExistStorageException {
        storage.delete(UUID_1);
        Assert.assertEquals(2, storage.size());
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws NotExistStorageException {
        storage.delete(UUID_4);
    }

    @Test
    public void getAll() {
        Resume[] allResumes = storage.getAll();
        Assert.assertEquals(3, allResumes.length);

        Assert.assertEquals(allResumes[0], RESUME_1);
        Assert.assertEquals(allResumes[1], RESUME_2);
        Assert.assertEquals(allResumes[2], RESUME_3);
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }
}