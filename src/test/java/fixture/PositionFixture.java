package fixture;

import domain.position.File;
import domain.position.Position;
import domain.position.Rank;

public final class PositionFixture {

    public static final Position A1 = new Position(new File('a'), new Rank(1));
    public static final Position A2 = new Position(new File('a'), new Rank(2));
    public static final Position A3 = new Position(new File('a'), new Rank(3));

    public static final Position B1 = new Position(new File('b'), new Rank(1));
    public static final Position B2 = new Position(new File('b'), new Rank(2));
    public static final Position B3 = new Position(new File('b'), new Rank(3));
    public static final Position B4 = new Position(new File('b'), new Rank(4));
    public static final Position B5 = new Position(new File('b'), new Rank(5));
    public static final Position B7 = new Position(new File('b'), new Rank(7));

    public static final Position C1 = new Position(new File('c'), new Rank(1));
    public static final Position C2 = new Position(new File('c'), new Rank(2));
    public static final Position C3 = new Position(new File('c'), new Rank(3));
    public static final Position C5 = new Position(new File('c'), new Rank(5));
    public static final Position C7 = new Position(new File('c'), new Rank(7));


}
