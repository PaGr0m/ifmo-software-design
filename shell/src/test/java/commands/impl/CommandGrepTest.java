package commands.impl;

import commands.Command;
import commands.CommandException;
import org.junit.Test;
import service.Executor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CommandGrepTest {
    private final Command commandGrep = new CommandGrep();
    private final String pathToTestResources = "src/test/resources/grep/";

    @Test
    public void testRunCommandGrepWithoutFlags() {
        // Arrange
        String searchWord = "plugin";
        String filename = "build.gradle.txt";
        String arguments = searchWord + " " + pathToTestResources + filename;

        List<String> result = Arrays.asList("apply plugin: 'java'",
                                            "apply plugin: 'JAVA'",
                                            "apply plugin: 'javac'",
                                            "apply plugin: 'idea'");

        // Act
        String actual = commandGrep.run(Arrays.asList(arguments.split(" ")));

        // Assert
        assertThat(actual).isEqualTo(String.join("\n", result));
    }

    @Test
    public void testRunCommandGrepWithCaseFlag() {
        // Arrange
        String searchWord = "plugin";
        String filename = "build.gradle.txt";
        String arguments = "-i " + searchWord + " " + pathToTestResources + filename;

        List<String> result = Arrays.asList("apply plugin: 'java'",
                                            "apply plugin: 'JAVA'",
                                            "apply plugin: 'javac'",
                                            "apply PLUGIN: 'kotlin'",
                                            "apply plugin: 'idea'");
        // Act
        String actual = commandGrep.run(Arrays.asList(arguments.split(" ")));

        // Assert
        assertThat(actual).isEqualTo(String.join("\n", result));
    }

    @Test
    public void testRunCommandGrepWithWordEqualsFlag() {
        // Arrange
        String searchWord = "java";
        String filename = "build.gradle.txt";
        String arguments = "-w " + searchWord + " " + pathToTestResources + filename;

        List<String> result = Collections.singletonList("apply plugin: 'java'");

        // Act
        String actual = commandGrep.run(Arrays.asList(arguments.split(" ")));

        // Assert
        assertThat(actual).isEqualTo(String.join("\n", result));
    }

    @Test
    public void testRunCommandGrepWithWordEqualsAndCaseFlag() {
        // Arrange
        String searchWord = "java";
        String filename = "build.gradle.txt";
        String arguments = "-i -w " + searchWord + " " + pathToTestResources + filename;

        List<String> result = Arrays.asList("apply plugin: 'java'",
                                            "apply plugin: 'JAVA'");

        // Act
        String actual = commandGrep.run(Arrays.asList(arguments.split(" ")));

        // Assert
        assertThat(actual).isEqualTo(String.join("\n", result));
    }

    @Test
    public void testRunCommandGrepWithNextLineFlag() {
        // Arrange
        String searchWord = "java";
        String filename = "build.gradle.txt";
        String arguments = "-A 1 " + searchWord + " " + pathToTestResources + filename;

        List<String> result = Arrays.asList("apply plugin: 'java'",
                                            "apply plugin: 'JAVA'",
                                            "apply plugin: 'javac'",
                                            "apply PLUGIN: 'kotlin'");

        // Act
        String actual = commandGrep.run(Arrays.asList(arguments.split(" ")));

        // Assert
        assertThat(actual).isEqualTo(String.join("\n", result));
    }

    @Test
    public void testRunCommandGrepWithNextLineFlagFromFile() {
        // Arrange
        String searchWord = "аптека";
        String filename = "Blok.txt";
        String arguments = "-A 2 " + searchWord + " " + pathToTestResources + filename;

        List<String> result = Arrays.asList("Ночь, улица, фонарь, аптека,",
                                            "Бессмысленный и тусклый свет.",
                                            "Живи ещё хоть четверть века —");

        // Act
        String actual = commandGrep.run(Arrays.asList(arguments.split(" ")));

        // Assert
        assertThat(actual).isEqualTo(String.join("\n", result));
    }

    @Test
    public void testRunCommandGrepWithPipe() {
        // Arrange
        String arguments = "cat src/test/resources/grep/build.gradle.txt | grep plugin";

        List<String> result = Arrays.asList("apply plugin: 'java'",
                                            "apply plugin: 'JAVA'",
                                            "apply plugin: 'javac'",
                                            "apply plugin: 'idea'");

        // Act
        Executor executor = new Executor();
        String actual = executor.execute(arguments);

        // Assert
        assertThat(actual).isEqualTo(String.join("\n", result));
    }

    @Test
    public void testRunCommandGrepWithPipeAndFlag() {
        // Arrange
        String arguments = "cat src/test/resources/grep/Blok.txt | grep -A 2 свет";

        List<String> result = Arrays.asList("Бессмысленный и тусклый свет.",
                                            "Живи ещё хоть четверть века —",
                                            "Всё будет так. Исхода нет.");

        // Act
        Executor executor = new Executor();
        String actual = executor.execute(arguments);

        // Assert
        assertThat(actual).isEqualTo(String.join("\n", result));
    }

    @Test(expected = CommandException.class)
    public void testRunCommandGrepException() {
        // Arrange
        String searchWord = "plugin";
        String filename = "build.gradle.txt";
        String arguments = "-A" + searchWord + " " + pathToTestResources + filename;

        // Act
        commandGrep.run(Arrays.asList(arguments.split(" ")));
    }

    @Test(expected = CommandException.class)
    public void testRunCommandGrepExceptionWithoutArguments() {
        // Act
        commandGrep.run(Collections.singletonList("-A"));
    }
}
