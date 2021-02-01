package ru.javaops.webapp.model;

import java.util.*;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume>{

    private final String uuid;
    private String fullName;

    private final Map<Contact, String> contacts = new EnumMap(Contact.class);
    private final Map<Section, SectionInfo> sections = new EnumMap(Section.class);

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getContact(Contact contact) {
        return contacts.get(contact);
    }

    public void addContact(Contact contact, String info) {
        Objects.requireNonNull(info, "Contact info must not be null");
        contacts.put(contact, info);
    }

    public SectionInfo getSection(Section section) {
        return sections.get(section);
    }

    public void addSection(Section section, SectionInfo sectionInfo) {
        sections.put(section, sectionInfo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (!uuid.equals(resume.uuid)) return false;
        return fullName.equals(resume.fullName);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return uuid + '(' + fullName + ')';
    }

    @Override
    public int compareTo(Resume resume) {
        int result = this.getFullName().compareTo(resume.getFullName());
        if (result == 0) {
            return this.getUuid().compareTo(resume.getUuid());
        } else {
            return result;
        }
    }
}
