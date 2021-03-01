package ru.javaops.webapp.util;

import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.sql.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {


    @FunctionalInterface
    public interface PrepareAndExecute<T> {
        T prepareAndExecute(PreparedStatement ps) throws SQLException;
    }

    public <T> T execute(ConnectionFactory connectionFactory, String query, PrepareAndExecute<T> pr) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            return pr.prepareAndExecute(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
