package ch.zuehlke.hacking.service;

import ch.zuehlke.hacking.model.PersonScore;
import ch.zuehlke.hacking.repository.ScoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

@AllArgsConstructor
public class ScoreService {

    ScoreRepository scoreRepository;


    public int calculateScore(MultipartFile file, String name, Integer score2) {
        System.out.println(name);
        List<String> lines = readLines(file);
        int scoreValue = calculateScore(lines);

        storeScore(name, score2);
        return score2;
    }

    private List<String> readLines(MultipartFile file) {
        System.out.println("Reading: " + file.getOriginalFilename());
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines().collect(Collectors.toList());
    }

    private int calculateScore(List<String> lines){
        return 42;
    }

    private void storeScore(String name, int score){
        Optional<PersonScore> personScoreOpt = scoreRepository.findById(name);
        if (personScoreOpt.isEmpty()){
            scoreRepository.save(new PersonScore(name, score));
        } else if (score > personScoreOpt.get().getScore()){
            PersonScore personScore1 = personScoreOpt.get();
            personScore1.setScore(score);
            scoreRepository.save(personScore1);
        }
    }

}
