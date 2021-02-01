package ru.javaops.webapp.model;

public enum Section {
    OBJECTIVE("Позиция"),
    PERSONAL("Личные качества"),
    ACHIEVEMENT("Достижения"),
    QUALIFICATIONS("Квалификация"),
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    private final String title;
    Section(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
