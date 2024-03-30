package chess.repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;


public class YmlFile {
    private static final Pattern YML_PATTERN = Pattern.compile("(\\w+): (.+)"); //"[KEY]: [VALUE] (주석 포함 가능)"

    private final Map<String, String> properties;

    public YmlFile(Map<String, String> properties) {
        this.properties = properties;
    }

    /**
     *
     * @param fileName src\main\resources 에 위치한 YML 파일
     * @return 파일 이름으로 만들어진 YmlFile 클래스
     */
    public static YmlFile of(String fileName) {
        if (!fileName.contains("yml") && !fileName.contains("yaml")) {
            throw new IllegalArgumentException("yaml 파일이 아닙니다.");
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("./src/\\main\\resources\\" + fileName));
            Map<String, String> properties = parsingProperty(bufferedReader);
            return new YmlFile(properties);
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
        if (!YML_PATTERN.matcher(propertyLine).matches()) {
            return;
        }
        String[] keyValue = propertyLine.split(": ");
        String key = keyValue[0];
        String value = keyValue[1].split("#")[0].strip();
        properties.put(key, value);
    }

    public Optional<String> getProperty(String key) {
        return Optional.ofNullable(properties.get(key));
    }
}
