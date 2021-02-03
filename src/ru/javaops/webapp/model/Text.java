package ru.javaops.webapp.model;

import java.util.Objects;

public class Text extends SectionInfo {
    private static final long serialVersionUID = 1L; //serialisation version

    private final String text;

    public Text(String text) {
        Objects.requireNonNull(text, "Text must not be null");
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Text text1 = (Text) o;

        return text.equals(text1.text);
    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }

    @Override
    public String toString() {
        return "Text{" +
                "text='" + text + '\'' +
                '}';
    }
}
