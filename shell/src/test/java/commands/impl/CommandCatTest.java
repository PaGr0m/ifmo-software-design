package commands.impl;

import commands.Command;
import org.junit.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class CommandCatTest {
    private final Command commandCat = new CommandCat();
    private final String pathToTestResources = "src/test/";

    @Test
    public void testRunCommandCat() {
        // Arrange
        String pathToFile = "resources/Example.txt";

        // Act
        String actual = commandCat.run(Collections.singletonList(pathToTestResources + pathToFile));

        // Assert
        assertThat(actual).isEqualTo("Hello, World!");
    }

    @Test
    public void testRunCommandCatWithEmptyFile() {
        // Arrange
        String pathToFile = "resources/Empty.txt";

        // Act
        String actual = commandCat.run(Collections.singletonList(pathToTestResources + pathToFile));

        // Assert
        assertThat(actual).isEqualTo("");
    }

    @Test
    public void testRunCommandCatWithNotExistFile() {
        // Arrange
        String pathToFile = "resources/NotExist.bash";

        // Act
        String actual = commandCat.run(Collections.singletonList(pathToTestResources + pathToFile));

        // Assert
        assertThat(actual).isEqualTo("File is not exist!");
    }
}