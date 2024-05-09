package cop2805;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

	// Get file
public class LineSearcher {
    private List<String> lines;

    public LineSearcher(String fileName) {
        try {
            this.lines = Files.readAllLines(Paths.get(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Get lines from text file
    public List<String> searchLines(int lineNumber) {
        List<String> returnLines = new ArrayList<>();
        int sub2 = lineNumber - 2;
        int sub1 = lineNumber - 1;
        int add2 = lineNumber + 2;
        int add1 = lineNumber + 1;

        // Add the previous lines
        if (sub2 >= 0) returnLines.add(lines.get(sub2));
        if (sub1 >= 0) returnLines.add(lines.get(sub1));

        // Add the requested line
        returnLines.add(lines.get(lineNumber));

        // Add the following lines if valid
        if (add1 < lines.size()) returnLines.add(lines.get(add1));
        if (add2 < lines.size()) returnLines.add(lines.get(add2));

        return returnLines;
    }
}
