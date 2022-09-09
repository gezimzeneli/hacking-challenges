package model;

import lombok.Data;

@Data
public class TransactionCommand {
    private TransactionCommandType type;
    private String buildingID;
    private int hypoVolumeInPercent;

    public TransactionCommand(TransactionCommandType type, String buildingID) {
        this.type = type;
        this.buildingID = buildingID;
    }
}
