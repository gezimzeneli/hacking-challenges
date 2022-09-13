package dataset;

import model.Building;
import model.DataSet;
import util.RandomGenerator;

import java.util.ArrayList;
import java.util.Collections;

public class InputDGenerator {

    DataSet dataSet;
    int buildingCounter = 0;

    public InputDGenerator() {
        dataSet = new DataSet();
        dataSet.setIniBalance(300);
        dataSet.setBuildings(new ArrayList<>());
    }

    public DataSet generate() {
        for (int i = 0; i < 5; i++)
            generateCheapBuildings();

        generateUpperClassBuildings();

        Collections.shuffle(dataSet.getBuildings());

        return dataSet;
    }

    private void generateCheapBuildings() {
        for (int a = 0; a < 20; a++) {
            for (int i = 0; i < 400; i++, buildingCounter++) {
                Building building = new Building();
                building.setIdentifier("Building" + buildingCounter);
                building.setBuildingYear(RandomGenerator.randomInteger(0, 800));
                building.setYieldProperty(false);
                if (RandomGenerator.randomInteger(0, 100) > 90) {
                    building.setPrices(getPrices2(100.0, a));
                } else {
                    building.setPrices(getPrices1(100.0, a));
                }

                dataSet.add(building);
            }
        }
    }

    private void generateUpperClassBuildings() {
        for (int i = 0; i < 5000; i++, buildingCounter++) {
            Building building = new Building();
            building.setIdentifier("Building" + buildingCounter);
            building.setBuildingYear(RandomGenerator.randomInteger(0, 800));
            building.setYieldProperty(false);
            if (RandomGenerator.randomInteger(0, 100) > 90) {
                building.setPrices(getPrices2(150.0, 1));
            } else {
                building.setPrices(getPrices1(150.0, 1));
            }

            dataSet.add(building);
        }
    }

    private void generateYieldBuildings() {
        for (int i = 0; i < 5000; i++, buildingCounter++) {
            Building building = new Building();
            building.setIdentifier("Building" + buildingCounter);
            building.setBuildingYear(RandomGenerator.randomInteger(0, 800));
            building.setYieldProperty(true);
            if (RandomGenerator.randomInteger(0, 100) > 90) {
                building.setPrices(getPrices2(150.0, 1));
            } else {
                building.setPrices(getPrices1(150.0, 1));
            }

            dataSet.add(building);
        }
    }



    private Integer[] getPrices1(double base, int duration) {
        int years = RandomGenerator.randomInteger(50+(duration*5), 300+(duration*5));
        double leftMove = RandomGenerator.randomInteger(0, 4);
        Integer[] prices = new Integer[years];

        for (int i = 0; i < years; i++) {
            int approximatePrice = (int)(8 * Math.sin(0.05 * i - leftMove) + Math.sin(i) + base + (-1*0.04)*i);
            prices[i] = RandomGenerator.randomInteger(approximatePrice-5, approximatePrice+5);
        }

        return prices;
    }

    private Integer[] getPrices2(double base, int duration) {
        int years = RandomGenerator.randomInteger(30+(duration*5), 200+(duration*5));
        double leftMove = RandomGenerator.randomInteger(0, 4);
        Integer[] prices = new Integer[years];

        for (int i = 0; i < years; i++) {
            int approximatePrice = (int)(80 * Math.sin(0.01 * i - leftMove) + Math.sin(i) + base + (-1*0.04)*i);
            prices[i] = RandomGenerator.randomInteger(approximatePrice-5, approximatePrice+5);
        }

        return prices;
    }


}
