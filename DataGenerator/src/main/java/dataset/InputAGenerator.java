package dataset;

import model.Building;
import model.DataSet;
import util.RandomGenerator;

import java.util.ArrayList;
import java.util.Collections;

public class InputAGenerator {

    DataSet dataSet;
    int buildingCounter = 0;

    public InputAGenerator() {
        dataSet = new DataSet();
        dataSet.setIniBalance(300);
        dataSet.setBuildings(new ArrayList<>());
    }

    public DataSet generate() {
        generateCheapBuildings();
        generateUpperClassBuildings();
        generateYieldProperties();
        Collections.shuffle(dataSet.getBuildings());

        return dataSet;
    }

    private void generateCheapBuildings() {
        for (int i = 0; i < 50; i++, buildingCounter++) {
            Building building = new Building();
            building.setIdentifier("Building" + buildingCounter);
            building.setBuildingYear(RandomGenerator.randomInteger(0, 300));
            building.setYieldProperty(false);
            building.setPrices(getPrices(100.0));

            dataSet.add(building);
        }
    }

    private void generateUpperClassBuildings() {
        for (int i = 0; i < 50; i++, buildingCounter++) {
            Building building = new Building();
            building.setIdentifier("Building" + buildingCounter);
            building.setBuildingYear(RandomGenerator.randomInteger(0, 300));
            building.setYieldProperty(false);
            building.setPrices(getPrices(150.0));

            dataSet.add(building);
        }
    }

    private void generateYieldProperties() {
        for (int i = 0; i < 50; i++, buildingCounter++) {
            Building building = new Building();
            building.setIdentifier("Building" + buildingCounter);
            building.setBuildingYear(RandomGenerator.randomInteger(0, 300));
            building.setYieldProperty(true);
            building.setPrices(getPrices(160.0));

            dataSet.add(building);
        }
    }




    private Integer[] getPrices(double base) {
        int years = RandomGenerator.randomInteger(70, 200);
        double leftMove = RandomGenerator.randomInteger(0, 4);
        Integer[] prices = new Integer[years];

        for (int i = 0; i < years; i++) {
            int approximatePrice = (int)getHousePrice(i, leftMove, base);
            prices[i] = RandomGenerator.randomInteger(approximatePrice-5, approximatePrice+5);
        }

        return prices;
    }

    private double getHousePrice(int year, double leftMove, double base) {
        return 8 * Math.sin(0.05 * year - leftMove) + Math.sin(year) + base;
    }


}
