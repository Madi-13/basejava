package ru.javaops.webapp.sql;

import org.postgresql.util.PSQLException;
import ru.javaops.webapp.exception.ExistStorageException;
import ru.javaops.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    @FunctionalInterface
    public interface Executor<T> {
        T prepareAndExecute(PreparedStatement ps) throws SQLException;
    }

    private static class SqlExceptionUtil {
        public static StorageException convertException(SQLException e) {
            if (e instanceof PSQLException) {
                if (e.getSQLState().equals("23505")) {
                    return new ExistStorageException(e);
                }
            }
            return new StorageException(e);
        }
    }

    public <T> T execute(ConnectionFactory connectionFactory, String query, Executor<T> executor) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            return executor.prepareAndExecute(ps);
        } catch (SQLException e) {
            throw SqlExceptionUtil.convertException(e);
        }
    }

    public <T> T transactionExecute(ConnectionFactory connectionFactory, SqlTransaction<T> executor) {
        try (Connection conn = connectionFactory.getConnection()) {
            try {
                conn.setAutoCommit(false);
                T result = executor.execute(conn);
                conn.commit();
                return result;
            } catch (SQLException e) {
                conn.rollback();
                throw SqlExceptionUtil.convertException(e);
            }
        } catch (SQLException e) {
            throw SqlExceptionUtil.convertException(e);
        }
    }
}
