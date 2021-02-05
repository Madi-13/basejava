package ru.javaops.webapp.storage.serializer;

import ru.javaops.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    private SectionInfo readText(DataInputStream dis) throws IOException {
        return new Text(dis.readUTF());
    }

    private void writeText(DataOutputStream dos, Text value) throws IOException {
        dos.writeUTF(value.getText());
    }


    private Link readLink(DataInputStream dis) throws IOException {
        return new Link(dis.readUTF(), dis.readUTF());
    }

    private void writeLink(DataOutputStream dos, Link link) throws IOException {
        dos.writeUTF(link.getTitle());
        dos.writeUTF(link.getUrl());
    }

    private SectionInfo readListOfTexts(DataInputStream dis) throws IOException {
        List<String> texts = new ArrayList<>();
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            texts.add(dis.readUTF());
        }
        return new ListOfTexts(texts);
    }

    private void writeListOfTexts(DataOutputStream dos, ListOfTexts value) throws IOException {
        List<String> texts = value.getTexts();
        dos.writeInt(texts.size());
        for (String text : texts) {
            dos.writeUTF(text);
        }
    }

    private Organization readOrganization(DataInputStream dis) throws IOException {
        Link link = readLink(dis);
        List<Organization.Position> positions = new ArrayList<>();
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            positions.add(new Organization.Position(
                    LocalDate.parse(dis.readUTF()), LocalDate.parse(dis.readUTF()),
                    dis.readUTF(), dis.readUTF()));
        }
        return new Organization(link, positions);
    }

    private void writeOrganization(DataOutputStream dos, Organization organization) throws IOException {
        writeLink(dos, organization.getLink());
        List<Organization.Position> positions = organization.getPositions();
        dos.writeInt(positions.size());
        for (Organization.Position position : positions) {
            dos.writeUTF(position.getStartDate().toString());
            dos.writeUTF(position.getEndDate().toString());
            dos.writeUTF(position.getTitle());
            dos.writeUTF(position.getInfo());
        }
    }

    private SectionInfo readListOfOrganizations(DataInputStream dis) throws IOException {
        List<Organization> organizations = new ArrayList<>();
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            Organization organization = readOrganization(dis);
            organizations.add(organization);
        }
        return new ListOfOrganizations(organizations);
    }

    private void writeListOfOrganizations(DataOutputStream dos, ListOfOrganizations value) throws IOException {
        List<Organization> organizations = value.getOrganizations();
        dos.writeInt(organizations.size());
        for (Organization organization : organizations) {
            writeOrganization(dos, organization);
        }
    }

    private SectionInfo readSection(DataInputStream dis, Section section) throws IOException {
        SectionInfo sectionInfo = null;
        switch (section) {
            case OBJECTIVE:
            case PERSONAL:
                sectionInfo = readText(dis);
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                sectionInfo = readListOfTexts(dis);
                break;
            case EXPERIENCE:
            case EDUCATION:
                sectionInfo = readListOfOrganizations(dis);
                break;
        }
        return sectionInfo;
    }

    private void writeSection(DataOutputStream dos, Section key, SectionInfo value) throws IOException {
        dos.writeUTF(key.name());
        switch (key) {
            case OBJECTIVE:
            case PERSONAL:
                writeText(dos, (Text) value);
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                writeListOfTexts(dos, (ListOfTexts) value);
                break;
            case EXPERIENCE:
            case EDUCATION:
                writeListOfOrganizations(dos, (ListOfOrganizations) value);
                break;
        }
    }

    @Override
    public void doWrite(OutputStream os, Resume resume) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<Contact, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<Contact, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            Map<Section, SectionInfo> sections = resume.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<Section, SectionInfo> entry : sections.entrySet()) {
                writeSection(dos, entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            Resume resume = new Resume(dis.readUTF(), dis.readUTF());
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(Contact.valueOf(dis.readUTF()), dis.readUTF());
            }

            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                Section section = Section.valueOf(dis.readUTF());
                SectionInfo sectionInfo = readSection(dis, section);
                resume.addSection(section, sectionInfo);
            }
            return resume;
        }
    }
}
