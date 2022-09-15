package ch.zuehlke.hacking.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BuildingInformation {
    private String identifier;
    private int yearBuilt;
    private int yearDestroyed;
    private boolean isYieldProperty;
    private Double[] annualPrices;
    private int numberOfRenovations = 0;

    private boolean isInPossession = false;
    private int mortgagePartPercentage;
    private BigDecimal purchasePrice;

    public BigDecimal getPrice(int year) {
        int priceIndex = year - yearBuilt;

        if (priceIndex < 0) {
            throw new IllegalStateException("Das Gebäude " + identifier + " existiert im Jahr " + year + " nicht!");
        }

        if (yearBuilt + yearDestroyed <= year) {
            return BigDecimal.ZERO;
        }

        return BigDecimal.valueOf(annualPrices[priceIndex]);
    }

    public void renovate(int year) {
        int priceIndex = year - yearBuilt;
        this.numberOfRenovations++;

        for (int i = priceIndex; i < annualPrices.length; i++) {
            annualPrices[i] = annualPrices[i] * 1.2;
        }
    }
}
