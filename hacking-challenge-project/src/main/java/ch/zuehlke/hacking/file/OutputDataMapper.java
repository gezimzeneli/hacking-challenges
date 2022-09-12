package ch.zuehlke.hacking.file;

import ch.zuehlke.hacking.model.TransactionCommand;
import ch.zuehlke.hacking.model.TransactionCommandType;
import ch.zuehlke.hacking.model.YearEntry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class OutputDataMapper {
    public Map<Integer, YearEntry> map(List<String> lines) {
        Map<Integer, YearEntry> operations = new HashMap<>();

        YearEntry entry = null;

        for (String line : lines) {
            if (isYear(line)) {
                int year = getYear(line);
                entry = new YearEntry(year);
                operations.put(year, entry);
            } else {
                entry.addTransactionCommand(getTransactionCommand(line));
            }
        }

        return operations;
    }

    private TransactionCommand getTransactionCommand(String line) {
        String[] commandParts = line.split("\\s+");

        TransactionCommand command = new TransactionCommand(
                TransactionCommandType.getCommand(commandParts[0]),
                commandParts[1]
        );

        if (commandParts.length == 3) {
            command.setHypoVolumeInPercent(Integer.parseInt(commandParts[2]));
        }

        return command;
    }

    private int getYear(String line) {
        return Integer.parseInt(line);
    }

    private boolean isYear(String line) {
        try {
            Integer.parseInt(line);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }


}
