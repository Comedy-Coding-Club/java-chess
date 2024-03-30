package repository;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesGenerator {
    public static Properties properties() {
        try (InputStream input = new FileInputStream("src/main/resources/application.properties")) {
            if (input == null) {
                throw new RuntimeException("[ERROR] application.properties 파일에 데이터베이스 정보를 등록해주세요.");
            }
            Properties properties = new Properties();
            properties.load(input);
            return properties;
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
