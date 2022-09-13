package ch.zuehlke.hacking.file;

import ch.zuehlke.hacking.model.YearEntry;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ResultReader {

    public Map<Integer, YearEntry> getResult(String filename) throws IOException {
        TextFileReader reader = new TextFileReader();
        List<String> lines = reader.read(filename);

        InputFileValidator validator = new InputFileValidator();
        validator.validate(lines);

        OutputDataMapper mapper = new OutputDataMapper();
        return mapper.map(lines);
    }
}
