package ru.javaops.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListOfOrganizations extends SectionInfo {
    private static final long serialVersionUID = 1L; //serialisation version

    private final List<Organization> organizations;

    public ListOfOrganizations(Organization... organizations) {
        this(Arrays.asList(organizations));
    }

    public ListOfOrganizations(List<Organization> organizations) {
        Objects.requireNonNull(organizations, "List of organizations must not be null");
        this.organizations = organizations;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListOfOrganizations that = (ListOfOrganizations) o;

        return organizations.equals(that.organizations);
    }

    @Override
    public int hashCode() {
        return organizations.hashCode();
    }

    @Override
    public String toString() {
        return "ListOfOrganizations{" +
                "organizations=" + organizations +
                '}';
    }
}
