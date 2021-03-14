package ru.javaops.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.*;

/**
 * Initial resume class
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD) // to work with fields without setters for XmlParser
public class Resume implements Comparable<Resume>, Serializable {
    private static final long serialVersionUID = 1L; // serialisation version

    private String uuid;
    private String fullName;

    private final Map<Contact, String> contacts = new EnumMap(Contact.class);
    private final Map<Section, SectionInfo> sections = new EnumMap(Section.class);

    public Resume() {
    }

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

    public Map<Contact, String> getContacts() {
        return contacts;
    }

    public Map<Section, SectionInfo> getSections() {
        return sections;
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
        if (!fullName.equals(resume.fullName)) return false;
        if (!contacts.equals(resume.contacts)) return false;
        return sections.equals(resume.sections);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        result = 31 * result + contacts.hashCode();
        result = 31 * result + sections.hashCode();
        return result;
    }


    @Override
    public String toString() {
        return "Resume{" +
                "uuid='" + uuid + '\'' +
                ", fullName='" + fullName + '\'' +
                ", contacts=" + contacts +
                ", sections=" + sections +
                '}';
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
