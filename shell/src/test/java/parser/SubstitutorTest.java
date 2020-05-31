package parser;

import org.junit.Test;
import service.Environment;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SubstitutorTest {
    private final Substitutor substitutor = new Substitutor();
    private final Environment environment = Environment.getInstance();

    @Test
    public void testSubstitution() {
        // Arrange
        String input = "Hello World \"    Software  Design\"  $bash 'hi' ";
        Lexeme lexeme1 = Lexeme.builder().word("Hello").type(LexemType.WORD).build();
        Lexeme lexeme2 = Lexeme.builder().word("World").type(LexemType.WORD).build();
        Lexeme lexeme3 = Lexeme.builder().word("\"    Software  Design\"").type(LexemType.WORD_IN_DOUBLE_QUOTE).build();
        Lexeme lexeme4 = Lexeme.builder().word("$bash").type(LexemType.WORD_VARIABLE).build();
        Lexeme lexeme5 = Lexeme.builder().word("'hi'").type(LexemType.WORD_IN_SINGLE_QUOTE).build();
        Lexeme lexeme6 = Lexeme.builder().word("value").type(LexemType.WORD_VARIABLE).build();

        List<Lexeme> actual = Arrays.asList(lexeme1, lexeme2, lexeme3, lexeme4, lexeme5);
        List<Lexeme> expected = Arrays.asList(lexeme1, lexeme2, lexeme3, lexeme6, lexeme5);

        environment.putVariable("bash", "value");

        // Act
        substitutor.substitution(actual);

        // Assert
        assertThat(actual).isEqualTo(expected);
    }
}
