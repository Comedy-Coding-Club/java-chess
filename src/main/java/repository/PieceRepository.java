package repository;

import static repository.PropertiesGenerator.properties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PieceRepository {
    private static final String SERVER = properties().getProperty("server");
    private static final String DATABASE = properties().getProperty("database");
    private static final String OPTION = properties().getProperty("option");
    private static final String USERNAME = properties().getProperty("username");
    private static final String PASSWORD = properties().getProperty("password");

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException exception) {
            System.err.println("DB 연결 오류:" + exception.getMessage());
            exception.printStackTrace();
            return null;
        }
    }
}
