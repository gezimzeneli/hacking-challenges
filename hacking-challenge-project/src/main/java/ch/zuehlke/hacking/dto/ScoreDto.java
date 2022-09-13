package ch.zuehlke.hacking.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScoreDto implements Comparable<ScoreDto> {

    String name;
    int scoreA;
    int scoreB;
    int scoreC;
    int scoreD;
    int scoreE;
    int totalScore;

    @Override
    public int compareTo(ScoreDto o) {
        return  o.totalScore - totalScore;
    }
}
