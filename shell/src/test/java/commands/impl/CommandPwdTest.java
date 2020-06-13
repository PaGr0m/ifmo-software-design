package commands.impl;

import commands.Command;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CommandPwdTest {
    private final Command commandPwd = new CommandPwd();

    @Test
    public void testRunCommandPwd() {
        String actual = commandPwd.run(null);
        assertThat(actual).isEqualTo(System.getProperty("user.dir"));
    }
}
