package repository;

import domain.piece.Color;
import java.util.Arrays;

public enum ColorMapper {
    BLACK("BLACK", Color.BLACK),
    WHITE("WHITE", Color.WHITE),
    ;

    private final String fieldName;
    private final Color color;

    ColorMapper(final String fieldName, final Color color) {
        this.fieldName = fieldName;
        this.color = color;
    }

    public static Color getColorByFieldName(final String fieldName) {
        return Arrays.stream(ColorMapper.values())
                .filter(element -> element.fieldName.equals(fieldName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 잘못된 Color 필드 입력입니다."))
                .color;
    }
}
