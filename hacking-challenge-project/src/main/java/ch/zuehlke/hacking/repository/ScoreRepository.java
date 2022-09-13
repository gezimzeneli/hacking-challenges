package ch.zuehlke.hacking.repository;

import ch.zuehlke.hacking.model.PersonScore;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScoreRepository extends CrudRepository<PersonScore, String> {

    Optional<PersonScore> findByName(String name);
}
