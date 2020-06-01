package commands;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CommandEntity {
    private Command command;
    private List<String> arguments;
}
