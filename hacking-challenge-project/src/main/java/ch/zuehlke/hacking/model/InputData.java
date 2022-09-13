package ch.zuehlke.hacking.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@AllArgsConstructor
public class InputData {
    private BigDecimal initialBalance;
    private Map<String, BuildingInformation> buildings;
}
