package ru.javaops.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListOfTexts extends SectionInfo {
    private static final long serialVersionUID = 1L; //serialisation version

    private final List<String> texts;

    public ListOfTexts(String... texts) {
        this(Arrays.asList(texts));
    }

    public ListOfTexts(List<String> texts) {
        Objects.requireNonNull(texts, "List of text must not be null");
        this.texts = texts;
    }

    public List<String> getTexts() {
        return texts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListOfTexts that = (ListOfTexts) o;

        return texts.equals(that.texts);
    }

    @Override
    public int hashCode() {
        return texts.hashCode();
    }

    @Override
    public String toString() {
        return "ListOfTexts{" +
                "texts=" + texts +
                '}';
    }
}
