package ru.javaops.webapp.model;

import java.util.Objects;

public class Text extends SectionInfo {
    private String text;

    public Text(String text) {
        Objects.requireNonNull(text, "Text must not be null");
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
}
