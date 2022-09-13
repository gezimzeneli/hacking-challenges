package ch.zuehlke.hacking.model;

public enum TransactionCommandType {
    KAUFE("KAUFE"),
    VERKAUFE("VERKAUFE"),
    RENOVIERE("RENOVIERE");

    private String command;

    TransactionCommandType(String command) {
        this.command = command;
    }

    public static TransactionCommandType getCommand(String command) {
        return TransactionCommandType.valueOf(command);
    }

}
