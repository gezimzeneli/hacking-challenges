package ch.zuehlke.hacking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScoreResult {
    int score;
    String errorMessage;
}
