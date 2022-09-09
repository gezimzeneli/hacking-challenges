package file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

class TextFileReader {
    public List<String> read(String filename) throws IOException {
        return Files.readAllLines(Paths.get(filename));
    }
}
