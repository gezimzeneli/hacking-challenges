package ch.zuehlke.hacking.conroller;

import ch.zuehlke.hacking.dto.ScoreDto;
import ch.zuehlke.hacking.model.PersonScore;
import ch.zuehlke.hacking.service.ScoreService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@Controller
@AllArgsConstructor
public class ScoreController {

    final ScoreService scoreService;

    @PostMapping("uploadFiles")
    public ResponseEntity<Integer> uploadFiles(@RequestParam("files") MultipartFile[] files, @RequestParam("name") String name, @RequestParam("score") int score) throws IOException {

        int scoreResult = scoreService.calculateScoreForMultipleFiles(files, name, score);

        return ResponseEntity.ok(scoreResult);

    }

    @GetMapping("/")
    public ResponseEntity<String> dummy(){
        return ResponseEntity.ok("Hello I'm alive");
    }

    @GetMapping("scores")
    public ResponseEntity<List<ScoreDto>> getScores(){
        return ResponseEntity.ok(scoreService.getScores());
    }

}
