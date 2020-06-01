package commands;

import commands.impl.CommandEcho;
import org.junit.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;


public class CommandExecutorTest {
    private final CommandExecutor commandExecutor = new CommandExecutor();

    @Test
    public void testExecuteCommandEcho() {
        // Arrange
        CommandEntity commandEntity =
                CommandEntity.builder()
                             .command(new CommandEcho())
                             .arguments(Collections.singletonList("hello world"))
                             .build();

        // Act
        String actual = commandExecutor.execute(commandEntity);

        // Arrange
        assertThat(actual).isEqualTo("hello world");
    }
}
