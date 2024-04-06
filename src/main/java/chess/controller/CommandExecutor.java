package chess.controller;

import chess.dto.CommandDto;

public interface CommandExecutor {
    State execute(CommandDto commandDto);
}
