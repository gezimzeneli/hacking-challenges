package ch.zuehlke.hacking.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class YearEntry {
    private int year;
    private List<TransactionCommand> operations;

    public YearEntry(int year) {
        this.year = year;
        this.operations = new ArrayList<>();
    }

    public void addTransactionCommand(TransactionCommand command) {
        operations.add(command);
    }
}
