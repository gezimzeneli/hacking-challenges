package ch.zuehlke.hacking.file;

import ch.zuehlke.hacking.file.ResultReader;
import ch.zuehlke.hacking.model.YearEntry;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

class ResultReaderTest {

    @Test
    @Disabled
    void test() throws IOException {
        ResultReader reader = new ResultReader();
        Map<Integer, YearEntry> set = reader.getResult("outputA.txt");


    }



}
