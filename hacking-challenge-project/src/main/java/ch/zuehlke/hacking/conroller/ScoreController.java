package ch.zuehlke.hacking.conroller;

import ch.zuehlke.hacking.dto.ScoreDto;
import ch.zuehlke.hacking.model.PersonScore;
import ch.zuehlke.hacking.service.ScoreService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS}, allowedHeaders = {"Content-Type", "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers"}, exposedHeaders = {"Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"})
@AllArgsConstructor
public class ScoreController {

    final ScoreService scoreService;

    @PostMapping(path ="uploadFiles", headers = "content-type=multipart/*")
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
