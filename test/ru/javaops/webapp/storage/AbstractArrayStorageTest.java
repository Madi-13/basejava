package ru.javaops.webapp.storage;

import org.junit.*;
import ru.javaops.webapp.exception.ExistStorageException;
import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;

public abstract class AbstractArrayStorageTest {
    private Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static int count = 0;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }


    @BeforeClass
    public static void start() {
        System.out.println("Start testing");
    }

    @Before
    public void init() {
        count++;
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @After
    public void tearDown() {
        storage.clear();
    }

    @AfterClass
    public static void end() {
        System.out.println("Unit test amount is " + count);
    }

    @Test(expected = NotExistStorageException.class)
    public void get() {
        Assert.assertEquals("uuid1", storage.get("uuid1").toString());
        storage.get("uuid4"); // Check to NotExistStorageException exception
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void update() {
        Resume oldResume = storage.get("uuid2");
        Resume newResume = new Resume("uuid2");

        storage.update(newResume);
        Assert.assertTrue(storage.get("uuid2") != oldResume);
        Assert.assertEquals(storage.get("uuid2"), newResume);

        storage.update(new Resume("uuid4")); // Check to NotExistStorageException exception
    }

    @Test()
    public void save() {
        Resume resume = new Resume("uuid4");
        storage.save(resume);
        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(storage.get("uuid4"), resume);

        boolean caughtExistException = false;
        try {
            storage.save(new Resume("uuid1")); // Check to ExistStorageException exception
        } catch (ExistStorageException exception) {
            caughtExistException = true;
        }

        Assert.assertTrue(caughtExistException);

        boolean caughtStorageException = false;
        try {
            String uuid = "uuid";
            int i = 5;

            while (true) {
                storage.save(new Resume(uuid + i));
            }
        } catch (StorageException exception) {
            caughtStorageException = true;
        }

        Assert.assertTrue(caughtStorageException);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete("uuid1");
        Assert.assertTrue(2 == storage.size());

        boolean caughtNotExistException = false;
        try {
            storage.get("uuid1");
        } catch (NotExistStorageException exception) {
            caughtNotExistException = true;
        }
        Assert.assertTrue(caughtNotExistException);

        storage.delete("uuid4");
    }

    @Test
    public void getAll() {
        Assert.assertEquals(3, storage.getAll().length);
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }
}