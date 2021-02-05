package ru.javaops.webapp.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.javaops.webapp.model.SectionInfo;

import java.io.Reader;
import java.io.Writer;

public class JsonParser {
    private static Gson GSON = new GsonBuilder().
            registerTypeAdapter(SectionInfo.class, new JsonSectionInfoAdapter()).
            setPrettyPrinting().create();

    public static <T> T read(Reader reader, Class<T> tClass) {
        return GSON.fromJson(reader, tClass);
    }

    public static <T> void write(Writer writer, T object) {
        GSON.toJson(object, writer);
    }
}
