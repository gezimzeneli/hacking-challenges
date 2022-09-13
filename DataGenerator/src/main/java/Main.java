import dataset.InputAGenerator;
import dataset.InputBGenerator;
import model.DataSet;
import util.TextFileWriter;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        /*
        InputAGenerator generatorInputA = new InputAGenerator();
        DataSet dataSetInputA = generatorInputA.generate();
        TextFileWriter.write("inputA.txt", dataSetInputA);

         */

        InputBGenerator generatorInputB = new InputBGenerator();
        DataSet dataSetInputB = generatorInputB.generate();
        TextFileWriter.write("inputB.txt", dataSetInputB);

    }

}