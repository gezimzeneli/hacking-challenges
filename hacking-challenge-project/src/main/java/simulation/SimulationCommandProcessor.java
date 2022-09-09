package simulation;

import lombok.AllArgsConstructor;
import model.BuildingInformation;
import model.InputData;
import model.TransactionCommand;
import model.YearEntry;

import java.math.BigDecimal;
import java.util.Map;

@AllArgsConstructor
class SimulationCommandProcessor {

    private Map<Integer, YearEntry> operations;
    private InputData input;
    private BigDecimal balance;
    private int year;

    BigDecimal run() {
        YearEntry operation = operations.get(year);

        for (TransactionCommand command : operation.getOperations()) {
            switch (command.getType()) {
                case KAUFE:
                    buyBuilding(year, command);
                    break;
                case VERKAUFE:
                    sellBuilding(year, command);
                    break;
                case RENOVIERE:
                    renovateBuilding(year, command);
                    break;
            }
        }

        return balance;
    }

    private void buyBuilding(int year, TransactionCommand command) {
        BuildingInformation building = input.getBuildings().get(command.getBuildingID());

        // TODO Hypothek einbauen

        if (balance.compareTo(building.getPrice(year)) >= 0) {
            throw new IllegalStateException("Nicht genug Geld um das Gebäude " + building.getIdentifier() + " zu kaufen!");
        }

        balance = balance.subtract(building.getPrice(year));
        building.setInPossession(true);
    }

    private void sellBuilding(int year, TransactionCommand command) {
        BuildingInformation building = input.getBuildings().get(command.getBuildingID());

        if (!building.isInPossession()) {
            throw new IllegalStateException("Du kannst das Gebäude " + building.getIdentifier() + " nicht verkaufen ohne es vorher gekauft zu haben.");
        }

        balance = balance.add(building.getPrice(year));
        building.setInPossession(false);
    }

    private void renovateBuilding(int year, TransactionCommand command) {
        BuildingInformation building = input.getBuildings().get(command.getBuildingID());

        if (!(building.getYearBuilt() + 20 > year) && !(building.getYearBuilt() == 0)) {
            throw new IllegalStateException("Das Gebäude " + building.getIdentifier() + " darf noch nicht im Jahr " + year + " renoviert werden!");
        }

        // TODO Renoviere Gebäude
    }
}
