package fixture;

import static fixture.PositionFixture.*;

import domain.piece.*;
import domain.piece.piecerole.*;
import domain.position.*;
import java.util.*;

public class PiecePositionFixture {

    /*
     * .KR.....  8
     * P.PB....  7
     * .P..Q...  6
     * ........  5
     * .....nq.  4
     * .....p.p  3
     * .....pp.  2
     * ....rk..  1
     * abcdefgh
     */
    // TODO: 맵이 변하지 않는다는 것을 어떻게 확신하지?
    public static final Map<Position, Piece> PIECE_POSITION_FOR_CHECKING_STATUS = Map.ofEntries(
            Map.entry(B8, new Piece(King.create(), Color.BLACK)),
            Map.entry(C8, new Piece(Rook.create(), Color.BLACK)),
            Map.entry(A7, new Piece(BlackPawn.create(), Color.BLACK)),
            Map.entry(C7, new Piece(BlackPawn.create(), Color.BLACK)),
            Map.entry(D7, new Piece(Bishop.create(), Color.BLACK)),
            Map.entry(B6, new Piece(BlackPawn.create(), Color.BLACK)),
            Map.entry(E6, new Piece(Queen.create(), Color.BLACK)),
            Map.entry(F4, new Piece(Knight.create(), Color.WHITE)),
            Map.entry(G4, new Piece(Queen.create(), Color.WHITE)),
            Map.entry(F3, new Piece(WhitePawn.create(), Color.WHITE)),
            Map.entry(H3, new Piece(WhitePawn.create(), Color.WHITE)),
            Map.entry(F2, new Piece(WhitePawn.create(), Color.WHITE)),
            Map.entry(G2, new Piece(WhitePawn.create(), Color.WHITE)),
            Map.entry(E1, new Piece(Rook.create(), Color.WHITE)),
            Map.entry(F1, new Piece(King.create(), Color.WHITE)
            )
    );
}
