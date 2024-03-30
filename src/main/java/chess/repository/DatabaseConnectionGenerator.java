package chess.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class DatabaseConnectionGenerator {
    private static final YmlFile CONFIG = YmlFile.of("database.yml");

    public Connection getConnection() {
        try {
            String databaseUrl = getDatabaseUrl(CONFIG);
            String username = getProperty(CONFIG, "MYSQL_USERNAME");
            String password = getProperty(CONFIG, "MYSQL_PASSWORD");
            return DriverManager.getConnection(databaseUrl, username, password);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private String getDatabaseUrl(YmlFile config) {
        String server = getProperty(config, "MYSQL_SERVER");
        String database = getProperty(config, "MYSQL_DATABASE");
        String option = getProperty(config, "MYSQL_OPTION");
        return "jdbc:mysql://" + server + "/" + database + option;
    }

    private String getProperty(YmlFile yml, String key) {
        Optional<String> property = yml.getProperty(key);
        if (property.isEmpty()) {
            throw new IllegalArgumentException("파일에 " + key + " 속성이 정의되어 있지 않습니다.");
        }
        return property.get();
    }
}
