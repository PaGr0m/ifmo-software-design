package commands.impl;

import commands.Command;
import org.junit.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;


public class CommandWcTest {
    private final Command command = new CommandWc();
    private final String pathToTestResources = "src/test/";

    @Test
    public void testRunCommandWc() {
        // Arrange
        String pathToFile = "resources/Example.txt";

        // Act
        String actual = command.run(Collections.singletonList(pathToTestResources + pathToFile));

        // Assert
        assertThat(actual).isEqualTo("1 2 12 src/test/resources/Example.txt\n");
    }

    @Test
    public void testRunCommandWcWithEmptyFile() {
        // Arrange
        String pathToFile = "resources/Empty.txt";

        // Act
        String actual = command.run(Collections.singletonList(pathToTestResources + pathToFile));

        // Assert
        assertThat(actual).isEqualTo("0 0 0 src/test/resources/Empty.txt\n");
    }

    @Test
    public void testRunCommandWcWithPipe() {
        // Act
        String actual = command.run(Collections.singletonList("hello world"));

        // Assert
        assertThat(actual).isEqualTo("1 2 10 \n");
    }

    @Test
    public void testRunCommandWcWithPathToDirectory() {
        // Arrange
        String pathToFile = "resources/";

        // Act
        String actual = command.run(Collections.singletonList(pathToTestResources + pathToFile));

        // Assert
        assertThat(actual).isEqualTo("This path is directory!");
    }
}
