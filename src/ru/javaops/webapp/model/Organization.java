package ru.javaops.webapp.model;

import ru.javaops.webapp.util.DateUtil;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Organization extends SectionInfo {
    private final Link link;
    private final List<Position> positions = new ArrayList<>();

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
        this.positions.addAll(positions);
    }

    public Link getLink() {
        return link;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public static class Position {
        private final LocalDate startDate;
        private final LocalDate endDate;
        private final String info;

        public Position(int startYear, Month startMonth, String info) {
            this(DateUtil.of(startYear, startMonth), DateUtil.NOW, info);
        }

        public Position(int startYear, Month startMonth, int endYear, Month endMonth, String info) {
            this(DateUtil.of(startYear, startMonth), DateUtil.of(endYear, endMonth), info);
        }

        public Position(LocalDate startDate, LocalDate endDate, String info) {
            Objects.requireNonNull(startDate, "Start date must not be null");
            Objects.requireNonNull(endDate, "End date must not be null");
            this.startDate = startDate;
            this.endDate = endDate;
            this.info = info;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Position position = (Position) o;

            if (!startDate.equals(position.startDate)) return false;
            if (!endDate.equals(position.endDate)) return false;
            return Objects.equals(info, position.info);
        }

        @Override
        public int hashCode() {
            int result = startDate.hashCode();
            result = 31 * result + endDate.hashCode();
            result = 31 * result + (info != null ? info.hashCode() : 0);
            return result;
        }
    }

}
