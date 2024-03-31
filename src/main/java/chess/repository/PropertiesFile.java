package chess.repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;


public class PropertiesFile {
    private static final String DELIMITER = "=";
    private static final Pattern PROPERTY_PATTERN = Pattern.compile("(\\w.+)" + DELIMITER + "(.+)");
    // "[KEY] = [VALUE]"

    private final Map<String, String> properties;

    public PropertiesFile(Map<String, String> properties) {
        this.properties = properties;
    }

    /**
     *
     * @param fileName src\main\resources 에 위치한 properties 파일
     * @return 파일 이름으로 만들어진 PropertiesFile 클래스
     */
    public static PropertiesFile of(String fileName) {
        if (!fileName.contains("properties")) {
            throw new IllegalArgumentException("properties 파일이 아닙니다.");
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("./src/\\main\\resources\\" + fileName));
            Map<String, String> properties = parsingProperty(bufferedReader);
            return new PropertiesFile(properties);
        } catch (IOException e) {
            throw new RuntimeException("입출력 오류", e);
        }
    }

    private static Map<String, String> parsingProperty(BufferedReader bufferedReader) throws IOException {
        Map<String, String> properties = new HashMap<>();

        String propertyLine;
        while ((propertyLine = bufferedReader.readLine()) != null) {
            saveProperty(propertyLine, properties);
        }

        return properties;
    }

    private static void saveProperty(String propertyLine, Map<String, String> properties) {
        if (!PROPERTY_PATTERN.matcher(propertyLine).matches()) {
            return;
        }
        String[] keyValue = propertyLine.split(DELIMITER);
        String key = keyValue[0].strip();
        StringBuilder valueBuilder = new StringBuilder();
        for (int i = 1; i < keyValue.length; i++) {
            valueBuilder.append(keyValue[i]);
        }
        String value = valueBuilder.toString().strip();
        properties.put(key, value);
    }

    public Optional<String> getProperty(String key) {
        return Optional.ofNullable(properties.get(key));
    }
}
