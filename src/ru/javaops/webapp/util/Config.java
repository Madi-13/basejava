package ru.javaops.webapp.util;

import ru.javaops.webapp.storage.SqlStorage;
import ru.javaops.webapp.storage.Storage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final String PROPS = "/resumes.properties";
    private final Properties PROP = new Properties();
    private final File STORAGE_DIR;
    private static final Config INSTANCE = new Config();
    private final String DB_URL;
    private final String DB_USER;
    private final String DB_PASSWORD;
    private final Storage storage;

    public static Config get() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream is = Config.class.getResourceAsStream(PROPS)) {
            PROP.load(is);
            STORAGE_DIR = new File(PROP.getProperty("storage.dir"));
            DB_URL = PROP.getProperty("db.url");
            DB_USER = PROP.getProperty("db.user");
            DB_PASSWORD = PROP.getProperty("db.password");
            storage = new SqlStorage(DB_URL, DB_USER, DB_PASSWORD);
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file" + PROPS);
        }
    }

    public Storage getStorage() {
        return storage;
    }

    public File getStorageDir() {
        return STORAGE_DIR;
    }

    private static String getHomeDirectory() {
        String path = System.getProperty("homeDir");
        File file = new File(path != null? path : ".");
        if (!file.isDirectory()) {
            throw new IllegalStateException(file + "is not directory");
        }
        return path != null? path : ".";
    }

    public String getDbUrl() {
        return DB_URL;
    }

    public String getDbUser() {
        return DB_USER;
    }

    public String getDbPassword() {
        return DB_PASSWORD;
    }
}
