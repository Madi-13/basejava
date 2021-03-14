package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.*;
import ru.javaops.webapp.sql.ConnectionFactory;
import ru.javaops.webapp.sql.SqlHelper;
import ru.javaops.webapp.util.JsonParser;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    private final ConnectionFactory connectionFactory;
    private final SqlHelper sqlHelper = new SqlHelper();

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException(e);
            }
            return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        }; // expensive operation
    }

    private void deleteContacts(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid = ?")) {
            ps.setString(1, resume.getUuid());
            ps.execute();
        }
    }

    private void insertContacts(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES(?,?,?)")) {
            for (Map.Entry<Contact, String> contact : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, contact.getKey().toString());
                ps.setString(3, contact.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteSections(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM section WHERE resume_uuid = ?")) {
            ps.setString(1, resume.getUuid());
            ps.execute();
        }
    }

    private void insertSections(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, info) VALUES(?,?,?)")) {
            for (Map.Entry<Section, SectionInfo> contact : resume.getSections().entrySet()) {
                ps.setString(1, resume.getUuid());
                Section section = contact.getKey();
                ps.setString(2, section.toString());
                SectionInfo value = contact.getValue();
                switch (section) {
                    case OBJECTIVE:
                    case PERSONAL:
                        if (value instanceof Text) {
                            ps.setString(3, JsonParser.write((Text) value));
                        } else {
                            throw new IllegalStateException("Unexpected value: " + value + " on section OBJECTIVE/PERSONAL");
                        }
                        break;
                    case QUALIFICATIONS:
                    case ACHIEVEMENT:
                        if (value instanceof ListOfTexts) {
                            ps.setString(3, JsonParser.write((ListOfTexts) value));
                        } else {
                            throw new IllegalStateException("Unexpected value: " + value + " on section QUALIFICATIONS/ACHIEVEMENT");
                        }
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        if (value instanceof ListOfOrganizations) {
                            ps.setString(3, JsonParser.write((ListOfOrganizations) value));
                        } else {
                            throw new IllegalStateException("Unexpected value: " + value + " on section QUALIFICATIONS/ACHIEVEMENT");
                        }
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + section);
                }
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    @FunctionalInterface
    private interface Adder {
        void addInfo(Resume resume, ResultSet rs) throws SQLException;
    }

    private SectionInfo readSectionInfo(ResultSet rs, Section section) throws SQLException {
        SectionInfo sectionInfo;
        switch (section) {
            case OBJECTIVE:
            case PERSONAL:
                sectionInfo = JsonParser.read(rs.getString("info"), Text.class);
                break;
            case QUALIFICATIONS:
            case ACHIEVEMENT:
                sectionInfo = JsonParser.read(rs.getString("info"), ListOfTexts.class);
                break;
            case EDUCATION:
            case EXPERIENCE:
                sectionInfo = JsonParser.read(rs.getString("info"), ListOfOrganizations.class);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + section);
        }
        return sectionInfo;
    }

    private void readAttributes(Connection conn, String query, Map<String, Resume> map, Adder adder) throws SQLException {
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            ResultSet rs = preparedStatement.executeQuery();
            boolean haveNext = rs.next();
            while (haveNext) {
                String current_uuid = rs.getString("resume_uuid");
                String uuid = current_uuid;
                Resume current_resume = map.get(uuid);
                while (current_uuid.equals(uuid)) {
                    if (!rs.wasNull()) {
                        adder.addInfo(current_resume, rs);
                    }
                    if (!rs.next()) {
                        haveNext = false;
                        break;
                    }
                    current_uuid = rs.getString("resume_uuid");
                }
            }
        }
    }

    @Override
    public void clear() {
        sqlHelper.execute(connectionFactory, "DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public void update(Resume resume) throws NotExistStorageException {
        sqlHelper.transactionExecute(connectionFactory, conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ?  WHERE uuid = ?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }
            deleteContacts(conn, resume);
            insertContacts(conn, resume);
            deleteSections(conn, resume);
            insertSections(conn, resume);
            return null;
        });
    }

    @Override
    public void save(Resume resume) throws StorageException {
        sqlHelper.transactionExecute(connectionFactory, conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume(uuid, full_name) VALUES(?,?)")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }
            insertContacts(conn, resume);
            insertSections(conn, resume);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) throws NotExistStorageException {
        return sqlHelper.transactionExecute(connectionFactory, conn -> {
            Resume resume;
            try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM resume WHERE uuid = ?")) {
                preparedStatement.setString(1, uuid);
                ResultSet rs = preparedStatement.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, rs.getString("full_name"));
            }

            try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM contact WHERE resume_uuid = ?")) {
                preparedStatement.setString(1, uuid);
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    resume.addContact(Contact.valueOf(rs.getString("type")), rs.getString("value"));
                }
            }

            try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM section WHERE resume_uuid = ?")){
                preparedStatement.setString(1, uuid);
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    Section section = Section.valueOf(rs.getString("type"));
                    resume.addSection(section, readSectionInfo(rs, section));
                }
            }
            return resume;
        });
    }

    @Override
    public void delete(String uuid) throws NotExistStorageException {
        sqlHelper.execute(connectionFactory, "DELETE FROM resume r WHERE r.uuid = ?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionExecute(connectionFactory, conn -> {
            Map<String, Resume> map = new LinkedHashMap<>();
            try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid")) {
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    map.put(rs.getString("uuid"), new Resume(rs.getString("uuid"), rs.getString("full_name")));
                }
            }
            readAttributes(conn,"SELECT * FROM contact", map, (resume, rs) -> {
                resume.addContact(Contact.valueOf(rs.getString("type")), rs.getString("value"));
            });
            readAttributes(conn, "SELECT * FROM section", map, ((resume, rs) -> {
                Section section = Section.valueOf(rs.getString("type"));
                resume.addSection(section, readSectionInfo(rs, section));
            }));
            return new ArrayList<>(map.values());
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute(connectionFactory, "SELECT COUNT(*) FROM resume r", ps -> {
            ResultSet rs = ps.executeQuery(); // in psql that resource closes when prepare statement close
            return rs.next() ? rs.getInt(1) : 0;
        });
    }
}
