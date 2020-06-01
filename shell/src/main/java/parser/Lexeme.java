package parser;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Lexeme {
    String word;
    LexemeType type;
}
