package simulation;

import model.InputData;
import model.YearEntry;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class SimulationRunner {

    private Map<Integer, YearEntry> operations;
    private InputData input;

    private List<BigDecimal> balanceOverYears = new ArrayList<>();
    private BigDecimal balance;

    public SimulationRunner(Map<Integer, YearEntry> operations, InputData input) {
        this.operations = operations;
        this.input = input;
        balance = input.getInitialBalance();
    }

    public BigDecimal run() {
        int year = 0;

        while (areBuildingsAvailable(year)) {
            RentCollector rentCollector = new RentCollector(input, balance, year);
            balance = rentCollector.collect();

            MortgagePayment payment = new MortgagePayment(input,balance, year);
            balance = payment.pay();

            if (operations.containsKey(year)) {
                SimulationCommandProcessor simulationCommandProcessor = new SimulationCommandProcessor(operations, input, balance, year);
                balance = simulationCommandProcessor.run();
            }

            balanceOverYears.add(balance);
            year++;
        }

        return balance;
    }

    private boolean areBuildingsAvailable(int year) {
        return !input.getBuildings().values()
                .stream()
                .filter(building -> year >= building.getYearBuilt() && year <= building.getYearDestroyed())
                .collect(Collectors.toList())
                .isEmpty();
    }
}
