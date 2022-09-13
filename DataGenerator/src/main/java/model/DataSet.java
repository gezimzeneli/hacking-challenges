package model;

import lombok.Data;

import java.util.List;

@Data
public class DataSet {
    private int iniBalance;
    private List<Building> buildings;

    public void add(Building building) {
        buildings.add(building);
    }
}
