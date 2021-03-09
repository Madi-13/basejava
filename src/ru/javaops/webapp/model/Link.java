package ru.javaops.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD) // to work with fields without setters for XmlParser
public class Link implements Serializable {
    private static final long serialVersionUID = 1L; //serialisation version

    private String title;
    private String url;

    public Link() {

    }

    public Link(String title, String url) {
        Objects.requireNonNull(title, "Title must not be null");
        this.title = title;
        this.url = url != null ? url : "";
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Link link = (Link) o;

        if (!title.equals(link.title)) return false;
        return Objects.equals(link.url, url);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + url.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Link{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
