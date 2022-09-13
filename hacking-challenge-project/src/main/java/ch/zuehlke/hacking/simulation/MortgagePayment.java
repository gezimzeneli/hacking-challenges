package ch.zuehlke.hacking.simulation;

import ch.zuehlke.hacking.model.BuildingInformation;
import ch.zuehlke.hacking.model.InputData;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
class MortgagePayment {

    private InputData input;
    private BigDecimal balance;
    private int year;


    BigDecimal pay() {
        List<BuildingInformation> possessedProperties = getBuildings();

        for (BuildingInformation property : possessedProperties) {
            balance = balance.subtract(
                    property.getPurchasePrice()
                            .multiply(BigDecimal.valueOf(getHypothek(year)))
                            .divide(BigDecimal.valueOf(100))
            );
        }

        return balance;
    }

    private List<BuildingInformation> getBuildings() {
        return input.getBuildings()
                .values()
                .stream()
                .filter(building -> building.isInPossession())
                .filter(building -> building.getMortgagePartPercentage() > 0)
                .collect(Collectors.toList());
    }

    public double getHypothek(int year) {
        return Math.sin(0.2 * year) + Math.sin(0.6 * year) + Math.sin(0.01 * year) + 3;
    }
}
