package service;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ExecutorTest {
    private final Executor executor = new Executor();
    private final Environment environment = new Environment();

    @Test
    public void testExecuteSingleCommand() {
        // Arrange
        String input = "echo hello world";
        String expected = "hello world";

        // Act
        String actual = executor.execute(input);

        // Assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testExecuteCommands() {
        // Arrange
        String input = "echo hello world | wc";
        String expected = "1 2 10 ";

        // Act
        String actual = executor.execute(input);

        // Assert
        assertThat(actual).isEqualTo(expected);
    }
}
