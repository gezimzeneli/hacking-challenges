package simulation;

import lombok.AllArgsConstructor;
import model.BuildingInformation;
import model.InputData;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
class MortgagePayment {

    private InputData input;
    private BigDecimal balance;
    private int year;


    BigDecimal pay() {
        List<BuildingInformation> possessedProperties = input.getBuildings()
                .values()
                .stream()
                .filter(building -> building.isInPossession())
                .filter(building -> building.getMortgagePartPercentage() > 0)
                .collect(Collectors.toList());

        // TODO Verifiziere
        for (BuildingInformation property : possessedProperties) {
            balance = balance.subtract(
                    property.getPurchasePrice()
                            .multiply(BigDecimal.valueOf(getHypothek(year)))
                            .divide(BigDecimal.valueOf(100))
            );
        }

        return balance;
    }

    public static double getHypothek(int year) {
        return Math.sin(0.2 * year) + Math.sin(0.6 * year) + Math.sin(0.01 * year) + 3;
    }
}
