package ru.javaops.webapp.storage.serializer;

import ru.javaops.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DataStreamSerializer implements StreamSerializer {

    @FunctionalInterface
    private interface SectionWriter<T> {
        void write(T t) throws IOException;

    }

    @FunctionalInterface
    private interface SectionReader<T> {
        T read() throws IOException;

    }

    private <T> void writeList(DataOutputStream dos, Collection<T> list, SectionWriter<T> sectionWriter) throws IOException { // pattern strategy
        dos.writeInt(list.size());
        for (T info : list) {
            sectionWriter.write(info);
        }
    }

    private <T> List<T> readList(DataInputStream dis, SectionReader<T> sectionReader) throws IOException { // pattern strategy
        int size = dis.readInt();
        List<T> sections = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            sections.add(sectionReader.read());
        }
        return sections;
    }

    private void writeSection(DataOutputStream dos, Section key, SectionInfo value) throws IOException {
        dos.writeUTF(key.name());
        switch (key) {
            case OBJECTIVE:
            case PERSONAL:
                dos.writeUTF(((Text) value).getText());
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                writeList(dos, ((ListOfTexts) value).getTexts(), dos::writeUTF);
                break;
            case EXPERIENCE:
            case EDUCATION:
                ListOfOrganizations organizations = (ListOfOrganizations) value;
                writeList(dos, organizations.getOrganizations(), organization -> {
                    dos.writeUTF(organization.getLink().getTitle());
                    dos.writeUTF(organization.getLink().getUrl());
                    writeList(dos, organization.getPositions(), position -> {
                        dos.writeUTF(position.getStartDate().toString());
                        dos.writeUTF(position.getEndDate().toString());
                        dos.writeUTF(position.getTitle());
                        dos.writeUTF(position.getInfo());
                    });
                });
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }

    private SectionInfo readSection(DataInputStream dis, Section section) throws IOException {
        SectionInfo sectionInfo;
        switch (section) {
            case OBJECTIVE:
            case PERSONAL:
                sectionInfo = new Text(dis.readUTF());
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                sectionInfo = new ListOfTexts(readList(dis, dis::readUTF));
                break;
            case EXPERIENCE:
            case EDUCATION:
                sectionInfo = new ListOfOrganizations(readList(dis, () ->
                        new Organization(dis.readUTF(), dis.readUTF(), readList(dis, () ->
                                new Organization.Position(LocalDate.parse(dis.readUTF()),
                                        LocalDate.parse(dis.readUTF()), dis.readUTF(), dis.readUTF())))));
                break;
            default:
                throw new UnsupportedOperationException();
        }
        return sectionInfo;
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
