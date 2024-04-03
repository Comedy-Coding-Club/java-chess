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
        try {
            execute(transaction);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    private void execute(TransactionalQuery transaction) throws SQLException {
        Connection connection = this.connectionGenerator.getConnection();
        try {
            connection.setAutoCommit(false);
            transaction.run(connection);
            connection.commit();
        } catch (SQLException sqlException) {
            connection.rollback();
            throw new DatabaseException(sqlException);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
