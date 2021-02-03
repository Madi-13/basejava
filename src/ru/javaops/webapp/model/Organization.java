package ru.javaops.webapp.model;

import ru.javaops.webapp.util.DateUtil;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Organization extends SectionInfo {
    private static final long serialVersionUID = 1L; //serialisation version

    private final Link link;
    private final List<Position> positions;

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
        Objects.requireNonNull(link, "Link ,ust not be null");
        this.link = link;
        this.positions = positions;
    }

    public Link getLink() {
        return link;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public static class Position implements Serializable {
        private static final long serialVersionUID = 1L; //serialisation version

        private final LocalDate startDate;
        private final LocalDate endDate;
        private final String title;
        private final String info;

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
            this.info = info;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Position position = (Position) o;

            if (!startDate.equals(position.startDate)) return false;
            if (!endDate.equals(position.endDate)) return false;
            if (!title.equals(position.title)) return false;
            return info != null ? info.equals(position.info) : position.info == null;
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
