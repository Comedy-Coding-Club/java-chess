package view.command;

import java.util.ArrayList;
import java.util.List;

public class CommandDto {
    private final CommandType commandType;
    private final List<String> supplements;

    public CommandDto(final SeparatedCommandInput commandInput) {
        this.commandType = CommandType.from(commandInput);
        this.supplements = new ArrayList<>(commandInput.getSupplements());
    }

    public boolean isInvalidSupplementSize() {
        return supplements.size() != commandType.getSupplementsCount();
    }

    public List<String> getSupplements() {
        return supplements;
    }
}
