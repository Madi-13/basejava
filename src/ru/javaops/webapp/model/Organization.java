package ru.javaops.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Organization extends SectionInfo {
    private Link link;
    private LocalDate startDate;
    private LocalDate endDate;
    private String info;

    public Organization(Link link, LocalDate startDate, LocalDate endDate, String info) {
        Objects.requireNonNull(startDate, "Start date must not be null");
        Objects.requireNonNull(endDate, "End date must not be null");
        this.link = link;
        this.startDate = startDate;
        this.endDate = endDate;
        this.info = info;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String  info) {
        this.info = info;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!link.equals(that.link)) return false;
        if (!startDate.equals(that.startDate)) return false;
        if (!endDate.equals(that.endDate)) return false;
        return Objects.equals(info, that.info);
    }

    @Override
    public int hashCode() {
        int result = link.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + (info != null ? info.hashCode() : 0);
        return result;
    }

}
