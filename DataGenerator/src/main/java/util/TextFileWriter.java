package util;

import model.Building;
import model.DataSet;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextFileWriter {

    public static void write(String filename, DataSet dataSet) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add(dataSet.getIniBalance() + "");
        for (Building building : dataSet.getBuildings()) {
            lines.add(building.getIdentifier()+";"+building.getBuildingYear()+";"+(building.isYieldProperty() ? "1" : "0")+ ";" + StringUtils.join(building.getPrices(), ","));
        }

        write(filename, lines);
    }

    public static void write(String filename, List<String> lines) throws IOException {
        File fout = new File(filename);
        FileOutputStream fos = new FileOutputStream(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        for (String line : lines) {
            bw.write(line);
            bw.newLine();
        }

        bw.close();
    }
}
