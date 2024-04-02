package chess.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class DatabaseConnectionGenerator {
    private final PropertiesFile config;

    public DatabaseConnectionGenerator(PropertiesFile config) {
        this.config = config;
    }

    public Connection getConnection() {
        try {
            String databaseUrl = getDatabaseUrl(config);
            String username = getProperty(config, "mysql.username");
            String password = getProperty(config, "mysql.password");
            return DriverManager.getConnection(databaseUrl, username, password);
        } catch (final SQLException e) {
            throw new RuntimeException("DB 연결 오류", e);
        }
    }

    private String getDatabaseUrl(PropertiesFile config) {
        String server = getProperty(config, "mysql.server");
        String database = getProperty(config, "mysql.database");
        String option = getProperty(config, "mysql.option");
        return "jdbc:mysql://" + server + "/" + database + option;
    }

    private String getProperty(PropertiesFile properties, String key) {
        Optional<String> property = properties.getProperty(key);
        if (property.isEmpty()) {
            throw new RuntimeException("설정 파일에 " + key + " 속성이 정의되어 있지 않습니다.");
        }
        return property.get();
    }
}
