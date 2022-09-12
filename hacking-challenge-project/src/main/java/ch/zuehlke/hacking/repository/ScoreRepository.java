package ch.zuehlke.hacking.repository;

import ch.zuehlke.hacking.model.PersonScore;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends CrudRepository<PersonScore, String> {
}
