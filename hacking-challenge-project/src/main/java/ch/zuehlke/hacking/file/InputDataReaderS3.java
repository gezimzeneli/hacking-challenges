package ch.zuehlke.hacking.file;


import ch.zuehlke.hacking.model.BuildingInformation;
import ch.zuehlke.hacking.model.InputData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InputDataReaderS3 {

    public InputData read(String filename) throws IOException {
        List<String> lines = getLines(filename);

        return new InputData(
                getInitialBalance(lines),
                getBuildings(lines)
        );
    }

    private List<String> getLines(String filename) throws IOException {

        URL u = new URL("https://hacking-challenge-inputdata.s3.amazonaws.com/" + filename);
        InputStream inputStream = u.openStream();
        List<String> lines = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines().collect(Collectors.toList());
        return lines;
    }

    private BigDecimal getInitialBalance(List<String> lines) {
        return new BigDecimal(lines.get(0));
    }

    private Map<String, BuildingInformation> getBuildings(List<String> lines) {
        Map<String, BuildingInformation> buildings = new HashMap<>();

        for (int i = 1; i < lines.size(); i++) {
            String[] splittedLine = lines.get(i).split(";");

            BuildingInformation building = new BuildingInformation();
            building.setIdentifier(splittedLine[0]);
            building.setYearBuilt(Integer.parseInt(splittedLine[1]));
            building.setYieldProperty(splittedLine[2].equals("1"));
            building.setAnnualPrices(getPrice(splittedLine));
            building.setYearDestroyed(Integer.parseInt(splittedLine[1]) + building.getAnnualPrices().length);

            buildings.put(building.getIdentifier(), building);
        }

        return buildings;
    }

    private Double[] getPrice(String[] splittedLine) {
        String[] pricesStr = splittedLine[3].split(",");
        Double[] prices = new Double[pricesStr.length];

        for (int i = 0; i < pricesStr.length; i++) {
            prices[i] = Double.parseDouble(pricesStr[i]);
        }

        return prices;
    }
}
