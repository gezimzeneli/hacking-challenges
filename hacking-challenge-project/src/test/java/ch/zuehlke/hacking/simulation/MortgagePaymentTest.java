package ch.zuehlke.hacking.simulation;


import ch.zuehlke.hacking.model.BuildingInformation;
import ch.zuehlke.hacking.model.InputData;
import ch.zuehlke.hacking.simulation.MortgagePayment;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MortgagePaymentTest {

    @Test
    @Disabled
    void test1() {
        InputData inputData = getInputData1();
        MortgagePayment payment = new MortgagePayment(inputData, BigDecimal.valueOf(1000), 3);

        BigDecimal balance = payment.pay().setScale(5, RoundingMode.HALF_UP);

        assertTrue(
                BigDecimal.valueOf(1000)
                        .subtract(
                                BigDecimal.valueOf(4.7969098846995123)
                                        .setScale(5, RoundingMode.HALF_UP)
                        ).compareTo(balance) == 0);
    }

    @Test
    void test2() {
        InputData inputData = getInputData2();
        MortgagePayment payment = new MortgagePayment(inputData, BigDecimal.valueOf(1000), 3);

        BigDecimal balance = payment.pay().setScale(5, RoundingMode.HALF_UP);

        assertTrue(BigDecimal.valueOf(1000).compareTo(balance) == 0);
    }



    private InputData getInputData1() {
        Map<String, BuildingInformation> buildings = new HashMap<>();

        BuildingInformation building = new BuildingInformation();
        building.setIdentifier("Building1");
        building.setYearBuilt(3);
        building.setYearDestroyed(7);
        building.setYieldProperty(true);
        building.setAnnualPrices(new Double[]{100.0, 105.0, 101.0, 98.0});
        building.setInPossession(true);
        building.setMortgagePartPercentage(30);
        building.setPurchasePrice(BigDecimal.valueOf(105));

        buildings.put(building.getIdentifier(), building);

        return new InputData(
                BigDecimal.valueOf(100),
                buildings
        );
    }

    private InputData getInputData2() {
        Map<String, BuildingInformation> buildings = new HashMap<>();

        BuildingInformation building = new BuildingInformation();
        building.setIdentifier("Building1");
        building.setYearBuilt(3);
        building.setYearDestroyed(7);
        building.setYieldProperty(true);
        building.setAnnualPrices(new Double[]{100.0, 105.0, 101.0, 98.0});
        building.setInPossession(true);
        building.setMortgagePartPercentage(0);
        building.setPurchasePrice(BigDecimal.valueOf(105));

        buildings.put(building.getIdentifier(), building);

        return new InputData(
                BigDecimal.valueOf(100),
                buildings
        );
    }

}