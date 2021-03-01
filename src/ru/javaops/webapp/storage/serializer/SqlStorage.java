package ru.javaops.webapp.storage.serializer;

import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.sql.ConnectionFactory;
import ru.javaops.webapp.storage.Storage;
import ru.javaops.webapp.util.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword); // expensive operation
    }

    @Override
    public void clear() {
        new SqlHelper().execute(connectionFactory, "DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public void update(Resume resume) throws NotExistStorageException {
        new SqlHelper().execute(connectionFactory, "UPDATE resume SET full_name = ?  WHERE uuid = ?", ps -> {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }
            return null;
        });
    }

    @Override
    public void save(Resume resume) throws StorageException {
        new SqlHelper().execute(connectionFactory, "INSERT INTO resume(uuid, full_name) VALUES(?,?)", ps -> {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.execute();
            return null;
        });
    }

    @Override
    public Resume get(String uuid) throws NotExistStorageException {
        SqlHelper sqlHelper = new SqlHelper();
        return sqlHelper.execute(connectionFactory, "SELECT * FROM resume r WHERE r.uuid = ?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery(); // in psql that resource closes when prepare statement close
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume r =  new Resume(uuid, rs.getString("full_name"));
            return r;
        });
    }

    @Override
    public void delete(String uuid) throws NotExistStorageException {
        new SqlHelper().execute(connectionFactory, "DELETE FROM resume r WHERE r.uuid = ?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        SqlHelper sqlHelper = new SqlHelper();
        return sqlHelper.execute(connectionFactory, "SELECT * FROM resume r", ps -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            Collections.sort(list);
            return list;
        });
    }

    @Override
    public int size() {
        SqlHelper sqlHelper = new SqlHelper();
        return sqlHelper.execute(connectionFactory, "SELECT COUNT(*) FROM resume r", ps -> {
            ResultSet rs = ps.executeQuery(); // in psql that resource closes when prepare statement close
            return rs.next() ? rs.getInt(1) : 0;
        });
    }
}
