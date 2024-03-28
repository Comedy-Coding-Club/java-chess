package fixture;

import static fixture.PositionFixture.A7;
import static fixture.PositionFixture.B6;
import static fixture.PositionFixture.B8;
import static fixture.PositionFixture.C7;
import static fixture.PositionFixture.C8;
import static fixture.PositionFixture.D7;
import static fixture.PositionFixture.E1;
import static fixture.PositionFixture.E6;
import static fixture.PositionFixture.F1;
import static fixture.PositionFixture.F2;
import static fixture.PositionFixture.F3;
import static fixture.PositionFixture.F4;
import static fixture.PositionFixture.G2;
import static fixture.PositionFixture.G4;
import static fixture.PositionFixture.H3;

import domain.piece.Color;
import domain.piece.Piece;
import domain.piece.piecerole.Bishop;
import domain.piece.piecerole.BlackPawn;
import domain.piece.piecerole.King;
import domain.piece.piecerole.Knight;
import domain.piece.piecerole.Queen;
import domain.piece.piecerole.Rook;
import domain.piece.piecerole.WhitePawn;
import domain.position.Position;
import java.util.Map;

public class PiecePositionFixture {
    private Map<Position, Piece> generateChessBoardForCheckingStatus() {
        return Map.ofEntries(
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
}
