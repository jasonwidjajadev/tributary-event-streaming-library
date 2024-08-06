package tributary;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

import tributary.cli.TributaryCLI;

public class TributaryTest {
    private TributaryCLI<String, String, String> cli;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private static final String RESET = "\u001B[0m";
    private static final String MAGENTA = "\033[0;35m";

    @Test
    public void testCreateTopic() {
        cli = new TributaryCLI<>();
        System.setOut(new PrintStream(outContent));
        String topicId = "topic_A";
        String type = "Integer";
        String input = "create topic " + topicId + " " + type + "\nq\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        cli.run();
        String expectedOutput = MAGENTA + "+ " + RESET + "Topic created with id:     " + topicId + "\n" + MAGENTA + "+ "
                + RESET + "Topic type:                " + type + "\n";

        assertTrue(outContent.toString().contains(expectedOutput));
        System.setOut(originalOut);
    }

    @Test
    public void testCreatePartition() {
        cli = new TributaryCLI<>();
        System.setOut(new PrintStream(outContent));
        String topicId = "topic_A";
        String partitionId = "A_P0";
        String input = "create topic topic_A Integer\ncreate partition topic_A " + partitionId + "\nq\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        cli.run();
        String expectedOutput = MAGENTA + "+ " + RESET + "Partition created with id  " + partitionId + "\n" + MAGENTA
                + "+ " + RESET + "Created in topic:          " + topicId + "\n";

        assertTrue(outContent.toString().contains(expectedOutput));
        System.setOut(originalOut);
    }
}
