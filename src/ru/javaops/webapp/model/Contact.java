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
    LINKEDIN("Профиль LinkedIn") {
        @Override
        protected String toHtml0(String value) {
            return "<a href='https://www.linkedin.com/in/" + value + "'>" + value + "</a>";
        }
    },
    GITHUB("Профиль GitHub") {
        @Override
        protected String toHtml0(String value) {
            return "<a href='https://github.com/" + value + "'>" + value + "</a>";
        }
    },
    STACKOVERFLOW("Профиль Stackoverflow") {
        @Override
        protected String toHtml0(String value) {
            return "<a href='https://stackoverflow.com/" + value + "'>" + value + "</a>";
        }
    },
    HOME_PAGE("Домашняя страница") {
        @Override
        protected String toHtml0(String value) {
            return "<a href='" + value + "'>" + value + "</a>";
        }
    };

    private final String title;

    Contact(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    protected String toHtml0(String value) {
        return value == null ? "-" : value;
    }

    public String toHtml(String value) {
        return title + ": " + toHtml0(value);
    }
}
