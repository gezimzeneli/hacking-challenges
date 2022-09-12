package ch.zuehlke.hacking.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BuildingInformation {
    private String identifier;
    private int yearBuilt;
    private int yearDestroyed;
    private boolean isYieldProperty;
    private Integer[] annualPrices;

    private boolean isInPossession = false;
    private int mortgagePartPercentage;
    private BigDecimal purchasePrice;

    public BigDecimal getPrice(int year) {
        int priceIndex = year - yearBuilt;

        if (priceIndex < 0 || priceIndex > annualPrices.length) {
            throw new IllegalStateException("Das Gebäude " + identifier + " existiert im Jahr " + year + " nicht!");
        }

        return BigDecimal.valueOf(annualPrices[priceIndex]);
    }
}
