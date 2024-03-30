package controller.dto;

import controller.constants.Winner;

public record GameResult(
        Winner winner,
        double blackScore,
        double whiteScore
) {
}
