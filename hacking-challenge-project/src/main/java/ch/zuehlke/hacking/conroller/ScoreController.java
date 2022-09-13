package ch.zuehlke.hacking.conroller;

import ch.zuehlke.hacking.service.ScoreService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Controller
@AllArgsConstructor
public class ScoreController {

    final ScoreService scoreService;

    @PostMapping("uploadFile")
    public ResponseEntity<Integer> upload(@RequestParam("file") MultipartFile file, @RequestParam("name") String name, @RequestParam("score") Integer score) throws IOException {

        int scoreResult = scoreService.calculateScore(file, name, score);

        return ResponseEntity.ok(scoreResult);

    }
}
