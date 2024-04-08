package chess.controller;

import chess.dto.CommandDTO;

public interface CommandExecutor {
    State execute(CommandDTO commandDto);
}
