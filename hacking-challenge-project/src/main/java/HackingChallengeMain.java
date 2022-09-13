import ch.zuehlke.hacking.file.ResultReader;
import ch.zuehlke.hacking.model.InputData;
import ch.zuehlke.hacking.model.YearEntry;
import ch.zuehlke.hacking.output.ResultPrinter;
import ch.zuehlke.hacking.simulation.SimulationRunner;
import ch.zuehlke.hacking.file.InputDataReader;

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
