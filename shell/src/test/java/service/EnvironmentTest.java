package service;

import commands.impl.CommandExit;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EnvironmentTest {
    private final Environment environment = new Environment();

    @Test
    public void testGetCommand() {
        assertThat(environment.getCommand("exit").getClass()).isEqualTo(CommandExit.class);
    }

    @Test
    public void testPutAndGetVariable() {
        // Act
        environment.putVariable("Hello", "World");

        // Assert
        assertThat(environment.getVariable("Hello")).isEqualTo("World");
    }

    @Test
    public void testGetCurrentPath() {
        assertThat(Environment.getCurrentPath()).isEqualTo(System.getProperty("user.dir"));
    }
}
