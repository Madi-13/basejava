package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Contact;
import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.sql.ConnectionFactory;
import ru.javaops.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private final ConnectionFactory connectionFactory;
    private final SqlHelper sqlHelper = new SqlHelper();

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword); // expensive operation
    }

    private void deleteContacts(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid = ?")) {
            ps.setString(1, resume.getUuid());
            ps.execute();
        }
    }

    private void addContacts(Connection conn, Resume resume) throws SQLException {
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
            addContacts(conn, resume);
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
            addContacts(conn, resume);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) throws NotExistStorageException {
        return sqlHelper.execute(connectionFactory,
                            "SELECT * FROM resume r\n" +
                                    "LEFT JOIN contact c ON r.uuid = c.resume_uuid\n" +
                                    "WHERE r.uuid = ?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery(); // in psql that resource closes when prepare statement close
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume resume = new Resume(uuid, rs.getString("full_name"));
            do {
                String type = rs.getString("type");
                if (rs.wasNull()) {
                    break;
                }
                Contact contact = Contact.valueOf(type);
                String value = rs.getString("value");
                resume.addContact(contact, value);
            } while (rs.next());
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
        return sqlHelper.execute(connectionFactory,
                          "SELECT * FROM resume r\n" +
                                "LEFT JOIN contact c ON r.uuid = c.resume_uuid\n" +
                                "ORDER BY r.full_name, r.uuid", ps -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> list = new ArrayList<>();
            boolean haveNext = rs.next();
            while (haveNext) {
                String current_uuid = rs.getString("uuid");
                String uuid = current_uuid;
                Resume resume = new Resume(uuid, rs.getString("full_name"));
                while (current_uuid.equals(uuid)) {
                    String type = rs.getString("type");
                    if (!rs.wasNull()) {
                        resume.addContact(Contact.valueOf(type), rs.getString("value"));
                    }
                    if (!rs.next()) {
                        haveNext = false;
                        break;
                    }
                    current_uuid = rs.getString("uuid");
                }
                list.add(resume);
            }
            return list;
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
