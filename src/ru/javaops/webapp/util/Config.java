package ru.javaops.webapp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final File PROPS = new File("/home/sayan/topjava/basejava/config/resumes.properties");
    private final Properties PROP = new Properties();
    private final File STORAGE_DIR;
    private static final Config INSTANCE = new Config();
    private final String DB_URL;
    private final String DB_USER;
    private final String DB_PASSWORD;

    public static Config get() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream is = new FileInputStream(PROPS)) {
            PROP.load(is);
            STORAGE_DIR = new File(PROP.getProperty("storage.dir"));
            DB_URL = PROP.getProperty("db.url");
            DB_USER = PROP.getProperty("db.user");
            DB_PASSWORD = PROP.getProperty("db.password");
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file" + PROPS.getAbsolutePath());
        }
    }

    public File getStorageDir() {
        return STORAGE_DIR;
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
