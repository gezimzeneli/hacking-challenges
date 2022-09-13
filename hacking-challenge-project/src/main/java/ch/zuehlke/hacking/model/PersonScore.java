package ch.zuehlke.hacking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonScore {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int scoreA;
    private int scoreB;
    private int scoreC;
    private int scoreD;
    private int scoreE;
}
