package chess.view;

import chess.domain.board.Board;
import chess.domain.chessGame.ChessGame;
import chess.domain.location.File;
import chess.domain.location.Location;
import chess.domain.location.Rank;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.piece.Score;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

public class OutputView {
    private static final Map<PieceType, String> PIECE_SYMBOL = Map.of(
            PieceType.KING, "k",
            PieceType.QUEEN, "q",
            PieceType.ROOK, "r",
            PieceType.KNIGHT, "n",
            PieceType.BISHOP, "b",
            PieceType.MOVED_PAWN, "p",
            PieceType.INITIAL_PAWN, "p"
    );

    public void printGameStart() {
        System.out.println("> 체스 게임을 시작합니다.");
        System.out.println("> 게임 시작 : start");
        System.out.println("> 게임 종료 : end");
        System.out.println("> 게임 이동 : move source위치 target위치 - 예. move b2 b3");
    }

    public void printBoard(Board board) {
        Arrays.stream(Rank.values()).sorted(Comparator.reverseOrder())
                .forEach(row -> {
                    printBoardRow(row, board.getBoard());
                    System.out.println();
                });
        System.out.println();
    }

    private void printBoardRow(Rank rank, Map<Location, Piece> board) {
        for (File file : File.values()) {
            Location location = new Location(file, rank);
            Piece piece = board.get(location);
            System.out.print(convertPieceToString(piece));
        }
    }

    private String convertPieceToString(Piece piece) {
        if (piece == null) {
            return ".";
        }
        String s = getPieceString(piece.getPieceType());
        if (piece.isColor(Color.BLACK)) {
            return s.toUpperCase();
        }
        return s.toLowerCase();
    }

    private String getPieceString(PieceType type) {
        return Optional.ofNullable(PIECE_SYMBOL.get(type))
                .orElseThrow(() -> new IllegalArgumentException("누락된 기물 타입이 존재합니다."));
    }

    public void printExceptionMessage(String exceptionMessage) {
        System.out.println(exceptionMessage);
    }

    public void printResult(ChessGame chessGame) {
        Score blackScore = chessGame.getScore(Color.BLACK);
        System.out.println("흑 : " + blackScore.getScore());
        Score whiteScore = chessGame.getScore(Color.WHITE);
        System.out.println("백 : " + whiteScore.getScore());

        System.out.println(getMatchResult(blackScore.getScore(), whiteScore.getScore()));
    }

    private String getMatchResult(double blackScore, double whiteScore) {
        if (blackScore > whiteScore) {
            return "흑 우세";
        }
        if (blackScore < whiteScore) {
            return "백 우세";
        }
        return "접전중";
    }

    public void printWinner(ChessGame game) {
        Color winner = game.getWinner();
        if (winner == Color.BLACK) {
            System.out.println("흑이 승리했습니다.");
            return;
        }
        System.out.println("백이 승리했습니다.");
    }
}
