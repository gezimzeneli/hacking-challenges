package simulation;

import lombok.AllArgsConstructor;
import model.BuildingInformation;
import model.InputData;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
class RentCollector {

    private InputData input;
    private BigDecimal balance;
    private int year;

    BigDecimal collect() {
        List<BuildingInformation> buildings = getBuildings();

        for (BuildingInformation building : buildings) {
            BigDecimal rentIncomePercentage = BigDecimal.valueOf(getMieteinnahmen(year));
            BigDecimal incomeOfBuilding = building.getPrice(year).multiply(rentIncomePercentage).divide(BigDecimal.valueOf(100));
            balance = balance.add(incomeOfBuilding);
        }

        return balance;
    }

    private List<BuildingInformation> getBuildings() {
        return input.getBuildings()
                .values()
                .stream()
                .filter(building -> building.isYieldProperty())
                .filter(building -> building.isInPossession())
                .collect(Collectors.toList());
    }

    private double getMieteinnahmen(int year) {
        return Math.sin(0.2*year-42) + Math.sin(0.9*year) + Math.sin(0.01*year) + 3.1;
    }
}
