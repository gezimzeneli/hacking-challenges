package ch.zuehlke.hacking.simulation;

import ch.zuehlke.hacking.model.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SimulationCommandProcessorTest_Renovate {

    @Test
    void test_renovate_1() {
        List<TransactionCommand> commands = getTransactionCommand(TransactionCommandType.RENOVIERE);
        commands.get(0).setHypoVolumeInPercent(0);
        YearEntry yearEntry = getYearEntry(commands);
        Map<Integer, YearEntry> commandsMap = new HashMap<>();
        commandsMap.put(yearEntry.getYear(), yearEntry);
        InputData inputData = getInputData();
        inputData.getBuildings().get("Building1").setYearBuilt(2);

        SimulationCommandProcessor commandProcessor = new SimulationCommandProcessor(commandsMap, inputData, BigDecimal.valueOf(1000), 3);

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            commandProcessor.run();
        });

        assertTrue(exception.getMessage().contains("darf noch nicht im Jahr"));
    }

    @Test
    void test_renovate_2() {
        List<TransactionCommand> commands = getTransactionCommand(TransactionCommandType.RENOVIERE);
        commands.get(0).setHypoVolumeInPercent(0);
        YearEntry yearEntry = getYearEntry(commands);
        Map<Integer, YearEntry> commandsMap = new HashMap<>();
        commandsMap.put(yearEntry.getYear(), yearEntry);
        InputData inputData = getInputData();

        SimulationCommandProcessor commandProcessor = new SimulationCommandProcessor(commandsMap, inputData, BigDecimal.ONE, 3);
        BigDecimal balance = commandProcessor.run();

        assertTrue(balance.compareTo(BigDecimal.ONE) == 0);
    }

    @Test
    void test_renovate_3() {
        List<TransactionCommand> commands = getTransactionCommand(TransactionCommandType.RENOVIERE);
        commands.get(0).setHypoVolumeInPercent(0);
        YearEntry yearEntry = getYearEntry(commands);
        Map<Integer, YearEntry> commandsMap = new HashMap<>();
        commandsMap.put(yearEntry.getYear(), yearEntry);
        InputData inputData = getInputData();

        SimulationCommandProcessor commandProcessor = new SimulationCommandProcessor(commandsMap, inputData, BigDecimal.valueOf(1000), 3);
        BigDecimal balance = commandProcessor.run();

        assertTrue(balance.compareTo(BigDecimal.valueOf(975.5)) == 0);
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

    private InputData getInputData() {
        Map<String, BuildingInformation> buildings = new HashMap<>();

        BuildingInformation building = new BuildingInformation();
        building.setIdentifier("Building1");
        building.setYearBuilt(0);
        building.setYearDestroyed(7);
        building.setYieldProperty(false);
        building.setAnnualPrices(new Double[]{100.0, 105.0, 101.0, 98.0});
        building.setInPossession(true);
        building.setMortgagePartPercentage(30);
        building.setPurchasePrice(BigDecimal.valueOf(105));

        buildings.put(building.getIdentifier(), building);

        return new InputData(
                BigDecimal.valueOf(100),
                buildings
        );
    }

}