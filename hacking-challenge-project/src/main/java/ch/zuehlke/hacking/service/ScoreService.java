package ch.zuehlke.hacking.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScoreService {


    public int calculateScore(MultipartFile file, String name) {
        System.out.println(name);
        List<String> lines = readLines(file);
        calculateScore(lines);
        return 0;
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

}
