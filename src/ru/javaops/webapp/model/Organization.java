package ru.javaops.webapp.model;

import ru.javaops.webapp.util.DateUtil;
import ru.javaops.webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Organization extends SectionInfo {
    private static final long serialVersionUID = 1L; //serialisation version

    private Link link;
    private List<Position> positions;

    public Organization() {

    }

    public Organization(String name, String url) {
        this(name, url, new ArrayList<>());
    }

    public Organization(String name, String url, Position... positions) {
        this(new Link(name, url), Arrays.asList(positions));
    }

    public Organization(String name, String url, List<Position> positions) {
        this(new Link(name, url), positions);
    }

    public Organization(Link link, Position... positions) {
        this(link, Arrays.asList(positions));
    }

    public Organization(Link link, List<Position> positions) {
        Objects.requireNonNull(link, "Link must not be null");
        this.link = link;
        this.positions = positions;
    }

    public Link getLink() {
        return link;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void addPosition(Position pos) {
        positions.add(pos);
    }

    public void delPosition(Position pos) {
        positions.remove(pos);
    }

    @XmlAccessorType(XmlAccessType.FIELD) // to work with fields without setters for XmlParser
    public static class Position implements Serializable {
        private static final long serialVersionUID = 1L; //serialisation version

        @XmlJavaTypeAdapter(value = LocalDateAdapter.class) // to serialize localDate for XmlParser
        private LocalDate startDate;
        @XmlJavaTypeAdapter(value = LocalDateAdapter.class) // to serialize localDate for XmlParser
        private LocalDate endDate;
        private String title;
        private String info;

        public Position() {
        }

        public Position(int startYear, Month startMonth, String title, String info) {
            this(DateUtil.of(startYear, startMonth), DateUtil.NOW, title, info);
        }

        public Position(int startYear, Month startMonth, int endYear, Month endMonth, String title, String info) {
            this(DateUtil.of(startYear, startMonth), DateUtil.of(endYear, endMonth), title, info);
        }

        public Position(LocalDate startDate, LocalDate endDate, String title, String info) {
            Objects.requireNonNull(startDate, "Start date must not be null");
            Objects.requireNonNull(endDate, "End date must not be null");
            Objects.requireNonNull(title, "name of position must not be null");
            this.startDate = startDate;
            this.endDate = endDate;
            this.title = title;
            this.info = info != null ? info : "";
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public String getTitle() {
            return title;
        }

        public String getInfo() {
            return info;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Position position = (Position) o;

            if (!startDate.equals(position.startDate)) return false;
            if (!endDate.equals(position.endDate)) return false;
            if (!title.equals(position.title)) return false;
            return Objects.equals(info, position.info);
        }

        @Override
        public int hashCode() {
            int result = startDate.hashCode();
            result = 31 * result + endDate.hashCode();
            result = 31 * result + title.hashCode();
            result = 31 * result + (info != null ? info.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Position{" +
                    "startDate=" + startDate +
                    ", endDate=" + endDate +
                    ", title='" + title + '\'' +
                    ", info='" + info + '\'' +
                    '}';
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!link.equals(that.link)) return false;
        return positions.equals(that.positions);
    }

    @Override
    public int hashCode() {
        int result = link.hashCode();
        result = 31 * result + positions.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "link=" + link +
                ", positions=" + positions +
                '}';
    }
}
