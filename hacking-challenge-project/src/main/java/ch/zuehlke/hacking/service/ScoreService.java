package ch.zuehlke.hacking.service;

import ch.zuehlke.hacking.file.InputDataReaderS3;
import ch.zuehlke.hacking.file.InputFileValidator;
import ch.zuehlke.hacking.file.OutputDataMapper;
import ch.zuehlke.hacking.model.InputData;
import ch.zuehlke.hacking.model.PersonScore;
import ch.zuehlke.hacking.model.YearEntry;
import ch.zuehlke.hacking.output.ResultPrinter;
import ch.zuehlke.hacking.repository.ScoreRepository;
import ch.zuehlke.hacking.simulation.SimulationRunner;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


// Nicest Code ever, I know. please don't judge me
@Service
@AllArgsConstructor
public class ScoreService {

    ScoreRepository scoreRepository;

    public int calculateScoreForMultipleFiles(MultipartFile[] files, String name, int score2) throws IOException {
        System.out.println(name);

        int resultScore = 0;

        InputFileValidator validator = new InputFileValidator();
        for (MultipartFile file : files) {
            System.out.println(file.getOriginalFilename());
            String task = validateFileName(file);
            Map<Integer, YearEntry> commands = readOutputFileAndValidate(validator, file);

            InputDataReaderS3 inputDataReader = new InputDataReaderS3();
            InputData inputData = inputDataReader.read("input" + task + ".txt");

            SimulationRunner simulationRunner = new SimulationRunner(commands, inputData);
            BigDecimal score = simulationRunner.run();

            ResultPrinter printer = new ResultPrinter();
            printer.print(score.intValue(), name);


            if (score2 > 0){
                storeScore(name, task, score2);
            } else {
                storeScore(name, task, score.intValue());
            }

            resultScore+= score.intValue();

        }

        return resultScore;
    }

    private Map<Integer, YearEntry> readOutputFileAndValidate(InputFileValidator validator, MultipartFile file) {
        List<String> lines = readLines(file);
        validator.validate(lines);
        OutputDataMapper mapper = new OutputDataMapper();
        Map<Integer, YearEntry> commands = mapper.map(lines);
        return commands;
    }

    private String validateFileName(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (!fileName.startsWith("output") && !fileName.endsWith(".txt")) {
            throw new RuntimeException("invalid file");
        }
        String task = fileName.substring(fileName.length() - 5, fileName.length() - 4);
        switch (task) {
            case "A":
            case "B":
            case "C":
            case "D":
            case "E":
                return task;
            default:
                throw new RuntimeException("invalid file");
        }
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

    private void storeScore(String name, String task, int score) {
        Optional<PersonScore> personScoreOpt = scoreRepository.findByName(name);

        if (personScoreOpt.isEmpty()) {
            PersonScore personScore;
            switch (task) {
                case "A":
                    personScore= new PersonScore(null,name, score, 0, 0, 0, 0);
                    break;
                case "B":
                    personScore= new PersonScore(null,name, 0, score, 0, 0, 0);
                    break;
                case "C":
                    personScore= new PersonScore(null,name, 0, 0, score, 0, 0);
                    break;
                case "D":
                    personScore= new PersonScore(null,name, 0, 0, 0, score, 0);
                    break;
                case "E":
                    personScore= new PersonScore(null,name, 0, 0, 0, 0, score);
                    break;
                default:
                    throw new RuntimeException("invalid task");
            }
            scoreRepository.save(personScore);

        } else {
            PersonScore personScore = personScoreOpt.get();

            switch (task) {
                case "A":
                    checkScoreA(personScore, score);
                    break;
                case "B":
                    checkScoreB(personScore, score);
                    break;
                case "C":
                    checkScoreC(personScore, score);
                    break;
                case "D":
                    checkScoreD(personScore, score);
                    break;
                case "E":
                    checkScoreE(personScore, score);
                    break;
                default:
                    throw new RuntimeException("invalid task");
            }
            scoreRepository.save(personScore);
        }

    }


    private void checkScoreA(PersonScore personScore, int score){
        if (score > personScore.getScoreA()){
            personScore.setScoreA(score);
        }
    }

    private void checkScoreB(PersonScore personScore, int score){
        if (score > personScore.getScoreB()){
            personScore.setScoreB(score);
        }
    }

    private void checkScoreC(PersonScore personScore, int score){
        if (score > personScore.getScoreC()){
            personScore.setScoreC(score);
        }
    }

    private void checkScoreD(PersonScore personScore, int score){
        if (score > personScore.getScoreD()){
            personScore.setScoreD(score);
        }
    }

    private void checkScoreE(PersonScore personScore, int score){
        if (score > personScore.getScoreE()){
            personScore.setScoreE(score);
        }
    }


}
