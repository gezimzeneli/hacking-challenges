package ch.zuehlke.hacking.simulation;

import ch.zuehlke.hacking.model.BuildingInformation;
import ch.zuehlke.hacking.model.InputData;
import ch.zuehlke.hacking.model.YearEntry;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class SimulationRunner {

    private Map<Integer, YearEntry> operations;
    private InputData input;

    private BigDecimal balance;

    public SimulationRunner(Map<Integer, YearEntry> operations, InputData input) {
        this.operations = operations;
        this.input = input;
        balance = input.getInitialBalance();
    }


    public void run() {
        int year = 0;

        while (areBuildingsAvailable(year)) {
            collectRent(year);

            MortgagePayment payment = new MortgagePayment(input,balance, year);
            balance = payment.pay();

            if (operations.containsKey(year)) {
                SimulationCommandProcessor simulationCommandProcessor = new SimulationCommandProcessor(operations, input, balance, year);
                balance = simulationCommandProcessor.run();
            }

            year++;
        }
    }

    private boolean areBuildingsAvailable(int year) {
        return !input.getBuildings().values()
                .stream()
                .filter(building -> year >= building.getYearBuilt() && year <= building.getYearDestroyed())
                .collect(Collectors.toList())
                .isEmpty();
    }

    private void collectRent(int year) {
        List<BuildingInformation> possessedYieldProperties = input.getBuildings()
                .values()
                .stream()
                .filter(building -> building.isYieldProperty())
                .filter(building -> building.isInPossession())
                .collect(Collectors.toList());

        for (BuildingInformation building : possessedYieldProperties) {
            BigDecimal rentIncomePercentage = BigDecimal.valueOf(getMieteinnahmen(year));
            BigDecimal incomeOfBuilding = building.getPrice(year).multiply(rentIncomePercentage);
            balance = balance.add(incomeOfBuilding);
        }
    }

    public static double getMieteinnahmen(int year) {
        return Math.sin(0.2*year-42) + Math.sin(0.9*year) + Math.sin(0.01*year) + 3.1;
    }
}
