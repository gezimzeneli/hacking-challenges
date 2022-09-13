import file.InputDataReader;
import file.ResultReader;
import model.InputData;
import model.YearEntry;
import output.ResultPrinter;
import simulation.SimulationRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

public class HackingChallengeMain {

    public static void main(String[] args) throws IOException {
        ResultReader reader = new ResultReader();
        Map<Integer, YearEntry> commands = reader.getResult("outputA.txt");

        InputDataReader inputDataReader = new InputDataReader();
        InputData inputData = inputDataReader.read("inputA.txt");

        SimulationRunner simulationRunner = new SimulationRunner(commands, inputData);
        BigDecimal score = simulationRunner.run();

        ResultPrinter printer = new ResultPrinter();
        printer.print(score.intValue());
    }

}
