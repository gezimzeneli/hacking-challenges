package ch.zuehlke.hacking.simulation;

import ch.zuehlke.hacking.model.BuildingInformation;
import ch.zuehlke.hacking.model.InputData;
import ch.zuehlke.hacking.model.TransactionCommand;
import ch.zuehlke.hacking.model.YearEntry;
import lombok.AllArgsConstructor;

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

        if (building.isInPossession()) {
            throw new IllegalStateException("Du kannst das Gebäude " + building.getIdentifier() + " nicht verkaufen, du besitzt es schon.");
        }

        if (balance.compareTo(building.getPrice(year)) <= 0) {
            return;
        }

        BigDecimal priceInclHypo = BigDecimal.valueOf(100 - command.getHypoVolumeInPercent())
                .multiply(building.getPrice(year))
                .divide(BigDecimal.valueOf(100));
        balance = balance.subtract(priceInclHypo);

        building.setInPossession(true);
        building.setMortgagePartPercentage(command.getHypoVolumeInPercent());
        building.setPurchasePrice(building.getPrice(year));
    }

    private void sellBuilding(int year, TransactionCommand command) {
        BuildingInformation building = input.getBuildings().get(command.getBuildingID());

        if (!building.isInPossession()) {
            throw new IllegalStateException("Du kannst das Gebäude " + building.getIdentifier() + " nicht verkaufen ohne es vorher gekauft zu haben.");
        }

        BigDecimal moneyYouAreGettingBack = building.getPrice(year);

        if (building.getMortgagePartPercentage() > 0) {
            BigDecimal hypo = building.getPurchasePrice()
                    .multiply(BigDecimal.valueOf(building.getMortgagePartPercentage()))
                    .divide(BigDecimal.valueOf(100));
            moneyYouAreGettingBack = moneyYouAreGettingBack.subtract(hypo);
        }

        balance = balance.add(moneyYouAreGettingBack);
        building.setInPossession(false);
        building.setMortgagePartPercentage(0);
        building.setPurchasePrice(BigDecimal.ZERO);
    }

    private void renovateBuilding(int year, TransactionCommand command) {
        BuildingInformation building = input.getBuildings().get(command.getBuildingID());

        if (year < building.getYearBuilt() + 20 && building.getYearBuilt() != 0) {
            throw new IllegalStateException("Das Gebäude " + building.getIdentifier() + " darf noch nicht im Jahr " + year + " renoviert werden!");
        }

        BigDecimal renovationCost = building.getPrice(year).multiply(BigDecimal.valueOf(0.25));

        if (renovationCost.compareTo(balance) > 0) {
            return;
        }

        balance = balance.subtract(renovationCost);
        building.renovate(year);
    }
}
