package ch.zuehlke.hacking.conroller;

import ch.zuehlke.hacking.service.ScoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Controller
public class ScoreController {

    final ScoreService scoreService;

    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @PostMapping("uploadFile")
    public ResponseEntity<Void> upload(@RequestParam("file") MultipartFile file, @RequestParam("name") String name) throws IOException {

        scoreService.calculateScore(file, name);

        return ResponseEntity.ok().build();

    }
}
