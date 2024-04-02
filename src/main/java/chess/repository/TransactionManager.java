package chess.repository;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {

    private final DatabaseConnectionGenerator connectionGenerator;

    public TransactionManager(DatabaseConnectionGenerator connectionGenerator) {
        this.connectionGenerator = connectionGenerator;
    }

    @FunctionalInterface
    public interface TransactionalQuery {
        void run(Connection connection) throws SQLException;
    }

    public void executeTransaction(TransactionalQuery transaction) {
        Connection connection = this.connectionGenerator.getConnection();
        try {
            connection.setAutoCommit(false);
            transaction.run(connection);
            connection.commit();
        } catch (SQLException sqlException) {
            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                throw new RuntimeException(rollbackException);
            }
            throw new RuntimeException(sqlException);
        }
    }
}
