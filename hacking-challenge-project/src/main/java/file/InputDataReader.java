package file;

import model.BuildingInformation;
import model.InputData;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputDataReader {

    public InputData read(String filename) throws IOException {
        List<String> lines = getLines(filename);

        return new InputData(
                getInitialBalance(lines),
                getBuildings(lines)
        );
    }

    private List<String> getLines(String filename) throws IOException {
        TextFileReader reader = new TextFileReader();
        return reader.read(filename);
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
