package commands.impl;

import commands.Command;
import org.junit.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class CommandLsTest {
    private final Command commandLs = new CommandDefault("ls");

    @Test
    public void testRunCommandLs() {
        String actual = commandLs.run(Collections.singletonList("resources/empty"));
        assertThat(actual).isEqualTo("");
    }
}
