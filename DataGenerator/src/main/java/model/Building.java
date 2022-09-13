package model;

import lombok.Data;

@Data
public class Building {
    private String identifier;
    private int buildingYear;
    private boolean isYieldProperty;
    private Integer[] prices;
}
