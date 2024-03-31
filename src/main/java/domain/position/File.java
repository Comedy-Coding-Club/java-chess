package domain.position;

import java.util.Objects;

public class File {
    private static final char MIN_LETTER = 'a';
    private static final char MAX_LETTER = 'h';

    private final char letter;

    public File(char letter) {
        validateLetterRange(letter);
        this.letter = letter;
    }

    private void validateLetterRange(char letter) {
        if (letter < MIN_LETTER || letter > MAX_LETTER) {
            throw new IllegalArgumentException(String.format("File의 범위는 최소 %c부터 %c 까지 입니다.", MIN_LETTER, MAX_LETTER));
        }
    }

    public File add(final int movement) {
        return new File((char) (letter + movement));
    }

    public int subtract(File target) {
        return letter - target.letter;
    }

    public char getLetter() {
        return letter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        File file = (File) o;
        return letter == file.letter;
    }

    @Override
    public int hashCode() {
        return Objects.hash(letter);
    }
}
