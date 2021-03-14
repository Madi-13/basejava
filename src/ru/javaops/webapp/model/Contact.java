package ru.javaops.webapp.model;

public enum Contact {
    PHONE("Тел."),
    MOBILE("Мобильный"),
    HOME_PHONE("Домашний тел."),
    SKYPE("Skype") {
        @Override
        protected String toHtml0(String value) {
            return "<a href='skype:" + value + "'>" + value + "</a>";
        }
    },
    MAIL("Почта") {
        @Override
        protected String toHtml0(String value) {
            return "<a href='mailto:" + value + "'>" + value + "</a>";
        }
    },
    LINKEDIN("Профиль LinkedIn"),
    GITHUB("Профиль GitHub"),
    STACKOVERFLOW("Профиль Stackoverflow"),
    HOME_PAGE("Домашняя страница");

    private final String title;

    Contact(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    protected String toHtml0(String value) {
        return value == null? "-" : value;
    }

    public String toHtml(String value) {
        return title + ": " + toHtml0(value);
    }
}
