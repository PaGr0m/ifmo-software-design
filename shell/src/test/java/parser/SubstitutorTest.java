package parser;

import org.junit.Test;
import service.Environment;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SubstitutorTest {
    private final Environment environment = new Environment();
    private final Substitutor substitutor = new Substitutor(environment);

    @Test
    public void testSubstitution() {
        // Arrange
        Lexeme lexemeIn = Lexeme.builder().word("$bash").type(LexemType.WORD_VARIABLE).build();
        Lexeme lexemeOut = Lexeme.builder().word("value").type(LexemType.WORD).build();

        environment.putVariable("bash", "value");

        List<Lexeme> actual = Collections.singletonList(lexemeIn);
        List<Lexeme> expected = Collections.singletonList(lexemeOut);

        // Act
        substitutor.substitution(actual);

        // Assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testSubstitution2() {
        // Arrange
        Lexeme lexemeIn1 = Lexeme.builder().word("$x").type(LexemType.WORD_VARIABLE).build();
        Lexeme lexemeIn2 = Lexeme.builder().word("$y").type(LexemType.WORD_VARIABLE).build();

        environment.putVariable("x", "ex");
        environment.putVariable("y", "it");

        Lexeme lexemeOut1 = Lexeme.builder().word("ex").type(LexemType.WORD).build();
        Lexeme lexemeOut2 = Lexeme.builder().word("it").type(LexemType.WORD).build();

        List<Lexeme> actual = Arrays.asList(lexemeIn1, lexemeIn2);
        List<Lexeme> expected = Arrays.asList(lexemeOut1, lexemeOut2);

        // Act
        substitutor.substitution(actual);

        // Assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testSubstitution3() {
        // Arrange
        Lexeme lexemeIn1 = Lexeme.builder().word("$x").type(LexemType.WORD_VARIABLE).build();
        Lexeme lexemeIn2 = Lexeme.builder().word("x").type(LexemType.WORD).build();
        Lexeme lexemeIn3 = Lexeme.builder().word("$y").type(LexemType.WORD_VARIABLE).build();

        environment.putVariable("x", "e");
        environment.putVariable("y", "it");

        Lexeme lexemeOut1 = Lexeme.builder().word("e").type(LexemType.WORD).build();
        Lexeme lexemeOut2 = Lexeme.builder().word("x").type(LexemType.WORD).build();
        Lexeme lexemeOut3 = Lexeme.builder().word("it").type(LexemType.WORD).build();

        List<Lexeme> actual = Arrays.asList(lexemeIn1, lexemeIn2, lexemeIn3);
        List<Lexeme> expected = Arrays.asList(lexemeOut1, lexemeOut2, lexemeOut3);

        // Act
        substitutor.substitution(actual);

        // Assert
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testSubstitution4() {
        // Arrange
        Lexeme lexemeIn1 = Lexeme.builder().word("\"$x\"").type(LexemType.WORD_IN_DOUBLE_QUOTE).build();
        Lexeme lexemeOut1 = Lexeme.builder().word("\"42\"").type(LexemType.WORD).build();

        environment.putVariable("x", "42");

        List<Lexeme> actual = Collections.singletonList(lexemeIn1);
        List<Lexeme> expected = Collections.singletonList(lexemeOut1);

        // Act
        substitutor.substitution(actual);

        // Assert
        assertThat(actual).isEqualTo(expected);
    }
}
