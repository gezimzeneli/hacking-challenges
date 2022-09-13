package ch.zuehlke.hacking.simulation;

import ch.zuehlke.hacking.model.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SimulationCommandProcessorTest_Sell {

    @Test
    void test_sell_1() {
        YearEntry yearEntry = getYearEntry(getTransactionCommand(TransactionCommandType.VERKAUFE));
        Map<Integer, YearEntry> commandsMap = new HashMap<>();
        commandsMap.put(yearEntry.getYear(), yearEntry);
        InputData inputData = getInputData(0);

        SimulationCommandProcessor commandProcessor = new SimulationCommandProcessor(commandsMap, inputData, BigDecimal.valueOf(1000), 3);
        BigDecimal balance = commandProcessor.run();

        assertTrue(BigDecimal.valueOf(1098).compareTo(balance) == 0);
    }

    @Test
    void test_buy_2() {
        YearEntry yearEntry = getYearEntry(getTransactionCommand(TransactionCommandType.VERKAUFE));
        Map<Integer, YearEntry> commandsMap = new HashMap<>();
        commandsMap.put(yearEntry.getYear(), yearEntry);
        InputData inputData = getInputData(30);

        SimulationCommandProcessor commandProcessor = new SimulationCommandProcessor(commandsMap, inputData, BigDecimal.valueOf(1000), 3);
        BigDecimal balance = commandProcessor.run();

        assertTrue(BigDecimal.valueOf(1066.5).compareTo(balance) == 0);
    }

    private List<TransactionCommand> getTransactionCommand(TransactionCommandType type) {
        TransactionCommand command = new TransactionCommand(
                type, "Building1"
        );

        List<TransactionCommand> commands = new ArrayList<>();
        commands.add(command);

        return commands;
    }

    private YearEntry getYearEntry(List<TransactionCommand> transactionCommands) {
        return new YearEntry(3, transactionCommands);
    }

    private InputData getInputData(int mortgagePartPercentage) {
        Map<String, BuildingInformation> buildings = new HashMap<>();

        BuildingInformation building = new BuildingInformation();
        building.setIdentifier("Building1");
        building.setYearBuilt(0);
        building.setYearDestroyed(7);
        building.setYieldProperty(false);
        building.setAnnualPrices(new Double[]{100.0, 105.0, 101.0, 98.0, 110.0});
        building.setMortgagePartPercentage(mortgagePartPercentage);
        building.setInPossession(true);
        building.setPurchasePrice(BigDecimal.valueOf(105));

        buildings.put(building.getIdentifier(), building);

        return new InputData(
                BigDecimal.valueOf(100),
                buildings
        );
    }

}