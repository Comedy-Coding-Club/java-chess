package chess.domain.location;

import chess.domain.board.Direction;
import java.util.Currency;
import java.util.Objects;

//TODO 캐싱
public class Location {

    private final File file;
    private final Rank rank;

    public Location(File file, Rank rank) {
        this.file = file;
        this.rank = rank;
    }

    public Location move(Direction direction) {
        File movedFile = this.file.move(direction);
        Rank movedRank = this.rank.move(direction);
        return new Location(movedFile, movedRank);
    }

    public static Location of(String input) {
        validateInput(input);
        File file = File.createByName(input.substring(0, 1));
        Rank rank = Rank.createByRank(input.substring(1, 2));
        return new Location(file, rank);
    }

    private static void validateInput(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("빈 값을 위치로 입력할 수 없습니다.");
        }
        if (input.length() != 2) {
            throw new IllegalArgumentException("잘못된 위치 입력입니다. 입력 형식 : A~H 사이의 알파벳 + 1~8 사이의 숫자");
        }
    }

    public int calculateRankDistance(Location target) {
        return this.rank.calculateDistance(target.rank);
    }

    public int calculateFileDistance(Location target) {
        return this.file.calculateDistance(target.file);
    }

    public Location copy() {
        return new Location(this.file, this.rank);
    }

    public File getFile() {
        return file;
    }

    public Rank getRank() {
        return rank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Location location = (Location) o;
        return file == location.file && rank == location.rank;
    }

    @Override
    public int hashCode() {
        return Objects.hash(file, rank);
    }
}
